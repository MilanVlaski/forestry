package domainapp.webapp.application.fixture.scenarios;

import domainapp.webapp.application.seed.Roles;
import domainapp.webapp.application.seed.Users;
import org.apache.causeway.testing.fixtures.applib.fixturescripts.FixtureScript;

public class PrototypingSecuritySetup extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChildren(this,
                new Roles.SimpleModuleSuperuserRole(),
                new Users.SvenUser()
        );
    }
}
