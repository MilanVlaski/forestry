package domainapp.modules.forest_inventory.fixture;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.testing.fixtures.applib.fixturescripts.FixtureScript;

import domainapp.modules.forest_inventory.forest.Forest;
import domainapp.modules.forest_inventory.forest.Forests;
import domainapp.modules.forest_inventory.inventory.Inventory;
import domainapp.modules.forest_inventory.plot.Plot;
import domainapp.modules.forest_inventory.tree.Tree;
import domainapp.modules.forest_inventory.tree.condition.Condition;
import domainapp.modules.forest_inventory.tree.condition.TreeCondition;
import domainapp.modules.forest_inventory.tree.species.Species;
import domainapp.modules.forest_inventory.tree.species.TreeSpecies;
import jakarta.inject.Inject;

public class CreateForestsAndEnums extends FixtureScript {

    @Override
    protected void execute(ExecutionContext ec) {
        var conditions = createAllConditions("Excellent", "Very Good", "Good", "Poor", "Very Poor");
        var species = createAllSpecies("European beech", "Fagus sylvatica", "Silver fir", "Abies alba", "Norway spruce", "Picea abies",
                "Oak", "Quercus robur", "Scots pine", "Pinus sylvestris", "European hornbeam", "Carpinus betulus",
                "Silver birch", "Betula pendula");
        var forests = createAllForests("Janj", "Lom", "Perućica");

        for (Forest forest : forests) {
            var inventories = createInventories(3);
            inventories.forEach(i -> forest.addInventory(i));

            for (Inventory inventory : inventories) {
                var plots = createPlots(5);
                plots.forEach(p -> inventory.addPlot(p));

                for (Plot plot : plots) {
                    var trees = createTrees(10, species, conditions);
                    trees.forEach(t -> plot.addTree(t));
                }
            }
        }

        forests.forEach(repositoryService::persist);
    }

    Random random = new Random();

    private List<Tree> createTrees(int num, List<Species> species, List<Condition> conditions) {
        var trees = new ArrayList<Tree>();

        for (int i = 0; i < num; i++) {
            var dbh = randomBigDecimal(10, 40, 2);
            var height = randomBigDecimal(1, 20, 2);
            var tree = new Tree(dbh, height, pickRandomly(species), pickRandomly(conditions), null);
            trees.add(tree);
            repositoryService.persist(tree);
        }

        return trees;
    }

    private <T> T pickRandomly(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    public BigDecimal randomBigDecimal(int min, int max, int scale) {
        BigDecimal range = BigDecimal.valueOf(max - min);
        BigDecimal fraction = BigDecimal.valueOf(random.nextDouble());
        BigDecimal result = BigDecimal.valueOf(min).add(range.multiply(fraction));
        return result.setScale(scale, RoundingMode.HALF_UP);
    }

    List<Plot> createPlots(int n) {
        var plots = new ArrayList<Plot>();
        for (int i = 0; i < n; i++) {
            var plot = new Plot();
            plots.add(plot);
            repositoryService.persist(plot);
        }
        return plots;
    }

    List<Inventory> createInventories(int n) {
        var inventories = new ArrayList<Inventory>();
        for (int i = 0; i < n; i++) {
            var inventory = new Inventory();
            inventories.add(inventory);
            repositoryService.persist(inventory);
        }
        return inventories;
    }

    List<Forest> createAllForests(String... names) {
        return Arrays.stream(names)
                .map(forests::create)
                .toList();
    }

    List<Condition> createAllConditions(String... conditions) {
        return Arrays.stream(conditions)
                .map(treeCondition::create)
                .toList();
    }

    List<Species> createAllSpecies(String... names) {
        var list = new ArrayList<Species>();
        for (int i = 0; i < names.length; i += 2) {
            var species = treeSpecies.create(names[i], names[i + 1]);
            list.add(species);
        }
        return list;
    }

    @Inject TreeSpecies treeSpecies;
    @Inject TreeCondition treeCondition;
    @Inject Forests forests;
    @Inject RepositoryService repositoryService;
}
