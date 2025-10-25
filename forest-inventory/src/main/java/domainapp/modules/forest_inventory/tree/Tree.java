package domainapp.modules.forest_inventory.tree;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;

import org.apache.causeway.applib.annotation.BookmarkPolicy;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Publishing;
import org.apache.causeway.applib.annotation.TableDecorator;
import org.apache.causeway.applib.jaxb.PersistentEntityAdapter;
import org.apache.causeway.applib.layout.LayoutConstants;
import org.apache.causeway.applib.services.user.UserService;
import org.apache.causeway.persistence.jpa.applib.integration.CausewayEntityListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import domainapp.modules.forest_inventory.ForestInventoryModule;
import domainapp.modules.forest_inventory.plot.Plot;
import domainapp.modules.forest_inventory.tree.condition.Condition;
import domainapp.modules.forest_inventory.tree.species.Species;
import domainapp.modules.forest_inventory.types.Notes;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@Table(schema = ForestInventoryModule.SCHEMA)
@EntityListeners(CausewayEntityListener.class)
@Named(ForestInventoryModule.NAMESPACE + ".Tree")
@DomainObject(entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout(
        tableDecorator = TableDecorator.DatatablesNet.class,
        bookmarking = BookmarkPolicy.AS_ROOT)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Tree implements Comparable<Tree> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(precision = 7, scale = 2, nullable = false)
    @Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.DETAILS, sequence = "1")
    private BigDecimal dbh_cm;

    @Column(precision = 5, scale = 2, nullable = false)
    @Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.DETAILS, sequence = "1")
    private BigDecimal height_m;

    @ManyToOne
    @Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.DETAILS, sequence = "1")
    private Condition condition;

    @ManyToOne
    @Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.DETAILS, sequence = "1")
    private Plot plot;

    @ManyToOne
    @Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.DETAILS, sequence = "1")
    private Species species;

    @Notes
    @Column(length = Notes.MAX_LEN, nullable = true)
    @Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.DETAILS, sequence = "2")
    private String notes;


    @Column
    @Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.METADATA, sequence = "2")
    private String createdBy;

    @Column
    @Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.METADATA, sequence = "3")
    private LocalDateTime createdAt;

    @Inject @Transient UserService userService;

    public Tree(BigDecimal dbh, BigDecimal height, Species species, Condition condition, String notes) {
        this.dbh_cm = dbh;
        this.height_m = height;
        this.species = species;
        this.condition = condition;
        this.notes = notes;
    }

    @PrePersist
    public void onCreate() {
        // TODO make this a relationship to user
        this.createdAt = LocalDateTime.now();
        this.createdBy = userService.currentUserNameElseNobody();
    }

    public String getTitle() {
        return "Tree " + id;
    }

    private final static Comparator<Tree> comparator =
            Comparator.comparing(Tree::getTitle);

    @Override
    public int compareTo(final Tree other) {
        return comparator.compare(this, other);
    }
}
