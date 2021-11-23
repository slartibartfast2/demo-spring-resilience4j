package ea.slartibartfast.demo.resilience4j.controller.response;

import ea.slartibartfast.demo.resilience4j.model.PaymentVo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class PaymentResponse {

    private PaymentVo paymentVo;
}
