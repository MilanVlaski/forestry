package domainapp.modules.forest_inventory.tree;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import domainapp.modules.forest_inventory.forest.Forest;

interface TreeRepository extends JpaRepository<Tree, Long> {
    @Query("""
            select t from Tree t
            join t.plot p
            join p.inventory i
            join i.forest f
            where f = :forest
            """)
    List<Tree> findByForest(@Param("forest") Forest forest);
}
