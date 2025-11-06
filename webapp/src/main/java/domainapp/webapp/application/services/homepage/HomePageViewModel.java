package domainapp.webapp.application.services.homepage;

import java.util.List;

import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.HomePage;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.TableDecorator;

import domainapp.modules.forest_inventory.forest.Forest;
import domainapp.modules.forest_inventory.forest.Forests;
import domainapp.modules.simple.SimpleModule;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named(SimpleModule.NAMESPACE + ".HomePageViewModel")
@DomainObject(nature = Nature.VIEW_MODEL)
@HomePage
@DomainObjectLayout()
public class HomePageViewModel {

    @ObjectSupport public String title() {
        return getForests().size() + " forests";
    }

    @Collection
    @CollectionLayout(tableDecorator = TableDecorator.DatatablesNet.class)
    public List<Forest> getForests() {
        return forests.listAll();
    }

    @Inject Forests forests;
}
