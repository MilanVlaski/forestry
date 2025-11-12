package com.akimi.webapp.application.fixtures.data;

import com.akimi.modules.forest_inventory.fixture.CreateForestsAndEnumsAndSampleData;

import org.apache.causeway.testing.fixtures.applib.fixturescripts.FixtureScript;
import org.apache.causeway.testing.fixtures.applib.modules.ModuleWithFixturesService;

import jakarta.inject.Inject;

/**
 * For creating fake objects while prototyping.
 * - MV
 */
public class DomainAppDemo extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {
        ec.executeChildren(
                this, moduleWithFixturesService.getTeardownFixture());
        ec.executeChild(
                this, new CreateForestsAndEnumsAndSampleData());
    }

    @Inject ModuleWithFixturesService moduleWithFixturesService;

}
