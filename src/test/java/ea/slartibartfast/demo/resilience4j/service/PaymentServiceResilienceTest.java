package ea.slartibartfast.demo.resilience4j.service;

import ea.slartibartfast.demo.resilience4j.controller.request.PaymentRequest;
import ea.slartibartfast.demo.resilience4j.model.PaymentVo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PaymentServiceResilienceTest {

    private static PaymentService paymentService;


    private static final String SAVED_CARD_TOKEN = "newCard";
    private static final Logger log = LoggerFactory.getLogger(PaymentServiceResilienceTest.class);

    @Autowired
    void setPaymentService(PaymentService paymentService) {
        PaymentServiceResilienceTest.paymentService = paymentService;
    }

    @Test
    void should_reject_call_when_request_count_is_bigger_than_bulkhead_maxConcurrentCalls() throws ExecutionException, InterruptedException, TimeoutException {
        //given
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(BigDecimal.TEN);
        paymentRequest.setCurrency("TRY");
        paymentRequest.setCardToken("token-2");
        paymentRequest.setSaveCard(true);

        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<PaymentVo>> futures = new ArrayList<>();


        //when
        for (int i = 0; i < 5; i++) {
            futures.add(executor.submit(() -> paymentService.pay(paymentRequest)));
        }

        //then
        int successCount = 0;
        int failureCount = 0;
        for (Future<PaymentVo> f : futures) {
            PaymentVo paymentVo = f.get(7, TimeUnit.SECONDS);
            if (SAVED_CARD_TOKEN.equals(paymentVo.getCardToken())) {
                successCount++;
            } else {
                failureCount++;
            }
        }

        assertEquals(successCount, 4);
        assertEquals(failureCount, 1);
    }
}