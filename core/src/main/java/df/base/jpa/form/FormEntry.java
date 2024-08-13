package df.base.jpa.form;

import df.base.common.hibernate5.generator.PrefixedId;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "DF_FORM_ENTRIES")
public class FormEntry {

    @Id
    @PrefixedId(prefixValue = "FEY", sequenceName = "FORM_ENTRY")
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "FORM_ID", nullable = false)
    private Form form;

    @Column(name = "CREATED_AT", nullable = false, updatable = false, insertable = false)
    private Timestamp createdAt;

    @OneToMany(mappedBy = "formEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormFieldEntry> fieldEntries;

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

    public List<FormFieldEntry> getFieldEntries() {
        return fieldEntries;
    }

    public void setFieldEntries(List<FormFieldEntry> fieldEntries) {
        this.fieldEntries = fieldEntries;
    }
}

