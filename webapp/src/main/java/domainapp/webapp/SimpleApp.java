package domainapp.webapp;

import domainapp.webapp.application.gcp.SecretInitializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
    AppManifest.class
//    , XrayEnable.class
})
public class SimpleApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        var app = new SpringApplication(SimpleApp.class);
        app.addInitializers(new SecretInitializer());
        app.run(args);
    }

}
