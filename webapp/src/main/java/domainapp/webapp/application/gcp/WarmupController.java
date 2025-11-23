package domainapp.webapp.application.gcp;

import jakarta.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WarmupController {
    @Inject org.apache.causeway.core.metamodel.specloader.SpecificationLoader specLoader;

    @GetMapping("/_ah/warmup")
    public ResponseEntity<String> ready() {
        return ResponseEntity.ok("Ok");
    }
}
