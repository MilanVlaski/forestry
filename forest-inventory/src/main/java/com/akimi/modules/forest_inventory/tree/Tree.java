package com.akimi.modules.forest_inventory.tree;

import com.akimi.modules.forest_inventory.ForestInventoryModule;
import com.akimi.modules.forest_inventory.plot.Plot;
import com.akimi.modules.forest_inventory.tree.condition.Condition;
import com.akimi.modules.forest_inventory.tree.species.Species;
import com.akimi.modules.forest_inventory.types.Notes;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;
import org.apache.causeway.applib.annotation.*;
import org.apache.causeway.applib.jaxb.PersistentEntityAdapter;
import org.apache.causeway.applib.layout.LayoutConstants;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.applib.services.user.UserService;
import org.apache.causeway.extensions.secman.applib.user.dom.ApplicationUserRepository;
import org.apache.causeway.extensions.secman.jpa.user.dom.ApplicationUser;
import org.apache.causeway.persistence.jpa.applib.integration.CausewayEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;

import static com.akimi.modules.forest_inventory.tree.Tree.NAMESPACE;

@Entity
@Table(schema = ForestInventoryModule.SCHEMA)
@EntityListeners(CausewayEntityListener.class)
@Named(NAMESPACE + ".Tree")
@DomainObject(entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout(
        tableDecorator = TableDecorator.DatatablesNet.class,
        bookmarking = BookmarkPolicy.AS_ROOT)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Tree implements Comparable<Tree> {

    public static final String NAMESPACE = ForestInventoryModule.NAMESPACE + ".tree";

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
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.DETAILS, sequence = "2")
    private BigDecimal height_m;

    @ManyToOne
    @Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.DETAILS, sequence = "3")
    private Species species;

    @ManyToOne
    @Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.DETAILS, sequence = "4")
    private Condition condition;


    @ManyToOne
    @Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.DETAILS, sequence = "5")
    private Plot plot;

    @ManyToOne(cascade = CascadeType.ALL)
    @Getter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.METADATA, sequence = "6")
    private ApplicationUser createdBy;

    @Column
    @Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.METADATA, sequence = "7")
    private LocalDateTime createdAt;

    @Notes
    @Column(length = Notes.MAX_LEN, nullable = true)
    @Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.DETAILS, sequence = "90")
    private String notes;

    @Inject @Transient UserService userService;
    @Inject @Transient RepositoryService repositoryService;
    @Inject @Transient ApplicationUserRepository applicationUserRepository;

    public Tree(BigDecimal dbh, BigDecimal height, Species species, Condition condition, String notes) {
        this.dbh_cm = dbh;
        this.height_m = height;
        this.species = species;
        this.condition = condition;
        this.notes = notes;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        var userName = userService.currentUserName().orElseThrow(() -> new IllegalStateException("User not found."));
        this.createdBy = (ApplicationUser) applicationUserRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalStateException("User not found."));
    }

    @ObjectSupport
    public String title() {
        return "Tree " + id;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    @ActionLayout(associateWith = "plot")
    public Tree assignToPlot(Plot plot) {
        plot.addTree(this);
        return this;
    }

    @MemberSupport
    public Collection<Plot> choices0AssignToPlot() {
        return repositoryService.allInstances(Plot.class);
    }

    private final static Comparator<Tree> comparator =
            Comparator.comparing(Tree::title);

    @Override
    public int compareTo(final Tree other) {
        return comparator.compare(this, other);
    }
}
