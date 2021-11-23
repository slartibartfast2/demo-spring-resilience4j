package ea.slartibartfast.demo.resilience4j.service;

import ea.slartibartfast.demo.resilience4j.controller.request.PaymentRequest;
import ea.slartibartfast.demo.resilience4j.model.CardVo;
import ea.slartibartfast.demo.resilience4j.model.PaymentVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final CardService cardService;
    private static final Long MOCK_PAYMENT_ID = 1L;

    public PaymentVo pay(PaymentRequest paymentRequest) {
        CardVo cardVo = prepareCard(paymentRequest);

        String cardToken = "";
        if (cardSuitableForSaving(cardVo, paymentRequest.getSaveCard())) {
            cardToken = cardService.saveCard(cardVo);
        }

        log.info("payment {} saved successfully", MOCK_PAYMENT_ID);
        return PaymentVo.builder().paymentId(MOCK_PAYMENT_ID).cardToken(cardToken).build();
    }

    private CardVo prepareCard(PaymentRequest paymentRequest) {
        if (Optional.ofNullable(paymentRequest.getCardToken()).isPresent()) {
            return cardService.retrieveCard(paymentRequest.getCardToken());
        } else {
            return CardVo.builder()
                         .expireYear(paymentRequest.getExpireYear())
                         .expireMonth(paymentRequest.getExpireMonth())
                         .cardNumber(paymentRequest.getCardNumber())
                         .build();
        }
    }

    private boolean cardSuitableForSaving(CardVo cardVo, boolean saveCard) {
        return Optional.ofNullable(cardVo.getCardNumber()).isPresent() && saveCard;
    }
}
