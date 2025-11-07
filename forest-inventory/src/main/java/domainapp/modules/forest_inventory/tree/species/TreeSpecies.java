package domainapp.modules.forest_inventory.tree.species;

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

@Named(Tree.NAMESPACE + ".TreeSpecies")
@DomainService
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class TreeSpecies {

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Species create(String name, String latinName) {
        return repositoryService.persist(new Species(name, latinName));
    }

    @Action(semantics = SemanticsOf.SAFE_AND_REQUEST_CACHEABLE)
    public List<Species> listAll() {
        return repositoryService.allInstances(Species.class);
    }

    @Inject RepositoryService repositoryService;
}
