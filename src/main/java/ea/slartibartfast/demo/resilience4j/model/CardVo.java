package ea.slartibartfast.demo.resilience4j.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CardVo {

    private String cardNumber;
    private int expireMonth;
    private int expireYear;
}
