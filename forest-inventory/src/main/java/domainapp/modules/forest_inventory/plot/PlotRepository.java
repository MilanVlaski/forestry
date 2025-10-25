package domainapp.modules.forest_inventory.plot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlotRepository extends JpaRepository<Plot, Long> {
    @Query("select p.id from Plot p where p.id like :id")
    List<Long> findIdsByIdLike(@Param("id") String id);
}
