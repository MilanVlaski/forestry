package com.akimi.modules.forest_inventory.tree.condition;

import java.math.BigDecimal;
import java.util.Comparator;

import com.akimi.modules.forest_inventory.ForestInventoryModule;
import com.akimi.modules.forest_inventory.tree.Tree;

import org.apache.causeway.applib.annotation.BookmarkPolicy;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Publishing;
import org.apache.causeway.applib.annotation.TableDecorator;
import org.apache.causeway.applib.annotation.Title;
import org.apache.causeway.applib.jaxb.PersistentEntityAdapter;
import org.apache.causeway.persistence.jpa.applib.integration.CausewayEntityListener;

import static org.apache.causeway.applib.layout.LayoutConstants.FieldSetId.DETAILS;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@Table(schema = ForestInventoryModule.SCHEMA, name = "TreeCondition")
@EntityListeners(CausewayEntityListener.class)
@Named(Tree.NAMESPACE + ".Condition")
@DomainObject(entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout(
        tableDecorator = TableDecorator.DatatablesNet.class,
        bookmarking = BookmarkPolicy.AS_ROOT)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Condition implements Comparable<Condition> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Title
    @Name
    @Column(nullable = false, length = Name.MAX_LEN, unique = true)
    @Getter @Setter
    @PropertyLayout(fieldSetId = DETAILS, sequence = "1")
    private String name;

    @Column(nullable = false, precision = 2, scale = 1)
    @Getter @Setter
    @Property
    @PropertyLayout(fieldSetId = DETAILS, sequence = "2")
    private BigDecimal level;

    public Condition(String name, int level) {
        this(name, BigDecimal.valueOf(level));
    }

    public Condition(String name, BigDecimal level) {
        this.name = name;
        this.level = level;
    }

    private static final Comparator<Condition> COMPARATOR =
            Comparator.comparing(Condition::getLevel);


    @Override
    public int compareTo(Condition other) {
        return COMPARATOR.compare(this, other);
    }
}
