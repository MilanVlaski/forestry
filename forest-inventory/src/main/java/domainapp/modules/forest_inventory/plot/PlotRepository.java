package domainapp.modules.forest_inventory.plot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlotRepository extends JpaRepository<Plot, Long> {

    // May not work in all DBs
    @Query("SELECT p FROM Plot p WHERE concat(p.id, '') LIKE %:id%")
    List<Plot> findByIdLike(@Param("id") String id);
}

