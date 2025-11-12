package com.akimi.modules.forest_inventory.forest;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.akimi.modules.forest_inventory.ForestInventoryModule;
import com.akimi.modules.forest_inventory.inventory.Inventory;
import com.akimi.modules.forest_inventory.types.Name;
import com.akimi.modules.forest_inventory.types.Notes;

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.BookmarkPolicy;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.PromptStyle;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Publishing;
import org.apache.causeway.applib.annotation.TableDecorator;
import org.apache.causeway.applib.annotation.Title;
import org.apache.causeway.applib.jaxb.PersistentEntityAdapter;
import org.apache.causeway.applib.services.message.MessageService;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.applib.services.title.TitleService;
import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.extensions.pdfjs.applib.annotations.PdfJsViewer;
import org.apache.causeway.persistence.jpa.applib.integration.CausewayEntityListener;
import org.apache.causeway.persistence.jpa.applib.types.BlobJpaEmbeddable;

import static org.apache.causeway.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.causeway.applib.annotation.SemanticsOf.NON_IDEMPOTENT;
import static org.apache.causeway.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;
import static org.apache.causeway.applib.layout.LayoutConstants.FieldSetId.DETAILS;
import static org.apache.causeway.applib.layout.LayoutConstants.FieldSetId.IDENTITY;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@Entity
@Table(schema = ForestInventoryModule.SCHEMA)
@NamedQueries(
        {@NamedQuery(name = Forest.NAMED_QUERY_FIND_BY_NAME_LIKE,
                query = "SELECT f FROM Forest f WHERE f.name LIKE :name")}
)
@EntityListeners(CausewayEntityListener.class)
@Named(ForestInventoryModule.NAMESPACE + ".Forest")
@DomainObject(entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout(
        tableDecorator = TableDecorator.DatatablesNet.class,
        bookmarking = BookmarkPolicy.AS_ROOT
)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Forest implements Comparable<Forest> {

    static final String NAMED_QUERY_FIND_BY_NAME_LIKE = "Forest.findByNameLike";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    @PropertyLayout(fieldSetId = "metadata", sequence = "999")
    @Getter @Setter
    private long version;

    public Forest(String name) {
        this.name = name;
    }

    public static Forest withName(final String name) {
        val forest = new Forest();
        forest.setName(name);
        return forest;
    }

    @Inject @Transient RepositoryService repositoryService;
    @Inject @Transient TitleService titleService;
    @Inject @Transient MessageService messageService;


    @Title
    @Name
    @Column(length = Name.MAX_LEN, nullable = false, unique = true)
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = IDENTITY, sequence = "1")
    private String name;

    @Notes @Column(length = Notes.MAX_LEN, nullable = true)
    @Getter @Setter
    @Property @PropertyLayout(fieldSetId = DETAILS, sequence = "2")
    private String notes;

    // TODO extract this into an annotation
    @AttributeOverrides({
            @AttributeOverride(
                    name = "name",
                    column = @Column(name = "attachment_name")
            ),
            @AttributeOverride(
                    name = "mimeType",
                    column = @Column(name = "attachment_mimeType")
            ),
            @AttributeOverride(
                    name = "bytes",
                    column = @Column(name = "attachment_bytes")
            )
    })
    @Embedded private BlobJpaEmbeddable attachment;

    @PdfJsViewer
    @Property(optionality = Optionality.OPTIONAL)
    @PropertyLayout(fieldSetId = "content", sequence = "1")
    public Blob getAttachment() {
        return BlobJpaEmbeddable.toBlob(attachment);
    }

    public void setAttachment(final Blob attachment) {
        this.attachment = BlobJpaEmbeddable.fromBlob(attachment);
    }


    @Action(semantics = IDEMPOTENT)
    @ActionLayout(associateWith = "name", promptStyle = PromptStyle.INLINE,
            describedAs = "Updates the name of this object, certain characters"
                    + " (" + PROHIBITED_CHARACTERS + ") are not allowed.")
    public Forest updateName(@Name final String name) {
        setName(name);
        return this;
    }

    @MemberSupport
    public String default0UpdateName() {
        return getName();
    }

    @MemberSupport
    public String validate0UpdateName(final String newName) {
        for (char prohibitedCharacter : PROHIBITED_CHARACTERS.toCharArray()) {
            if (newName.contains("" + prohibitedCharacter)) {
                return "Character '" + prohibitedCharacter
                        + "' is not allowed.";
            }
        }
        return null;
    }

    static final String PROHIBITED_CHARACTERS = "&%$!";


    @Action(semantics = IDEMPOTENT)
    @ActionLayout(associateWith = "attachment",
            position = ActionLayout.Position.PANEL)
    public Forest updateAttachment(@Nullable final Blob attachment) {
        setAttachment(attachment);
        return this;
    }

    @MemberSupport
    public Blob default0UpdateAttachment() {
        return getAttachment();
    }

    @OneToMany(mappedBy = "forest", cascade = CascadeType.ALL) @Collection
    @CollectionLayout @Getter
    private Set<Inventory> inventories = new TreeSet<>();

    @Action(semantics = NON_IDEMPOTENT)
    @ActionLayout(associateWith = "inventories")
    public Inventory addInventory() {
        var inventory = new Inventory();
        addInventory(inventory);
        return inventory;
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(fieldSetId = IDENTITY, position = ActionLayout.Position.PANEL,
            describedAs = "Deletes this object from the persistent datastore")
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.removeAndFlush(this);
    }


    private static final Comparator<Forest> COMPARATOR
            = Comparator.comparing(Forest::getName);

    @Override
    public int compareTo(final Forest other) {
        return COMPARATOR.compare(this, other);
    }

    public void addInventory(Inventory inventory) {
        inventories.add(inventory);
        inventory.setForest(this);
    }
}
