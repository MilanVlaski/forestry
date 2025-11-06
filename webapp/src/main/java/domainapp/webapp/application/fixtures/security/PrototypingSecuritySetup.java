package domainapp.webapp.application.fixtures.security;

import org.apache.causeway.testing.fixtures.applib.fixturescripts.FixtureScript;

import domainapp.webapp.application.seed.Users;

public class PrototypingSecuritySetup extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChildren(this,
                new Users.MarcoTheForestManager(),
                new Users.MariaTheArborist()
        );
    }
}
