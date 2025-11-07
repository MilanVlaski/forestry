package domainapp.modules.forest_inventory.tree.condition;

import java.math.BigDecimal;
import java.util.List;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.applib.annotation.PromptStyle;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.repository.RepositoryService;

import lombok.RequiredArgsConstructor;

import domainapp.modules.forest_inventory.tree.Tree;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named(Tree.NAMESPACE + ".Conditions")
@DomainService
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class TreeCondition {

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Condition create(String condition, BigDecimal level) {
        return repositoryService.persist(new Condition(condition, level));
    }

    @Action(semantics = SemanticsOf.SAFE_AND_REQUEST_CACHEABLE)
    public List<Condition> listAll() {
        return repositoryService.allInstances(Condition.class);
    }

    @Inject RepositoryService repositoryService;
}
