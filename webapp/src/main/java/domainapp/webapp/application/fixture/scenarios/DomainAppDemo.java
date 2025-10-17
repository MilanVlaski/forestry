package domainapp.webapp.application.fixture.scenarios;

import org.apache.causeway.testing.fixtures.applib.fixturescripts.FixtureScript;
import org.apache.causeway.testing.fixtures.applib.modules.ModuleWithFixturesService;

import domainapp.modules.simple.fixture.SimpleObject_persona;
import jakarta.inject.Inject;

/**
 * For creating fake objects while prototyping.
 * - MV
 */
public class DomainAppDemo extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {
        ec.executeChildren(this, moduleWithFixturesService.getTeardownFixture());
        ec.executeChild(this, new SimpleObject_persona.PersistAll());
    }

    @Inject ModuleWithFixturesService moduleWithFixturesService;

}
