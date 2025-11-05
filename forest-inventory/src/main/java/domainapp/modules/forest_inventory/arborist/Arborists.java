package domainapp.modules.forest_inventory.arborist;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.applib.annotation.PromptStyle;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.applib.services.userreg.UserDetails;
import org.apache.causeway.applib.services.userreg.UserRegistrationService;
import org.apache.causeway.extensions.secman.applib.user.dom.ApplicationUserRepository;
import org.apache.causeway.extensions.secman.jpa.role.dom.ApplicationRoleRepository;
import org.apache.causeway.extensions.secman.jpa.user.dom.ApplicationUser;

import lombok.RequiredArgsConstructor;

import domainapp.modules.forest_inventory.ForestInventoryModule;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named(ForestInventoryModule.NAMESPACE + ".Arborists")
@DomainService
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Arborists {

    final UserRegistrationService userRegistrationService;
    final ApplicationUserRepository userRepository;
    final ApplicationRoleRepository roleRepository;
    final RepositoryService repositoryService;

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Arborist register(
            @Parameter String username,
            @Parameter String password,
            @Parameter String emailAddress
    ) {
        var userDetails = new UserDetails();
        userDetails.setUsername(username);
        userDetails.setPassword(password);
        userDetails.setEmailAddress(emailAddress);

        userRegistrationService.registerUser(userDetails);
        ApplicationUser user = (ApplicationUser) userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        var role = roleRepository.findByName("arborist")
                .orElseThrow(() -> new IllegalStateException("Role not found"));

        roleRepository.addRoleToUser(role, user);
        return repositoryService.persist(new Arborist(user));
    }
}
