package domainapp.modules.forest_inventory.arborist;

import org.apache.causeway.applib.annotation.BookmarkPolicy;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Publishing;
import org.apache.causeway.applib.annotation.TableDecorator;
import org.apache.causeway.applib.annotation.Title;
import org.apache.causeway.applib.jaxb.PersistentEntityAdapter;
import org.apache.causeway.extensions.secman.applib.user.dom.ApplicationUser;
import org.apache.causeway.persistence.jpa.applib.integration.CausewayEntityListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import domainapp.modules.forest_inventory.ForestInventoryModule;
import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@Table(schema = ForestInventoryModule.SCHEMA)
@EntityListeners(CausewayEntityListener.class)
@Named(ForestInventoryModule.NAMESPACE + ".Arborist")
@DomainObject(entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout(
        tableDecorator = TableDecorator.DatatablesNet.class,
        bookmarking = BookmarkPolicy.AS_ROOT)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Arborist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Title
    @Column(nullable = false)
    @Getter
    @Property
    @PropertyLayout
    private String name;

    public Arborist(ApplicationUser user) {
        this.name = user.getUsername();
    }
}
