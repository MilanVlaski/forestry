package domainapp.modules.forest_inventory.tree;

import domainapp.modules.forest_inventory.ForestInventoryModule;
import domainapp.modules.forest_inventory.forest.Forest;
import domainapp.modules.forest_inventory.inventory.Inventory;
import domainapp.modules.forest_inventory.plot.Plot;
import domainapp.modules.forest_inventory.plot.PlotRepository;
import domainapp.modules.forest_inventory.tree.condition.Condition;
import domainapp.modules.forest_inventory.tree.species.Species;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.causeway.applib.annotation.*;
import org.apache.causeway.applib.services.repository.RepositoryService;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Named(ForestInventoryModule.NAMESPACE + ".Trees")
@DomainService
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class Trees {

    final RepositoryService repositoryService;
    private final PlotRepository plotRepository;
    private final TreeRepository treeRepository;

    // TODO paging
    @Action(semantics = SemanticsOf.SAFE)
    public List<Tree> listAll() {
        return repositoryService.allInstances(Tree.class);
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_MODAL)
    public Tree addTree(
            @Parameter @ParameterLayout final BigDecimal dbh,
            @Parameter @ParameterLayout final BigDecimal height,
            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout final Species species,
            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout final Condition condition,
            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout final String notes,
            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout final Plot plot
    ) {
        val tree = new Tree(dbh, height, species, condition, notes);
        if (plot != null) {
            plot.addTree(tree);
        }
        return repositoryService.persist(tree);
    }

    @Action(semantics = SemanticsOf.SAFE )
    public List<Tree> allTreesInForest(@Parameter Forest forest) {
        return treeRepository.findByForest(forest);
    }

    @MemberSupport
    public Collection<Forest> choices0AllTreesInForest() {
        return repositoryService.allInstances(Forest.class);
    }

    public List<Tree> findTree(
            @Parameter Forest forest,
            @Parameter Inventory inventory,
            @Parameter Plot plot
    ) {
        return Collections.emptyList();
    }


    public List<Tree> allTreesOfInventory(
            @Parameter Inventory inventory
    ) {
        return Collections.emptyList();
    }

    public List<Tree> allTreesInPlot(
            @Parameter Plot plot
    ) {
        return Collections.emptyList();
    }
    public List<Tree> allTreesCreatedByUser(
            @Parameter String username
    ) {
        return Collections.emptyList();
    }
    public List<Tree> allTreesAddedByArborist(
            @Parameter String username
    ) {
        return Collections.emptyList();
    }

    @MemberSupport
    public Collection<Species> choices2AddTree() {
        return repositoryService.allInstances(Species.class);
    }

    @MemberSupport
    public Collection<Condition> choices3AddTree() {
        return repositoryService.allInstances(Condition.class);
    }

    @MemberSupport
    public java.util.Collection<Plot> autoComplete5AddTree(String search) {
        return plotRepository.findByIdLike(search);
    }
}
