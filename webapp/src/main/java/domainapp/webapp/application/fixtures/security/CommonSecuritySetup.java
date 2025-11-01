package domainapp.webapp.application.fixtures.security;

import domainapp.webapp.application.seed.Roles;
import org.apache.causeway.testing.fixtures.applib.fixturescripts.FixtureScript;

public class CommonSecuritySetup extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChildren(this,
                new Roles.ForestModuleSuperuserRole()
        );
    }
}