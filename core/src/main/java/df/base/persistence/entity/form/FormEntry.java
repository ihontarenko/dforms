package df.base.persistence.entity.form;

import df.base.common.extensions.hibernate.generator.PrefixedId;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Set;

import static df.base.persistence.support.EntityGraphConstants.FORM_WITH_ENTRIES_WITH_FIELD_ENTRIES;
import static df.base.persistence.support.EntityGraphConstants.FORM_WITH_FIELD_ENTRIES_FIELD;

@Entity
@Table(name = "DF_FORM_ENTRIES")
@NamedEntityGraph(
        name = FORM_WITH_ENTRIES_WITH_FIELD_ENTRIES,
        attributeNodes = {
                @NamedAttributeNode(value = "fieldEntries", subgraph = FORM_WITH_FIELD_ENTRIES_FIELD)
        },
        subgraphs = {
                @NamedSubgraph(
                        name = FORM_WITH_FIELD_ENTRIES_FIELD,
                        attributeNodes = @NamedAttributeNode("field")
                )
        }
)
public class FormEntry {

    @Id
    @PrefixedId(prefixValue = "ENTRY", sequenceName = "FORM_ENTRY")
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "FORM_ID", nullable = false)
    private Form form;

    @Column(name = "CREATED_AT", nullable = false, updatable = false, insertable = false)
    private Timestamp createdAt;

    @OneToMany(mappedBy = "formEntry", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<FieldEntry> fieldEntries;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Set<FieldEntry> getFieldEntries() {
        return fieldEntries;
    }

    public void setFieldEntries(Set<FieldEntry> fieldEntries) {
        this.fieldEntries = fieldEntries;
    }
}

