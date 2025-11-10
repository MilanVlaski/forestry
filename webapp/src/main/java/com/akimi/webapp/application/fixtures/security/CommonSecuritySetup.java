package com.akimi.webapp.application.fixtures.security;

import org.apache.causeway.testing.fixtures.applib.fixturescripts.FixtureScript;

import com.akimi.webapp.application.seed.Roles;

public class CommonSecuritySetup extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChildren(this,
                new Roles.ForestModuleSuperuserRole(),
                new Roles.ArboristRole()
        );
    }
}