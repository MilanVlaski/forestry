package domainapp.webapp.application.fixture.scenarios;

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