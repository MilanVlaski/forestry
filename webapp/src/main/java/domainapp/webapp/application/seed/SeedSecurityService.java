package domainapp.webapp.application.seed;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.applib.events.metamodel.MetamodelEvent;
import org.apache.causeway.applib.services.xactn.TransactionService;
import org.apache.causeway.core.config.environment.CausewaySystemEnvironment;
import org.apache.causeway.testing.fixtures.applib.fixturescripts.FixtureScript;
import org.apache.causeway.testing.fixtures.applib.fixturescripts.FixtureScripts;

import lombok.RequiredArgsConstructor;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;

@Service
@Priority(PriorityPrecedence.MIDPOINT + 10)
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class SeedSecurityService {

    private final FixtureScripts fixtureScripts;
    private final TransactionService transactionService;
    private final CausewaySystemEnvironment causewaySystemEnvironment;

    @EventListener(MetamodelEvent.class)
    public void onMetamodelEvent(final MetamodelEvent event) {
        if (event.isPostMetamodel()) {

            fixtureScripts.run(new CommonSecuritySetup());

            if (causewaySystemEnvironment.isPrototyping()) {
                fixtureScripts.run(new PrototypingSecuritySetup());
            }

            transactionService.flushTransaction();
        }
    }

    private static class PrototypingSecuritySetup extends FixtureScript {

        @Override
        protected void execute(ExecutionContext executionContext) {
            executionContext.executeChildren(this,
                    new Roles.SimpleModuleSuperuserRole(),
                    new Users.SvenUser()
            );
        }
    }

    private static class CommonSecuritySetup extends FixtureScript {

        @Override
        protected void execute(ExecutionContext executionContext) {
            executionContext.executeChildren(this,
                    new Roles.ForestModuleSuperuserRole()
            );
        }
    }

}
