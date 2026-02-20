package df.application.persistence.entity.form;

import df.application.persistence.entity.user.User;
import df.application.persistence.support.EntityConstants;
import df.application.persistence.support.EntityGraphConstants;
import df.common.extensions.hibernate.generator.PrefixedId;
import df.common.persistence.GlobalEntityListener;
import df.application.persistence.entity.support.FormStatus;
import df.application.persistence.entity.EntityNameAware;
import df.application.persistence.generator.NamedEntityIdGenerator;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.*;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "DF_FORMS")
@NamedEntityGraph(
        name = EntityGraphConstants.FORM_WITH_USER,
        attributeNodes = @NamedAttributeNode("user")
)
@EntityListeners(GlobalEntityListener.class)
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

    @ManyToMany
    @JoinTable(
            name = EntityConstants.TABLE_FORM_FIELD_MAPPING,
            inverseJoinColumns = @JoinColumn(name = EntityConstants.COLUMN_FORM_FIELD_MAPPING_FIELD_ID),
            joinColumns = @JoinColumn(name = EntityConstants.COLUMN_FORM_FIELD_MAPPING_FORM_ID)
    )
    @OrderColumn(name = "SEQUENCE_ORDER")
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
        return new ArrayList<>(fields);
    }

    public void setFields(@NotNull List<Field> fields) {
        this.fields = new ArrayList<>(new LinkedHashSet<>(fields));
    }

    public void addField(Field field) {
        Optional.ofNullable(field).ifPresent(f -> {
            this.fields.remove(f);
            this.fields.add(f);
        });
    }

    public boolean existsField(Field field) {
        return this.fields.contains(field);
    }

    public boolean absentField(Field field) {
        return !existsField(field);
    }

    public void removeField(Field field) {
        this.fields.remove(requireNonNull(field));
    }

    public List<FormEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<FormEntry> entries) {
        this.entries = entries;
    }

}
