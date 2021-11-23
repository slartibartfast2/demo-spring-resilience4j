package ea.slartibartfast.demo.resilience4j.controller;

import ea.slartibartfast.demo.resilience4j.controller.request.PaymentRequest;
import ea.slartibartfast.demo.resilience4j.controller.response.PaymentResponse;
import ea.slartibartfast.demo.resilience4j.model.PaymentVo;
import ea.slartibartfast.demo.resilience4j.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class RestPaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponse add(@RequestBody @Valid PaymentRequest paymentRequest) {
        PaymentVo paymentVo = paymentService.pay(paymentRequest);
        return PaymentResponse.builder().paymentVo(paymentVo).build();
    }
}
