package io.startform.parent.jpa.geo;

import javax.persistence.*;

@Entity
@Table(name="COUNTRIES")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "FULL_NAME")
    private String canonicalName;

    @Column(name = "ISO", columnDefinition="CHAR")
    private String iso;

    @Column(name = "ISO3", columnDefinition="CHAR")
    private String iso3;

    @Column(name = "NUMBER_CODE")
    private Short code;

    @Column(name = "PHONE_CODE")
    private Integer phoneCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public Short getCode() {
        return code;
    }

    public void setCode(Short code) {
        this.code = code;
    }

    public Integer getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(Integer phoneCode) {
        this.phoneCode = phoneCode;
    }

    @Override
    public String toString() {
        return "Country{id=%d, name='%s', canonicalName='%s', iso='%s', iso3='%s', code='%s', phoneCode='%s'}"
                .formatted(id, name, canonicalName, iso, iso3, code, phoneCode);
    }

}
