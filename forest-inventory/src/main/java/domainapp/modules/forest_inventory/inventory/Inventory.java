package domainapp.modules.forest_inventory.inventory;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.causeway.applib.annotation.BookmarkPolicy;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Publishing;
import org.apache.causeway.applib.annotation.TableDecorator;
import org.apache.causeway.applib.jaxb.PersistentEntityAdapter;
import org.apache.causeway.applib.layout.LayoutConstants;
import org.apache.causeway.persistence.jpa.applib.integration.CausewayEntityListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import domainapp.modules.forest_inventory.ForestInventoryModule;
import domainapp.modules.forest_inventory.forest.Forest;
import domainapp.modules.forest_inventory.plot.Plot;
import jakarta.inject.Named;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@Table(schema = ForestInventoryModule.SCHEMA)
@EntityListeners(CausewayEntityListener.class)
@Named(ForestInventoryModule.NAMESPACE + ".Inventory")
@DomainObject(entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout(
        tableDecorator = TableDecorator.DatatablesNet.class,
        bookmarking = BookmarkPolicy.AS_ROOT)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Inventory implements Comparable<Inventory> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.DETAILS, sequence = "2")
    private Forest forest;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    @Collection
    @CollectionLayout
    @Getter
    private final SortedSet<Plot> plots =  new TreeSet<>();

    public void addPlot(Plot plot) {
        plots.add(plot);
        plot.setInventory(this);
    }

    public String getTitle() {
        return "Inventory " + id;
    }

    public Inventory(Forest forest) {
        this.forest = forest;
    }

    private final static Comparator<Inventory> comparator =
            Comparator.comparing(Inventory::getTitle);

    @Override
    public int compareTo(final Inventory other) {
        return comparator.compare(this, other);
    }
}
