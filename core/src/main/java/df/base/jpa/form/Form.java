package df.base.jpa.form;

import df.base.internal.hibernate.generator.PrefixedId;
import df.base.jpa.*;
import df.base.jpa.form.support.FormStatus;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "DF_FORMS")
@NamedEntityGraph(
        name = EntityGraphConstants.FORM_WITH_USER,
        attributeNodes = @NamedAttributeNode("user")
)
public class Form implements EntityNameAware {

    @Id
    @PrefixedId(
            prefixValue = "D",
            sequenceName = "FORM",
            prefixGenerator = NamedEntityIdGenerator.class,
            numberFormat = "%04d",
            initialValue = 1000,
            incrementBy = 1,
            prefixSeparator = "_"
    )
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID", nullable = false)
    private User user;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private FormStatus status;

    @Column(name = "CREATED_AT", nullable = false, updatable = false, insertable = false)
    private Timestamp createdAt;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormConfig> configs;

    @ManyToMany(mappedBy = "forms", fetch = FetchType.LAZY)
    private List<Field> fields;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormEntry> entries;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FormStatus getStatus() {
        return status;
    }

    public void setStatus(FormStatus status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public List<FormConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<FormConfig> configs) {
        this.configs = configs;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public void addField(Field field) {
        this.fields.add(requireNonNull(field));
    }

    public List<FormEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<FormEntry> entries) {
        this.entries = entries;
    }

}
