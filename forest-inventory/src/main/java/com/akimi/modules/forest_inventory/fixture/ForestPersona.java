package com.akimi.modules.forest_inventory.fixture;

import java.math.BigDecimal;

import com.akimi.modules.forest_inventory.forest.Forest;
import com.akimi.modules.forest_inventory.forest.Forests;
import com.akimi.modules.forest_inventory.plot.Plot;
import com.akimi.modules.forest_inventory.tree.Tree;

import org.apache.causeway.applib.services.clock.ClockService;
import org.apache.causeway.applib.services.registry.ServiceRegistry;
import org.apache.causeway.testing.fakedata.applib.services.FakeDataService;
import org.apache.causeway.testing.fixtures.applib.personas.BuilderScriptWithResult;
import org.apache.causeway.testing.fixtures.applib.personas.Persona;
import org.apache.causeway.testing.fixtures.applib.setup.PersonaEnumPersistAll;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import jakarta.inject.Inject;

@RequiredArgsConstructor
public enum ForestPersona implements Persona<Forest, ForestPersona.Builder> {

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
        return serviceRegistry.lookupService(Forests.class)
                .map(x -> x.findByNameExact(name)).orElseThrow();
    }

    @Accessors(chain = true)
    public static class Builder extends BuilderScriptWithResult<Forest> {

        @Getter @Setter private ForestPersona persona;

        @Override
        protected Forest buildResult(final ExecutionContext ec) {

            var forest = wrap(forests).create(persona.name);
            final var plot = new Plot();
            final var tree = new Tree(
                    BigDecimal.valueOf(10), BigDecimal.valueOf(10),
                    null, null, null
            );

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
            extends PersonaEnumPersistAll<Forest, ForestPersona, Builder> {
        public PersistAll() {
            super(ForestPersona.class);
        }
    }


}
