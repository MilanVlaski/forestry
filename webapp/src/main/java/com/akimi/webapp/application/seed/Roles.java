package com.akimi.webapp.application.seed;

import com.akimi.modules.forest_inventory.ForestInventoryModule;
import com.akimi.modules.forest_inventory.tree.Tree;
import org.apache.causeway.applib.services.appfeat.ApplicationFeatureId;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.extensions.secman.applib.role.fixtures.AbstractRoleAndPermissionsFixtureScript;

import static org.apache.causeway.applib.services.appfeat.ApplicationFeatureId.newFeature;
import static org.apache.causeway.applib.services.appfeat.ApplicationFeatureSort.MEMBER;
import static org.apache.causeway.applib.services.appfeat.ApplicationFeatureSort.TYPE;
import static org.apache.causeway.extensions.secman.applib.permission.dom.ApplicationPermissionMode.CHANGING;
import static org.apache.causeway.extensions.secman.applib.permission.dom.ApplicationPermissionMode.VIEWING;
import static org.apache.causeway.extensions.secman.applib.permission.dom.ApplicationPermissionRule.ALLOW;

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
                    ALLOW, CHANGING,
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
                    ALLOW, CHANGING,
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
                    ALLOW, VIEWING,
                    Can.of(newFeature(TYPE, ForestInventoryModule.NAMESPACE + ".HomePageViewModel"))
            );
            newPermissions(
                    ALLOW, CHANGING,
                    Can.of(
                            newFeature(TYPE, ForestInventoryModule.NAMESPACE + ".Plot"),
                            newFeature(TYPE, Tree.NAMESPACE + ".Tree"),
                            newFeature(TYPE, Tree.NAMESPACE + ".Trees"),
                            newFeature(TYPE, Tree.NAMESPACE + ".Condition"),
                            newFeature(TYPE, Tree.NAMESPACE + ".Species"))
            );
            newPermissions(
                    ALLOW, VIEWING,
                    Can.of(
                            newFeature(TYPE, ForestInventoryModule.NAMESPACE + ".Forest")
                    )
            );
            newPermissions(
                    ALLOW, CHANGING,
                    Can.of(
                            newFeature(MEMBER, ForestInventoryModule.NAMESPACE + ".Forests#listAll"),
                            newFeature(MEMBER, ForestInventoryModule.NAMESPACE + ".Inventories#listAll"),
                            newFeature(MEMBER, ForestInventoryModule.NAMESPACE + ".Plots#listAll"),
                            newFeature(MEMBER, ForestInventoryModule.NAMESPACE + ".Arborists#listAll"),
                            newFeature(MEMBER, ForestInventoryModule.NAMESPACE + ".Forests#listAll"),
                            newFeature(MEMBER, Tree.NAMESPACE + ".Species#listAll"),
                            newFeature(MEMBER, Tree.NAMESPACE + ".Conditions#listAll")
                    )
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
                    ALLOW, CHANGING,
                    Can.of(newFeature(TYPE,
                            ForestInventoryModule.NAMESPACE + ".Arborists")
                    )
            );
        }
    }

}
