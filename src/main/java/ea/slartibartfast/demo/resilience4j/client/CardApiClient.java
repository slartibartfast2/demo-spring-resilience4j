package ea.slartibartfast.demo.resilience4j.client;

import ea.slartibartfast.demo.resilience4j.exception.CardNotFoundException;
import ea.slartibartfast.demo.resilience4j.model.CardVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CardApiClient {

    private static final CardVo mockCardVo;
    private static final String MOCK_CARD_TOKEN;
    private static final String SLEEP_CARD_TOKEN;
    private static final String FAILURE_CARD_TOKEN;
    private static final String FAILURE_CARD_NUMBER;

    static {
        mockCardVo = CardVo.builder().cardNumber("1111222233334444").expireMonth(12).expireYear(2030).build();

        MOCK_CARD_TOKEN = "newCard";
        FAILURE_CARD_TOKEN = "token-1";
        SLEEP_CARD_TOKEN = "token-2";
        FAILURE_CARD_NUMBER = "5555666677778888";
    }

    public CardVo retrieveCard(String cardToken) {
        if (FAILURE_CARD_TOKEN.equals(cardToken)) {
            throw new CardNotFoundException();
        }
        if (SLEEP_CARD_TOKEN.equals(cardToken)) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
        return mockCardVo;
    }

    public String saveCard(CardVo cardVo) {
        if (FAILURE_CARD_NUMBER.equals(cardVo.getCardNumber())) {
            throw new CardNotFoundException();
        }
        return MOCK_CARD_TOKEN;
    }
}
