package domainapp.webapp.custom.restapi;

import jakarta.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ReadyController {
    @Inject org.apache.causeway.core.metamodel.specloader.SpecificationLoader specLoader;

    @GetMapping("/ready")
    public ResponseEntity<Object> ready() {
        return specLoader.isMetamodelFullyIntrospected()
                ? ResponseEntity.ok("OK") : ResponseEntity.status(503).body("warming");
    }
}