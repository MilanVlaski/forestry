package domainapp.modules.forest_inventory.fixture;

import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.testing.fixtures.applib.fixturescripts.FixtureScript;

import domainapp.modules.forest_inventory.forest.Forests;
import domainapp.modules.forest_inventory.tree.condition.TreeCondition;
import domainapp.modules.forest_inventory.tree.species.TreeSpecies;
import jakarta.inject.Inject;

public class CreateForestsAndEnums extends FixtureScript {

    @Override
    protected void execute(ExecutionContext ec) {
        treeCondition.createAll("Excellent", "Very Good", "Good", "Poor", "Very Poor");
        treeSpecies.createAll("European beech", "Abies alba", "Silver fir", "Abies alba", "Norway spruce", "Picea abies",
                "Oak", "Quercus robur", "Scots pine", "Pinus sylvestris", "European hornbeam", "Carpinus betulus",
                "Silver birch", "Betula pendula");
        forests.createAll("Janj", "Lom", "Perućica");
    }

    @Inject TreeSpecies treeSpecies;
    @Inject TreeCondition treeCondition;
    @Inject Forests forests;
    @Inject RepositoryService repositoryService;
}
