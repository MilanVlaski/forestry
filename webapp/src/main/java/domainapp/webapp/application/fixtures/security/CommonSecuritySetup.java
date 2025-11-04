package domainapp.webapp.application.fixtures.security;

import org.apache.causeway.testing.fixtures.applib.fixturescripts.FixtureScript;

import domainapp.webapp.application.seed.Roles;

public class CommonSecuritySetup extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChildren(this,
                new Roles.ForestModuleSuperuserRole(),
                new Roles.ArboristRole()
        );
    }
}