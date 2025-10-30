package domainapp.modules.forest_inventory.tree;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.MemberSupport;
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
import domainapp.modules.forest_inventory.plot.Plot;
import domainapp.modules.forest_inventory.tree.condition.Condition;
import domainapp.modules.forest_inventory.tree.species.Species;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named(ForestInventoryModule.NAMESPACE + ".Trees")
@DomainService
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class Trees {

    final RepositoryService repositoryService;

    // TODO paging
    @Action(semantics = SemanticsOf.SAFE)
    public List<Tree> listAll() {
        return repositoryService.allInstances(Tree.class);
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_MODAL)
    public Tree addTree(
            @Parameter @ParameterLayout
            final BigDecimal dbh,
            @Parameter @ParameterLayout
            final BigDecimal height,
            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout
            final Species species,
            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout
            final Condition condition,
            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout
            final String notes,
//            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout
//            final Forest forest,
//            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout
//            final Inventory inventory,
            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout
            final Plot plot
    ) {
        val tree = new Tree(dbh, height, species, condition, notes);
        if(plot != null) {
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

//    @MemberSupport
//    public java.util.Collection<Forest> choices5AddTree() {
//        return repositoryService.allInstances(Forest.class);
//    }
//
//    @MemberSupport
//    public java.util.Collection<Inventory> choices6AddTree() {
//        return repositoryService.allInstances(Inventory.class);
//    }

    @MemberSupport
    public java.util.Collection<Plot> choices5AddTree() {
        return repositoryService.allInstances(Plot.class);
    }
}
