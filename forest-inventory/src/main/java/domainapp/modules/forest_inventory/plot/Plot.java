package domainapp.modules.forest_inventory.plot;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.BookmarkPolicy;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.PromptStyle;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Publishing;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.annotation.TableDecorator;
import org.apache.causeway.applib.jaxb.PersistentEntityAdapter;
import org.apache.causeway.applib.layout.LayoutConstants;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.persistence.jpa.applib.integration.CausewayEntityListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

import domainapp.modules.forest_inventory.ForestInventoryModule;
import domainapp.modules.forest_inventory.inventory.Inventory;
import domainapp.modules.forest_inventory.tree.Tree;
import domainapp.modules.forest_inventory.tree.condition.Condition;
import domainapp.modules.forest_inventory.tree.species.Species;
import jakarta.inject.Inject;
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
import jakarta.persistence.Transient;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@Table(schema = ForestInventoryModule.SCHEMA)
@EntityListeners(CausewayEntityListener.class)
@Named(ForestInventoryModule.NAMESPACE + ".Plot")
@DomainObject(entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout(
        tableDecorator = TableDecorator.DatatablesNet.class,
        bookmarking = BookmarkPolicy.AS_ROOT)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Plot implements Comparable<Plot> {

    @Id
    @Getter
    @Property
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.DETAILS, sequence = "2")
    private Inventory inventory;

    @OneToMany(mappedBy = "plot", cascade = CascadeType.ALL)
    @Collection
    @CollectionLayout
    @Getter
    private Set<Tree> trees = new TreeSet<>();

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(associateWith = "trees", promptStyle = PromptStyle.DIALOG_MODAL)
    public Plot addTree(
            @Parameter @ParameterLayout
            final BigDecimal dbh,
            @Parameter @ParameterLayout
            final BigDecimal height,
            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout
            final Species species,
            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout
            final Condition condition,
            @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout
            final String notes
    ) {
        val tree = new Tree(dbh, height, species, condition, notes);
        this.addTree(tree);
        return this;
    }

    // TODO copy pasted from Trees. Is there a better way?
    // Maybe if we register this somewhere.
    @MemberSupport
    public java.util.Collection<Species> choices2AddTree() {
        return repositoryService.allInstances(Species.class);
    }

    @MemberSupport
    public java.util.Collection<Condition> choices3AddTree() {
        return repositoryService.allInstances(Condition.class);
    }



    @ObjectSupport
    public String title() {
        return "Plot " + id;
    }

    public Plot(Inventory inventory) {
        this.inventory = inventory;
    }

    private final static Comparator<Plot> comparator =
            Comparator.comparing(Plot::title);

    @Override
    public int compareTo(final Plot other) {
        return comparator.compare(this, other);
    }

    public void addTree(Tree tree) {
        this.trees.add(tree);
        tree.setPlot(this);
    }

    @Transient @Inject RepositoryService repositoryService;
}
