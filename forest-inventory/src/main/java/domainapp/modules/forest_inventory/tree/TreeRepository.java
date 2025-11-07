package domainapp.modules.forest_inventory.tree;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.apache.causeway.extensions.secman.applib.user.dom.ApplicationUser;

import domainapp.modules.forest_inventory.forest.Forest;
import domainapp.modules.forest_inventory.inventory.Inventory;
import domainapp.modules.forest_inventory.plot.Plot;

interface TreeRepository extends JpaRepository<Tree, Long> {
    @Query("""
            select t from Tree t
            join t.plot p
            join p.inventory i
            join i.forest f
            where f = :forest
            """)
    List<Tree> findByForest(@Param("forest") Forest forest);
    @Query("""
            select t from Tree t
            join t.plot p
            join p.inventory i
            where i = :inventory
            """)
    List<Tree> findByInventory(@Param("inventory") Inventory inventory);
    List<Tree> findByPlot(Plot plot);

    @Query("""
            select t from Tree t
            join t.plot p
            join p.inventory i
            join i.forest f
            where i = :inventory or f = :forest
            """)
    List<Tree> findSpecific(
            @Param("forest") Forest forest,
            @Param("inventory") Inventory inventory
    );

    @Query("""
            select t from Tree t
            join t.plot p
            join p.inventory i
            where i = :inventory or p = :plot
            """)
    List<Tree> findSpecific(
            @Param("inventory") Inventory inventory,
            @Param("plot") Plot plot
    );


    List<Tree> findByCreatedBy(ApplicationUser user);
}
