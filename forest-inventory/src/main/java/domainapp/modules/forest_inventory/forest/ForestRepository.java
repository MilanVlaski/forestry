package domainapp.modules.forest_inventory.forest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ForestRepository extends JpaRepository<Forest, Long> {

    List<Forest> findByNameContaining(final String name);

    Forest findByName(final String name);

}
