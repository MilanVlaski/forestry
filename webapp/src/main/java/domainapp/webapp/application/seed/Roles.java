package domainapp.webapp.application.seed;

import org.apache.causeway.applib.services.appfeat.ApplicationFeatureId;
import org.apache.causeway.applib.services.appfeat.ApplicationFeatureSort;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.extensions.secman.applib.permission.dom.ApplicationPermissionMode;
import org.apache.causeway.extensions.secman.applib.permission.dom.ApplicationPermissionRule;
import org.apache.causeway.extensions.secman.applib.role.fixtures.AbstractRoleAndPermissionsFixtureScript;

import domainapp.modules.forest_inventory.ForestInventoryModule;

/**
 * Contains all possible Roles and Users.
 */
public class Roles {

    public static class SimpleModuleSuperuserRole extends AbstractRoleAndPermissionsFixtureScript {

        public static final String ROLE_NAME = "simple-superuser";

        public SimpleModuleSuperuserRole() {
            super(ROLE_NAME, "Permission to use everything in the 'simple' module");
        }

        @Override
        protected void execute(ExecutionContext executionContext) {
            newPermissions(
                    ApplicationPermissionRule.ALLOW,
                    ApplicationPermissionMode.CHANGING,
                    Can.of(ApplicationFeatureId.newNamespace("simple"))
            );
        }
    }


    public static class ForestModuleSuperuserRole extends AbstractRoleAndPermissionsFixtureScript {

        public static final String ROLE_NAME = "forest-superuser";

        public ForestModuleSuperuserRole() {
            super(ROLE_NAME, "Permission to use everything in the 'forest' module");
        }

        @Override
        protected void execute(ExecutionContext executionContext) {
            newPermissions(
                    ApplicationPermissionRule.ALLOW,
                    ApplicationPermissionMode.CHANGING,
                    Can.of(ApplicationFeatureId.newNamespace("forest"))
            );
        }
    }

    public static class ArboristRole extends AbstractRoleAndPermissionsFixtureScript {

        public static final String ROLE_NAME = "arborist";

        public ArboristRole() {
            super(ROLE_NAME, "Permission to add Trees, and assign them to Plots.");
        }

        @Override
        protected void execute(ExecutionContext executionContext) {
            newPermissions(
                    ApplicationPermissionRule.ALLOW,
                    ApplicationPermissionMode.CHANGING,
                    Can.of(ApplicationFeatureId.newFeature(ApplicationFeatureSort.TYPE,
                                    ForestInventoryModule.NAMESPACE + ".Tree"),
                            ApplicationFeatureId.newFeature(ApplicationFeatureSort.TYPE,
                                    ForestInventoryModule.NAMESPACE + ".Trees"),
                            ApplicationFeatureId.newFeature(ApplicationFeatureSort.TYPE,
                                    ForestInventoryModule.NAMESPACE + ".Plot"),
                            ApplicationFeatureId.newFeature(ApplicationFeatureSort.TYPE,
                                    ForestInventoryModule.NAMESPACE + ".Condition"),
                            ApplicationFeatureId.newFeature(ApplicationFeatureSort.TYPE,
                                    ForestInventoryModule.NAMESPACE + ".Species"))
            );
        }
    }

    /**
     * Currently unused, but can be used once a distinction should be made between a role that can manage forests,
     * but not administer any security.
     */
    public static class ForestManagerRole extends AbstractRoleAndPermissionsFixtureScript {

        public static final String ROLE_NAME = "forest-manager";

        public ForestManagerRole() {
            super(ROLE_NAME, "Permission to register Arborists.");
        }

        @Override
        protected void execute(ExecutionContext executionContext) {
            newPermissions(
                    ApplicationPermissionRule.ALLOW,
                    ApplicationPermissionMode.CHANGING,
                    Can.of(ApplicationFeatureId.newFeature(ApplicationFeatureSort.TYPE,
                            ForestInventoryModule.NAMESPACE + ".Arborists")
                    )
            );
        }
    }

}
