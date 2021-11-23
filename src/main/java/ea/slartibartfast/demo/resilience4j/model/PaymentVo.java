package ea.slartibartfast.demo.resilience4j.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class PaymentVo {

    private Long paymentId;

    @Setter
    private String cardToken;
}
