package domainapp.modules.forest_inventory.fixture;

import java.math.BigDecimal;

import org.apache.causeway.applib.services.clock.ClockService;
import org.apache.causeway.applib.services.registry.ServiceRegistry;
import org.apache.causeway.testing.fakedata.applib.services.FakeDataService;
import org.apache.causeway.testing.fixtures.applib.personas.BuilderScriptWithResult;
import org.apache.causeway.testing.fixtures.applib.personas.Persona;
import org.apache.causeway.testing.fixtures.applib.setup.PersonaEnumPersistAll;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.val;
import lombok.experimental.Accessors;

import domainapp.modules.forest_inventory.forest.Forest;
import domainapp.modules.forest_inventory.forest.Forests;
import domainapp.modules.forest_inventory.plot.Plot;
import domainapp.modules.forest_inventory.tree.Tree;
import jakarta.inject.Inject;

@RequiredArgsConstructor
public enum Forest_persona implements Persona<Forest, Forest_persona.Builder> {

    JANJ("Janj"),
    LOM("Lom"),
    PERUCICA("Perućica");

    private final String name;

    @Override
    public Builder builder() {
        return new Builder().setPersona(this);
    }

    @Override
    public Forest findUsing(final ServiceRegistry serviceRegistry) {
        return serviceRegistry.lookupService(Forests.class).map(x -> x.findByNameExact(name)).orElseThrow();
    }

    @Accessors(chain = true)
    public static class Builder extends BuilderScriptWithResult<Forest> {

        @Getter @Setter private Forest_persona persona;

        @Override
        protected Forest buildResult(final ExecutionContext ec) {

            val forest = wrap(forests).create(persona.name);
            val plot = new Plot();
            val tree = new Tree(BigDecimal.valueOf(10), BigDecimal.valueOf(10),
                    null, null, null);

            var inventory = forest.addInventory();
            inventory.addPlot(plot);
            plot.addTree(tree);

            return forest;
        }

        // -- DEPENDENCIES

        @Inject Forests forests;
        @Inject ClockService clockService;
        @Inject FakeDataService fakeDataService;
    }

    public static class PersistAll
            extends PersonaEnumPersistAll<Forest, Forest_persona, Builder> {
        public PersistAll() {
            super(Forest_persona.class);
        }
    }


}
