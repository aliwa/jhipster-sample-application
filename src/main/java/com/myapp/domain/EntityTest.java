package com.myapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A EntityTest.
 */
@Entity
@Table(name = "entity_test")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EntityTest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 10, max = 100)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "a_string", length = 100, nullable = false, unique = true)
    private String aString;

    @NotNull
    @Min(value = 10)
    @Max(value = 100)
    @Column(name = "a_integer", nullable = false, unique = true)
    private Integer aInteger;

    @NotNull
    @Min(value = 10L)
    @Max(value = 100L)
    @Column(name = "a_long", nullable = false, unique = true)
    private Long aLong;

    @NotNull
    @DecimalMin(value = "10")
    @DecimalMax(value = "100")
    @Column(name = "a_big_decimal", precision = 21, scale = 2, nullable = false, unique = true)
    private BigDecimal aBigDecimal;

    @NotNull
    @DecimalMin(value = "10")
    @DecimalMax(value = "100")
    @Column(name = "a_float", nullable = false, unique = true)
    private Float aFloat;

    @NotNull
    @DecimalMin(value = "10")
    @DecimalMax(value = "100")
    @Column(name = "a_double", nullable = false, unique = true)
    private Double aDouble;

    @NotNull
    @Column(name = "a_boolean", nullable = false, unique = true)
    private Boolean aBoolean;

    @NotNull
    @Column(name = "a_local_date", nullable = false, unique = true)
    private LocalDate aLocalDate;

    @NotNull
    @Column(name = "a_zoned_date_time", nullable = false, unique = true)
    private ZonedDateTime aZonedDateTime;

    @NotNull
    @Column(name = "a_instant", nullable = false, unique = true)
    private Instant aInstant;

    @NotNull
    @Column(name = "a_duration", nullable = false, unique = true)
    private Duration aDuration;

    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "a_uuid", length = 36, nullable = false, unique = true)
    private UUID aUUID;

    @Lob
    @Column(name = "a_blob", nullable = false, unique = true)
    private byte[] aBlob;

    @Column(name = "a_blob_content_type", nullable = false)
    private String aBlobContentType;

    @Lob
    @Column(name = "a_any_blob", nullable = false, unique = true)
    private byte[] aAnyBlob;

    @Column(name = "a_any_blob_content_type", nullable = false)
    private String aAnyBlobContentType;

    @Lob
    @Column(name = "a_image_blob", nullable = false, unique = true)
    private byte[] aImageBlob;

    @Column(name = "a_image_blob_content_type", nullable = false)
    private String aImageBlobContentType;

    @Lob
    @Column(name = "a_text_blob", nullable = false, unique = true)
    private String aTextBlob;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntityTest id(Long id) {
        this.id = id;
        return this;
    }

    public String getaString() {
        return this.aString;
    }

    public EntityTest aString(String aString) {
        this.aString = aString;
        return this;
    }

    public void setaString(String aString) {
        this.aString = aString;
    }

    public Integer getaInteger() {
        return this.aInteger;
    }

    public EntityTest aInteger(Integer aInteger) {
        this.aInteger = aInteger;
        return this;
    }

    public void setaInteger(Integer aInteger) {
        this.aInteger = aInteger;
    }

    public Long getaLong() {
        return this.aLong;
    }

    public EntityTest aLong(Long aLong) {
        this.aLong = aLong;
        return this;
    }

    public void setaLong(Long aLong) {
        this.aLong = aLong;
    }

    public BigDecimal getaBigDecimal() {
        return this.aBigDecimal;
    }

    public EntityTest aBigDecimal(BigDecimal aBigDecimal) {
        this.aBigDecimal = aBigDecimal;
        return this;
    }

    public void setaBigDecimal(BigDecimal aBigDecimal) {
        this.aBigDecimal = aBigDecimal;
    }

    public Float getaFloat() {
        return this.aFloat;
    }

    public EntityTest aFloat(Float aFloat) {
        this.aFloat = aFloat;
        return this;
    }

    public void setaFloat(Float aFloat) {
        this.aFloat = aFloat;
    }

    public Double getaDouble() {
        return this.aDouble;
    }

    public EntityTest aDouble(Double aDouble) {
        this.aDouble = aDouble;
        return this;
    }

    public void setaDouble(Double aDouble) {
        this.aDouble = aDouble;
    }

    public Boolean getaBoolean() {
        return this.aBoolean;
    }

    public EntityTest aBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
        return this;
    }

    public void setaBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public LocalDate getaLocalDate() {
        return this.aLocalDate;
    }

    public EntityTest aLocalDate(LocalDate aLocalDate) {
        this.aLocalDate = aLocalDate;
        return this;
    }

    public void setaLocalDate(LocalDate aLocalDate) {
        this.aLocalDate = aLocalDate;
    }

    public ZonedDateTime getaZonedDateTime() {
        return this.aZonedDateTime;
    }

    public EntityTest aZonedDateTime(ZonedDateTime aZonedDateTime) {
        this.aZonedDateTime = aZonedDateTime;
        return this;
    }

    public void setaZonedDateTime(ZonedDateTime aZonedDateTime) {
        this.aZonedDateTime = aZonedDateTime;
    }

    public Instant getaInstant() {
        return this.aInstant;
    }

    public EntityTest aInstant(Instant aInstant) {
        this.aInstant = aInstant;
        return this;
    }

    public void setaInstant(Instant aInstant) {
        this.aInstant = aInstant;
    }

    public Duration getaDuration() {
        return this.aDuration;
    }

    public EntityTest aDuration(Duration aDuration) {
        this.aDuration = aDuration;
        return this;
    }

    public void setaDuration(Duration aDuration) {
        this.aDuration = aDuration;
    }

    public UUID getaUUID() {
        return this.aUUID;
    }

    public EntityTest aUUID(UUID aUUID) {
        this.aUUID = aUUID;
        return this;
    }

    public void setaUUID(UUID aUUID) {
        this.aUUID = aUUID;
    }

    public byte[] getaBlob() {
        return this.aBlob;
    }

    public EntityTest aBlob(byte[] aBlob) {
        this.aBlob = aBlob;
        return this;
    }

    public void setaBlob(byte[] aBlob) {
        this.aBlob = aBlob;
    }

    public String getaBlobContentType() {
        return this.aBlobContentType;
    }

    public EntityTest aBlobContentType(String aBlobContentType) {
        this.aBlobContentType = aBlobContentType;
        return this;
    }

    public void setaBlobContentType(String aBlobContentType) {
        this.aBlobContentType = aBlobContentType;
    }

    public byte[] getaAnyBlob() {
        return this.aAnyBlob;
    }

    public EntityTest aAnyBlob(byte[] aAnyBlob) {
        this.aAnyBlob = aAnyBlob;
        return this;
    }

    public void setaAnyBlob(byte[] aAnyBlob) {
        this.aAnyBlob = aAnyBlob;
    }

    public String getaAnyBlobContentType() {
        return this.aAnyBlobContentType;
    }

    public EntityTest aAnyBlobContentType(String aAnyBlobContentType) {
        this.aAnyBlobContentType = aAnyBlobContentType;
        return this;
    }

    public void setaAnyBlobContentType(String aAnyBlobContentType) {
        this.aAnyBlobContentType = aAnyBlobContentType;
    }

    public byte[] getaImageBlob() {
        return this.aImageBlob;
    }

    public EntityTest aImageBlob(byte[] aImageBlob) {
        this.aImageBlob = aImageBlob;
        return this;
    }

    public void setaImageBlob(byte[] aImageBlob) {
        this.aImageBlob = aImageBlob;
    }

    public String getaImageBlobContentType() {
        return this.aImageBlobContentType;
    }

    public EntityTest aImageBlobContentType(String aImageBlobContentType) {
        this.aImageBlobContentType = aImageBlobContentType;
        return this;
    }

    public void setaImageBlobContentType(String aImageBlobContentType) {
        this.aImageBlobContentType = aImageBlobContentType;
    }

    public String getaTextBlob() {
        return this.aTextBlob;
    }

    public EntityTest aTextBlob(String aTextBlob) {
        this.aTextBlob = aTextBlob;
        return this;
    }

    public void setaTextBlob(String aTextBlob) {
        this.aTextBlob = aTextBlob;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntityTest)) {
            return false;
        }
        return id != null && id.equals(((EntityTest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EntityTest{" +
            "id=" + getId() +
            ", aString='" + getaString() + "'" +
            ", aInteger=" + getaInteger() +
            ", aLong=" + getaLong() +
            ", aBigDecimal=" + getaBigDecimal() +
            ", aFloat=" + getaFloat() +
            ", aDouble=" + getaDouble() +
            ", aBoolean='" + getaBoolean() + "'" +
            ", aLocalDate='" + getaLocalDate() + "'" +
            ", aZonedDateTime='" + getaZonedDateTime() + "'" +
            ", aInstant='" + getaInstant() + "'" +
            ", aDuration='" + getaDuration() + "'" +
            ", aUUID='" + getaUUID() + "'" +
            ", aBlob='" + getaBlob() + "'" +
            ", aBlobContentType='" + getaBlobContentType() + "'" +
            ", aAnyBlob='" + getaAnyBlob() + "'" +
            ", aAnyBlobContentType='" + getaAnyBlobContentType() + "'" +
            ", aImageBlob='" + getaImageBlob() + "'" +
            ", aImageBlobContentType='" + getaImageBlobContentType() + "'" +
            ", aTextBlob='" + getaTextBlob() + "'" +
            "}";
    }
}
