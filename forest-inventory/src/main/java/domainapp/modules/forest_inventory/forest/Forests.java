package domainapp.modules.forest_inventory.forest;

import java.util.List;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.applib.annotation.PromptStyle;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.persistence.jpa.applib.services.JpaSupportService;

import lombok.RequiredArgsConstructor;

import domainapp.modules.forest_inventory.ForestInventoryModule;
import domainapp.modules.forest_inventory.types.Name;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.TypedQuery;

@Named(ForestInventoryModule.NAMESPACE + ".Forests")
@DomainService
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Forests {

    final RepositoryService repositoryService;
    final JpaSupportService jpaSupportService;
    final ForestRepository forestRepository;


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Forest create(
            @Name final String name) {
        return repositoryService.persist(Forest.withName(name));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Forest> findByName(
            @Name final String name
            ) {
        return forestRepository.findByNameContaining(name);
    }


    public Forest findByNameExact(final String name) {
        return forestRepository.findByName(name);
    }



    @Action(semantics = SemanticsOf.SAFE)
    public List<Forest> listAll() {
        return forestRepository.findAll();
    }



    public void ping() {
        jpaSupportService.getEntityManager(Forest.class)
            .mapEmptyToFailure()
            .mapSuccessAsNullable(entityManager -> {
                final TypedQuery<Forest> q = entityManager.createQuery(
                                "SELECT p FROM Forest p ORDER BY p.name",
                                Forest.class)
                        .setMaxResults(1);
                return q.getResultList();
            })
        .ifFailureFail();
    }
}
