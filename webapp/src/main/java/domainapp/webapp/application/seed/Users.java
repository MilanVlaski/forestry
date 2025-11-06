package domainapp.webapp.application.seed;

import java.util.function.Supplier;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.core.config.CausewayConfiguration;
import org.apache.causeway.extensions.secman.applib.user.dom.AccountType;
import org.apache.causeway.extensions.secman.applib.user.fixtures.AbstractUserAndRolesFixtureScript;

import jakarta.inject.Inject;

public class Users {

    public static class MarcoTheForestManager extends AbstractUserAndRolesFixtureScript {
        public MarcoTheForestManager() {
            super(() -> "marco", () -> "pass", () -> AccountType.LOCAL, new RoleSupplier());
        }

        private static class RoleSupplier implements Supplier<Can<String>> {
            @Override
            public Can<String> get() {
                return Can.of(
                        causewayConfiguration.getExtensions().getSecman().getSeed().getRegularUser().getRoleName(), // built-in stuff
                        Roles.ForestModuleSuperuserRole.ROLE_NAME
                );
            }

            @Inject CausewayConfiguration causewayConfiguration;
        }
    }

    public static class MariaTheArborist extends AbstractUserAndRolesFixtureScript {
        public MariaTheArborist() {
            super(() -> "maria", () -> "pass", () -> AccountType.LOCAL, new RoleSupplier());
        }

        private static class RoleSupplier implements Supplier<Can<String>> {
            @Override
            public Can<String> get() {
                return Can.of(
                        causewayConfiguration.getExtensions().getSecman().getSeed().getRegularUser().getRoleName(), // built-in stuff
                        Roles.ArboristRole.ROLE_NAME
                );
            }

            @Inject CausewayConfiguration causewayConfiguration;
        }
    }

}
