package domainapp.webapp.application.gcp;

import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import static com.google.cloud.secretmanager.v1.SecretVersionName.of;

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

        try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
            System.setProperty("ADMIN_PASSWORD",
                    fetchSecret(projectId, "admin-password", client));
            System.setProperty("GCLOUD_DB",
                    fetchSecret(projectId, "GCLOUD_DB", client));
            System.setProperty("GCLOUD_DB_USER",
                    fetchSecret(projectId, "GCLOUD_DB_USER", client));
            System.setProperty("GCLOUD_DB_INSTANCE",
                    fetchSecret(projectId, "GCLOUD_DB_INSTANCE", client));
        } catch (Exception e) {
            throw new RuntimeException("Failed loading secret", e);
        }

    }

    private static String fetchSecret(String projectId, String secretId,
                                      SecretManagerServiceClient client) {
        return client.accessSecretVersion(of(projectId, secretId, "latest"))
                .getPayload().getData().toStringUtf8();
    }

}
