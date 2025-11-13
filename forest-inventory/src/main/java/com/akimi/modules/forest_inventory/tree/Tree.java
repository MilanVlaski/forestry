package com.akimi.modules.forest_inventory.tree;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;

import com.akimi.modules.forest_inventory.ForestInventoryModule;
import com.akimi.modules.forest_inventory.plot.Plot;
import com.akimi.modules.forest_inventory.tree.condition.Condition;
import com.akimi.modules.forest_inventory.tree.species.Species;
import com.akimi.modules.forest_inventory.tree.types.Dbh;
import com.akimi.modules.forest_inventory.tree.types.TreeHeight;
import com.akimi.modules.forest_inventory.types.Notes;

import static com.akimi.modules.forest_inventory.tree.Tree.NAMESPACE;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.BookmarkPolicy;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Publishing;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.annotation.TableDecorator;
import org.apache.causeway.applib.jaxb.PersistentEntityAdapter;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.applib.services.user.UserService;
import org.apache.causeway.extensions.secman.applib.user.dom.ApplicationUserRepository;
import org.apache.causeway.extensions.secman.jpa.user.dom.ApplicationUser;
import org.apache.causeway.persistence.jpa.applib.integration.CausewayEntityListener;

import static org.apache.causeway.applib.layout.LayoutConstants.FieldSetId.DETAILS;
import static org.apache.causeway.applib.layout.LayoutConstants.FieldSetId.METADATA;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.CascadeType;
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

    @Dbh
    @Column(precision = 7, scale = 2, nullable = false)
    @Getter @Setter
    @PropertyLayout(fieldSetId = DETAILS, sequence = "1")
    private BigDecimal dbhCm;

    @TreeHeight
    @Column(precision = 5, scale = 2, nullable = false)
    @Getter @Setter
    @PropertyLayout(fieldSetId = DETAILS, sequence = "2")
    private BigDecimal heightM;

    @ManyToOne
    @Getter @Setter
    @Property
    @PropertyLayout(fieldSetId = DETAILS, sequence = "3")
    private Species species;

    @ManyToOne
    @Getter @Setter
    @Property
    @PropertyLayout(fieldSetId = DETAILS, sequence = "4")
    private Condition condition;


    @ManyToOne
    @Getter @Setter
    @Property
    @PropertyLayout(fieldSetId = DETAILS, sequence = "5")
    private Plot plot;

    @ManyToOne(cascade = CascadeType.ALL)
    @Getter
    @Property
    @PropertyLayout(fieldSetId = METADATA, sequence = "6")
    private ApplicationUser createdBy;

    @Column
    @Getter @Setter
    @Property
    @PropertyLayout(fieldSetId = METADATA, sequence = "7")
    private LocalDateTime createdAt;

    @Notes
    @Column(length = Notes.MAX_LEN, nullable = true)
    @Getter @Setter
    @Property
    @PropertyLayout(fieldSetId = DETAILS, sequence = "90")
    private String notes;

    @Inject @Transient UserService userService;
    @Inject @Transient RepositoryService repositoryService;
    @Inject @Transient ApplicationUserRepository applicationUserRepository;

    public Tree(BigDecimal dbh, BigDecimal height, Species species,
                Condition condition, String notes) {
        this.dbhCm = dbh;
        this.heightM = height;
        this.species = species;
        this.condition = condition;
        this.notes = notes;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        var userName = userService.currentUserName()
                .orElseThrow(() -> new IllegalStateException("User not found."));
        this.createdBy = (ApplicationUser) applicationUserRepository
                .findByUsername(userName)
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

    private static final Comparator<Tree> COMPARATOR =
            Comparator.comparing(Tree::title);

    @Override
    public int compareTo(final Tree other) {
        return COMPARATOR.compare(this, other);
    }
}
