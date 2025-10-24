package domainapp.modules.forest_inventory.tree;

import java.math.BigDecimal;
import java.util.List;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.applib.annotation.PromptStyle;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.repository.RepositoryService;

import lombok.RequiredArgsConstructor;

import domainapp.modules.forest_inventory.ForestInventoryModule;
import domainapp.modules.forest_inventory.forest.ForestRepository;
import domainapp.modules.forest_inventory.inventory.InventoryRepository;
import domainapp.modules.forest_inventory.plot.Plot;
import domainapp.modules.forest_inventory.plot.PlotRepository;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named(ForestInventoryModule.NAMESPACE + ".Trees")
@DomainService
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class Trees {

    final RepositoryService repositoryService;
    final ForestRepository forestRepository;
    final InventoryRepository inventoryRepository;
    final PlotRepository plotRepository;

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_MODAL)
    public Tree createTree(
//            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout
//            final Forest forest,
//            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout
//            final Inventory inventory,
//            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout
//            final Plot plot,
            @Parameter @ParameterLayout
            final BigDecimal dbh,
            @Parameter @ParameterLayout
            final BigDecimal height,
//            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout
//            final Species species,
//            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout
//            final Condition condition,
            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout
            final String notes
    ) {
        // User searches for a Forest name. If it doesn't exist, a new Forest gets createTreed.
        // Users searches for an Inventory. If it doesn't exist, a new Inventory gets createTreed.
        // Users searches for a Plot. If it doesn't match an existing, a new Plot gets createTreed.
        // dbh and height are mandatory
        // species is searched for, and condition as well, from the existing entities, but
        // they are optional. Note is also optional.
        var tree = new Tree(dbh, height, null, null, notes);
//        tree.setPlot(plot);

//        inventory.setForest(forest);
//        plot.setInventory(inventory);

        return repositoryService.persist(tree);
    }


    public List<Plot> autoComplete0CreateTree(final String search) {
        return repositoryService.allInstances(Plot.class);
    }

}
