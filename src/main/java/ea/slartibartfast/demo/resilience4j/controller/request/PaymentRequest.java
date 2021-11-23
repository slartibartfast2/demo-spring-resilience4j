package ea.slartibartfast.demo.resilience4j.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Setter
@Getter
public class PaymentRequest {

    @NotNull @Positive
    private BigDecimal amount;

    @NotBlank
    private String currency;

    private String cardToken;
    private String cardNumber;
    private int expireMonth;
    private int expireYear;

    @NotNull
    private Boolean saveCard;
}
