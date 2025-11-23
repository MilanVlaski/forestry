package domainapp.webapp.application.gcp;

import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;

import com.google.cloud.secretmanager.v1.SecretVersionName;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import static org.springframework.core.env.Profiles.of;

public class SecretInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
        Environment env = ctx.getEnvironment();

        if (!env.acceptsProfiles(of("prod"))) {
            return;
        }

        String projectId = System.getenv("GOOGLE_CLOUD_PROJECT");
        String secretId  = "admin-password";

        String value = loadFromGSecretManager(projectId, secretId);

        System.setProperty("ADMIN_PASSWORD", value);
    }

    private String loadFromGSecretManager(String projectId, String secretId) {
        try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
            var name = SecretVersionName.of(projectId, secretId, "latest");
            var resp = client.accessSecretVersion(name);
            return resp.getPayload().getData().toStringUtf8();
        } catch (Exception e) {
            throw new RuntimeException("Failed loading secret", e);
        }
    }
}
