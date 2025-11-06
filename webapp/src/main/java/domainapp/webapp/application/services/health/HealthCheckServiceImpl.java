package domainapp.webapp.application.services.health;

import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.health.Health;
import org.apache.causeway.applib.services.health.HealthCheckService;

import jakarta.inject.Named;

@Service
@Named("domainapp.HealthCheckServiceImpl")
public class HealthCheckServiceImpl implements HealthCheckService {

//    private final SimpleObjects simpleObjects;
//
//    @Inject
//    public HealthCheckServiceImpl(SimpleObjects simpleObjects) {
//        this.simpleObjects = simpleObjects;
//    }

    @Override
    public Health check() {
        return Health.ok();
//        try {
//            simpleObjects.ping();
//            return Health.ok();
//        } catch (Exception ex) {
//            return Health.error(ex);
//        }
    }
}
