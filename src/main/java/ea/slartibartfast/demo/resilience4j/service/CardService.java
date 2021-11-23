package ea.slartibartfast.demo.resilience4j.service;

import ea.slartibartfast.demo.resilience4j.client.CardApiClient;
import ea.slartibartfast.demo.resilience4j.model.CardVo;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CardService {

    private final CardApiClient cardApiClient;

    private static final String EMPTY_CARD_TOKEN = "";
    private static final String CARD_API = "cardApi";

    @CircuitBreaker(name = CARD_API, fallbackMethod = "retrieveCardCircuitBreakerFallback")
    @Bulkhead(name = CARD_API)
    public CardVo retrieveCard(final String cardToken) {
        log.info("going to retrieve card for cardToken {}", cardToken);
        return cardApiClient.retrieveCard(cardToken);
    }

    public CardVo retrieveCardCircuitBreakerFallback(Throwable t) {
        log.warn("Card api not healthy! Returning empty card", t);
        return CardVo.builder().build();
    }

    @CircuitBreaker(name = CARD_API, fallbackMethod = "saveCardCircuitBreakerFallback")
    @Bulkhead(name = CARD_API)
    public String saveCard(CardVo cardVo) {
        log.info("card {} will be saved", cardVo.getCardNumber());
        return cardApiClient.saveCard(cardVo);
    }

    public String saveCardCircuitBreakerFallback(Throwable e) {
        log.warn("Card api not healthy! Card cannot be saved, returning empty card token");
        return EMPTY_CARD_TOKEN;
    }

    public String saveCardCircuitBreakerFallback(CallNotPermittedException e) {
        log.warn("Card api not healthy! Call not permitted");
        return null;
    }
}
