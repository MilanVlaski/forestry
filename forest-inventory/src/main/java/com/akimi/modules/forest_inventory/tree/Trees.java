package com.akimi.modules.forest_inventory.tree;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import com.akimi.modules.forest_inventory.arborist.ArboristRepository;
import com.akimi.modules.forest_inventory.forest.Forest;
import com.akimi.modules.forest_inventory.inventory.Inventory;
import com.akimi.modules.forest_inventory.inventory.InventoryRepository;
import com.akimi.modules.forest_inventory.plot.Plot;
import com.akimi.modules.forest_inventory.plot.PlotRepository;
import com.akimi.modules.forest_inventory.tree.condition.Condition;
import com.akimi.modules.forest_inventory.tree.species.Species;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.PromptStyle;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.extensions.secman.applib.user.dom.ApplicationUser;
import org.apache.causeway.extensions.secman.applib.user.dom.ApplicationUserRepository;

import lombok.RequiredArgsConstructor;
import lombok.val;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named(Tree.NAMESPACE + ".Trees")
@DomainService
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class Trees {

    final RepositoryService repositoryService;
    final PlotRepository plotRepository;
    final TreeRepository treeRepository;
    final InventoryRepository inventoryRepository;
    final ApplicationUserRepository applicationUserRepository;
    final ArboristRepository arboristRepository;

    // TODO paging
    @Action(semantics = SemanticsOf.SAFE)
    public List<Tree> listAll() {
        return repositoryService.allInstances(Tree.class);
    }

    // Remove 'final', as it's verbose.
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_MODAL)
    public Tree addTree(
            @Parameter @ParameterLayout final BigDecimal dbh,
            @Parameter @ParameterLayout final BigDecimal height,

            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout final Species species,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout final Condition condition,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout final Plot plot,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout final String notes
    ) {
        val tree = new Tree(dbh, height, species, condition, notes);
        if (plot != null) {
            plot.addTree(tree);
        }
        return repositoryService.persist(tree);
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
    public java.util.Collection<Plot> autoComplete4AddTree(String search) {
        return plotRepository.findByIdLike(search);
    }

    @Action(semantics = SemanticsOf.SAFE)
    public List<Tree> allTreesInForest(@Parameter Forest forest) {
        return treeRepository.findByForest(forest);
    }

    @Action(semantics = SemanticsOf.SAFE)
    public List<Tree> allTreesOfInventory(@Parameter Inventory inventory) {
        return treeRepository.findByInventory(inventory);
    }

    @MemberSupport
    public Collection<Inventory> choices0AllTreesOfInventory() {
        return inventoryRepository.findAll();
    }

    @MemberSupport
    public Collection<Forest> choices0AllTreesInForest() {
        return repositoryService.allInstances(Forest.class);
    }

    @Action(semantics = SemanticsOf.SAFE)
    public List<Tree> allTreesInForestInventory(
            @Parameter Forest forest,
            @Parameter Inventory inventory
    ) {
        return treeRepository.findSpecific(forest, inventory);
    }

    @MemberSupport
    public Collection<Forest> choices0AllTreesInForestInventory() {
        return repositoryService.allInstances(Forest.class);
    }

    @MemberSupport
    public Collection<Inventory> choices1AllTreesInForestInventory(Forest forest) {
        return inventoryRepository.findByForest(forest);
    }

    @Action(semantics = SemanticsOf.SAFE)
    public List<Tree> allTreesOfInventoryPlot(
            @Parameter Inventory inventory,
            @Parameter Plot plot
    ) {
        return treeRepository.findSpecific(inventory, plot);
    }

    @MemberSupport
    public Collection<Inventory> choices0AllTreesOfInventoryPlot() {
        return repositoryService.allInstances(Inventory.class);
    }

    @MemberSupport
    public Collection<Plot> choices1AllTreesOfInventoryPlot(Inventory inventory) {
        return plotRepository.findByInventory(inventory);
    }

    @Action(semantics = SemanticsOf.SAFE)
    public List<Tree> allTreesInPlot(@Parameter Plot plot) {
        return treeRepository.findByPlot(plot);
    }

    @MemberSupport
    public Collection<Plot> autoComplete0AllTreesInPlot(String search) {
        return plotRepository.findByIdLike(search);
    }

    @Action(semantics = SemanticsOf.SAFE)
    public List<Tree> allTreesCreatedByUser(@Parameter String userName) {
        var user = getUser(userName);
        return treeRepository.findByCreatedBy(user);
    }

    @MemberSupport
    public Collection<String> autoComplete0AllTreesCreatedByUser(String search) {
        return applicationUserRepository.findMatching(search).stream()
                .map(ApplicationUser::getUsername)
                .toList();
    }

    @Action(semantics = SemanticsOf.SAFE)
    public List<Tree> allTreesCreatedByArborist(@Parameter String userName) {
        var user = getUser(userName);
        return treeRepository.findByCreatedBy(user);
    }

    @Programmatic
    private ApplicationUser getUser(String userName) {
        return applicationUserRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalStateException("User not found."));
    }

    @MemberSupport
    public Collection<String> autoComplete0AllTreesCreatedByArborist(String search) {
        return arboristRepository.findAllArboristUsernamesWithUsernameLike(search);
    }
}
