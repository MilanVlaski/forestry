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
import lombok.val;

import domainapp.modules.forest_inventory.ForestInventoryModule;
import domainapp.modules.forest_inventory.forest.ForestRepository;
import domainapp.modules.forest_inventory.inventory.InventoryRepository;
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
            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout
            final String plotSearch,
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
        val tree = new Tree(dbh, height, null, null, notes);
        try {
            val plot = plotRepository.findById(Long.valueOf(plotSearch));
            plot.ifPresent(tree::setPlot);
        } catch (NumberFormatException ignored) {}


//        inventory.setForest(forest);
//        plot.setInventory(inventory);

        return repositoryService.persist(tree);
    }


    /**
     * Search for plots.
     * @param search
     * @return a list of plot ids
     */
    public List<Long> autoComplete0CreateTree(final String search) {
        return plotRepository.findIdsByIdLike(search);
    }

}
