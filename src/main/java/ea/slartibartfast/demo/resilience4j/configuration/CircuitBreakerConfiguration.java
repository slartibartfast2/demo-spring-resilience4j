package ea.slartibartfast.demo.resilience4j.configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class CircuitBreakerConfiguration {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @PostConstruct
    public void eventSetting() {
        circuitBreakerRegistry.getAllCircuitBreakers()
                              .forEach(circuitBreaker -> circuitBreaker.getEventPublisher()
                                                                       .onSuccess(e -> log.info("SUCCESS!!!!!"))
                                                                       .onError(e -> log.error("ERROR!!!!!"))
                                                                       .onStateTransition(e -> log.info("{} to {}",
                                                                                                        e.getStateTransition().getFromState(),
                                                                                                        e.getStateTransition().getToState()))
                                                                       .onCallNotPermitted(e -> log.warn("Not Permitted!!!!!"))
                                                                       .onIgnoredError(e -> log.info("Ignored!!!!!"))
                                                                       .onReset(e -> log.info("Reset!!!!")));
    }
}

