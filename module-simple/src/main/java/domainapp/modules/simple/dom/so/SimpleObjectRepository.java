package domainapp.modules.simple.dom.so;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleObjectRepository extends JpaRepository<SimpleObject, Long> {

    List<SimpleObject> findByNameContaining(String name);

    SimpleObject findByName(String name);

}
