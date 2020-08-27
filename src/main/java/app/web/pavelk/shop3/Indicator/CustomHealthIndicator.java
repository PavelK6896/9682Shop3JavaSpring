package app.web.pavelk.shop3.Indicator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

//    http://localhost:8080/actuator/health/diskSpace
//    http://localhost:8080/actuator/health/custom

    @Override
    public Health health() {
        int errorCode = 0;

        if (errorCode != 0) {
            return Health.down().withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

}
