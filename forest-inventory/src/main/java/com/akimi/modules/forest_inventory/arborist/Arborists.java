package com.akimi.modules.forest_inventory.arborist;

import java.util.List;

import com.akimi.modules.forest_inventory.ForestInventoryModule;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.applib.services.userreg.UserDetails;
import org.apache.causeway.applib.services.userreg.UserRegistrationService;
import org.apache.causeway.core.config.CausewayConfiguration;
import org.apache.causeway.extensions.secman.applib.user.dom.ApplicationUserRepository;
import org.apache.causeway.extensions.secman.jpa.role.dom.ApplicationRoleRepository;
import org.apache.causeway.extensions.secman.jpa.user.dom.ApplicationUser;

import lombok.RequiredArgsConstructor;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named(ForestInventoryModule.NAMESPACE + ".Arborists")
@DomainService
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class Arborists {

    final UserRegistrationService userRegistrationService;
    final ApplicationUserRepository userRepository;
    final ApplicationRoleRepository roleRepository;
    final RepositoryService repositoryService;
    final ArboristRepository arboristRepository;

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    @ActionLayout
    public ApplicationUser register(
            @Parameter String username,
            @Parameter String password,
            @Parameter String emailAddress
    ) {
        var userDetails = new UserDetails();
        userDetails.setUsername(username);
        userDetails.setPassword(password);
        userDetails.setEmailAddress(emailAddress);

        userRegistrationService.registerUser(userDetails);
        var user = (ApplicationUser) userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        // TODO replace magic 'arborist' string
        var role = roleRepository.findByName("arborist")
                .orElseThrow(() -> new IllegalStateException("Role not found"));
        var defaultRole = roleRepository.findByName(
                        causewayConfiguration.getExtensions().getSecman()
                                .getSeed().getRegularUser().getRoleName()
                )
                .orElseThrow(() -> new IllegalStateException("Role not found"));

        roleRepository.addRoleToUser(role, user);
        roleRepository.addRoleToUser(defaultRole, user);
        return repositoryService.persist(user);
    }

    @Action(semantics = SemanticsOf.SAFE)
    public List<ApplicationUser> listAll() {
        return arboristRepository.findAllArborists();
    }

    @Inject CausewayConfiguration causewayConfiguration;
}
