package domainapp.modules.forest_inventory.tree.species;

import java.util.ArrayList;
import java.util.List;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.applib.annotation.PromptStyle;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.repository.RepositoryService;

import lombok.RequiredArgsConstructor;
import lombok.val;

import domainapp.modules.forest_inventory.ForestInventoryModule;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named(ForestInventoryModule.NAMESPACE + ".TreeSpecies")
@DomainService
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class TreeSpecies {

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Species create(String name, String latinName) {
        return repositoryService.persist(new Species(name, latinName));
    }

    public List<Species> createAll(String... names) {
        val list = new ArrayList<Species>();
        for( int i=0; i<names.length; i+=2 ) {
            Species species = new Species(names[i], names[i + 1]);
            list.add(species);
            repositoryService.persist(species);
        }
        return list;
    }

    @Inject RepositoryService repositoryService;
}
