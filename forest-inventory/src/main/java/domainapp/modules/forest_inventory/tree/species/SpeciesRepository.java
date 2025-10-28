package domainapp.modules.forest_inventory.tree.species;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeciesRepository extends JpaRepository<Species, Long> {
    Collection<Species> findByNameLikeIgnoreCase(String name);
}
