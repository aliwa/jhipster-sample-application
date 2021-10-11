package com.aliwa.myapp.domain;

import com.aliwa.myapp.domain.enumeration.AEnum;
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
 * A A.
 */
@Entity
@Table(name = "a")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class A implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 100, max = 1000)
    @Pattern(regexp = "^[A-Z][a-z]+\\d$")
    @Column(name = "a_string", length = 1000, nullable = false, unique = true)
    private String aString;

    @NotNull
    @Min(value = 100)
    @Max(value = 1000)
    @Column(name = "a_integer", nullable = false, unique = true)
    private Integer aInteger;

    @NotNull
    @Min(value = 100L)
    @Max(value = 1000L)
    @Column(name = "a_long", nullable = false, unique = true)
    private Long aLong;

    @NotNull
    @DecimalMin(value = "100")
    @DecimalMax(value = "1000")
    @Column(name = "a_big_decimal", precision = 21, scale = 2, nullable = false, unique = true)
    private BigDecimal aBigDecimal;

    @NotNull
    @DecimalMin(value = "100")
    @DecimalMax(value = "1000")
    @Column(name = "a_float", nullable = false, unique = true)
    private Float aFloat;

    @NotNull
    @DecimalMin(value = "100")
    @DecimalMax(value = "1000")
    @Column(name = "a_double", nullable = false, unique = true)
    private Double aDouble;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "a_enum", nullable = false, unique = true)
    private AEnum aEnum;

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

    @NotNull
    @Column(name = "a_blob_content_type", nullable = false)
    private String aBlobContentType;

    @Lob
    @Column(name = "a_any_blob", nullable = false, unique = true)
    private byte[] aAnyBlob;

    @NotNull
    @Column(name = "a_any_blob_content_type", nullable = false)
    private String aAnyBlobContentType;

    @Lob
    @Column(name = "image_blob", nullable = false, unique = true)
    private byte[] imageBlob;

    @NotNull
    @Column(name = "image_blob_content_type", nullable = false)
    private String imageBlobContentType;

    @Lob
    @Column(name = "a_text_blob", nullable = false, unique = true)
    private String aTextBlob;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public A id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getaString() {
        return this.aString;
    }

    public A aString(String aString) {
        this.setaString(aString);
        return this;
    }

    public void setaString(String aString) {
        this.aString = aString;
    }

    public Integer getaInteger() {
        return this.aInteger;
    }

    public A aInteger(Integer aInteger) {
        this.setaInteger(aInteger);
        return this;
    }

    public void setaInteger(Integer aInteger) {
        this.aInteger = aInteger;
    }

    public Long getaLong() {
        return this.aLong;
    }

    public A aLong(Long aLong) {
        this.setaLong(aLong);
        return this;
    }

    public void setaLong(Long aLong) {
        this.aLong = aLong;
    }

    public BigDecimal getaBigDecimal() {
        return this.aBigDecimal;
    }

    public A aBigDecimal(BigDecimal aBigDecimal) {
        this.setaBigDecimal(aBigDecimal);
        return this;
    }

    public void setaBigDecimal(BigDecimal aBigDecimal) {
        this.aBigDecimal = aBigDecimal;
    }

    public Float getaFloat() {
        return this.aFloat;
    }

    public A aFloat(Float aFloat) {
        this.setaFloat(aFloat);
        return this;
    }

    public void setaFloat(Float aFloat) {
        this.aFloat = aFloat;
    }

    public Double getaDouble() {
        return this.aDouble;
    }

    public A aDouble(Double aDouble) {
        this.setaDouble(aDouble);
        return this;
    }

    public void setaDouble(Double aDouble) {
        this.aDouble = aDouble;
    }

    public AEnum getaEnum() {
        return this.aEnum;
    }

    public A aEnum(AEnum aEnum) {
        this.setaEnum(aEnum);
        return this;
    }

    public void setaEnum(AEnum aEnum) {
        this.aEnum = aEnum;
    }

    public Boolean getaBoolean() {
        return this.aBoolean;
    }

    public A aBoolean(Boolean aBoolean) {
        this.setaBoolean(aBoolean);
        return this;
    }

    public void setaBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public LocalDate getaLocalDate() {
        return this.aLocalDate;
    }

    public A aLocalDate(LocalDate aLocalDate) {
        this.setaLocalDate(aLocalDate);
        return this;
    }

    public void setaLocalDate(LocalDate aLocalDate) {
        this.aLocalDate = aLocalDate;
    }

    public ZonedDateTime getaZonedDateTime() {
        return this.aZonedDateTime;
    }

    public A aZonedDateTime(ZonedDateTime aZonedDateTime) {
        this.setaZonedDateTime(aZonedDateTime);
        return this;
    }

    public void setaZonedDateTime(ZonedDateTime aZonedDateTime) {
        this.aZonedDateTime = aZonedDateTime;
    }

    public Instant getaInstant() {
        return this.aInstant;
    }

    public A aInstant(Instant aInstant) {
        this.setaInstant(aInstant);
        return this;
    }

    public void setaInstant(Instant aInstant) {
        this.aInstant = aInstant;
    }

    public Duration getaDuration() {
        return this.aDuration;
    }

    public A aDuration(Duration aDuration) {
        this.setaDuration(aDuration);
        return this;
    }

    public void setaDuration(Duration aDuration) {
        this.aDuration = aDuration;
    }

    public UUID getaUUID() {
        return this.aUUID;
    }

    public A aUUID(UUID aUUID) {
        this.setaUUID(aUUID);
        return this;
    }

    public void setaUUID(UUID aUUID) {
        this.aUUID = aUUID;
    }

    public byte[] getaBlob() {
        return this.aBlob;
    }

    public A aBlob(byte[] aBlob) {
        this.setaBlob(aBlob);
        return this;
    }

    public void setaBlob(byte[] aBlob) {
        this.aBlob = aBlob;
    }

    public String getaBlobContentType() {
        return this.aBlobContentType;
    }

    public A aBlobContentType(String aBlobContentType) {
        this.aBlobContentType = aBlobContentType;
        return this;
    }

    public void setaBlobContentType(String aBlobContentType) {
        this.aBlobContentType = aBlobContentType;
    }

    public byte[] getaAnyBlob() {
        return this.aAnyBlob;
    }

    public A aAnyBlob(byte[] aAnyBlob) {
        this.setaAnyBlob(aAnyBlob);
        return this;
    }

    public void setaAnyBlob(byte[] aAnyBlob) {
        this.aAnyBlob = aAnyBlob;
    }

    public String getaAnyBlobContentType() {
        return this.aAnyBlobContentType;
    }

    public A aAnyBlobContentType(String aAnyBlobContentType) {
        this.aAnyBlobContentType = aAnyBlobContentType;
        return this;
    }

    public void setaAnyBlobContentType(String aAnyBlobContentType) {
        this.aAnyBlobContentType = aAnyBlobContentType;
    }

    public byte[] getImageBlob() {
        return this.imageBlob;
    }

    public A imageBlob(byte[] imageBlob) {
        this.setImageBlob(imageBlob);
        return this;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    public String getImageBlobContentType() {
        return this.imageBlobContentType;
    }

    public A imageBlobContentType(String imageBlobContentType) {
        this.imageBlobContentType = imageBlobContentType;
        return this;
    }

    public void setImageBlobContentType(String imageBlobContentType) {
        this.imageBlobContentType = imageBlobContentType;
    }

    public String getaTextBlob() {
        return this.aTextBlob;
    }

    public A aTextBlob(String aTextBlob) {
        this.setaTextBlob(aTextBlob);
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
        if (!(o instanceof A)) {
            return false;
        }
        return id != null && id.equals(((A) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "A{" +
            "id=" + getId() +
            ", aString='" + getaString() + "'" +
            ", aInteger=" + getaInteger() +
            ", aLong=" + getaLong() +
            ", aBigDecimal=" + getaBigDecimal() +
            ", aFloat=" + getaFloat() +
            ", aDouble=" + getaDouble() +
            ", aEnum='" + getaEnum() + "'" +
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
            ", imageBlob='" + getImageBlob() + "'" +
            ", imageBlobContentType='" + getImageBlobContentType() + "'" +
            ", aTextBlob='" + getaTextBlob() + "'" +
            "}";
    }
}
