package ea.slartibartfast.demo.resilience4j.configuration;

import io.github.resilience4j.bulkhead.BulkheadRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class BulkheadConfiguration {

    private final BulkheadRegistry bulkheadRegistry;

    @PostConstruct
    public void eventSetting() {
        bulkheadRegistry.getAllBulkheads()
                        .forEach(bulkhead -> bulkhead.getEventPublisher()
                                                     .onCallPermitted(event -> log.info("CALL PERMITTED!!!!!"))
                                                     .onCallRejected(event -> log.error("CALL REJECTED!!!!!"))
                                                     .onCallFinished(event -> log.info("CALL FINISHED!!!!!")));
    }
}
