package com.myapp.service.dto;

import com.myapp.domain.enumeration.AEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.myapp.domain.A} entity.
 */
public class ADTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 100, max = 1000)
    @Pattern(regexp = "^[A-Z][a-z]+\\d$")
    private String aString;

    @NotNull
    @Min(value = 100)
    @Max(value = 1000)
    private Integer aInteger;

    @NotNull
    @Min(value = 100L)
    @Max(value = 1000L)
    private Long aLong;

    @NotNull
    @DecimalMin(value = "100")
    @DecimalMax(value = "1000")
    private BigDecimal aBigDecimal;

    @NotNull
    @DecimalMin(value = "100")
    @DecimalMax(value = "1000")
    private Float aFloat;

    @NotNull
    @DecimalMin(value = "100")
    @DecimalMax(value = "1000")
    private Double aDouble;

    @NotNull
    private AEnum aEnum;

    @NotNull
    private Boolean aBoolean;

    @NotNull
    private LocalDate aLocalDate;

    @NotNull
    private ZonedDateTime aZonedDateTime;

    @NotNull
    private Instant aInstant;

    @NotNull
    private Duration aDuration;

    @NotNull
    private UUID aUUID;

    @Lob
    private byte[] aBlob;

    private String aBlobContentType;

    @Lob
    private byte[] aAnyBlob;

    private String aAnyBlobContentType;

    @Lob
    private byte[] imageBlob;

    private String imageBlobContentType;

    @Lob
    private String aTextBlob;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getaString() {
        return aString;
    }

    public void setaString(String aString) {
        this.aString = aString;
    }

    public Integer getaInteger() {
        return aInteger;
    }

    public void setaInteger(Integer aInteger) {
        this.aInteger = aInteger;
    }

    public Long getaLong() {
        return aLong;
    }

    public void setaLong(Long aLong) {
        this.aLong = aLong;
    }

    public BigDecimal getaBigDecimal() {
        return aBigDecimal;
    }

    public void setaBigDecimal(BigDecimal aBigDecimal) {
        this.aBigDecimal = aBigDecimal;
    }

    public Float getaFloat() {
        return aFloat;
    }

    public void setaFloat(Float aFloat) {
        this.aFloat = aFloat;
    }

    public Double getaDouble() {
        return aDouble;
    }

    public void setaDouble(Double aDouble) {
        this.aDouble = aDouble;
    }

    public AEnum getaEnum() {
        return aEnum;
    }

    public void setaEnum(AEnum aEnum) {
        this.aEnum = aEnum;
    }

    public Boolean getaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public LocalDate getaLocalDate() {
        return aLocalDate;
    }

    public void setaLocalDate(LocalDate aLocalDate) {
        this.aLocalDate = aLocalDate;
    }

    public ZonedDateTime getaZonedDateTime() {
        return aZonedDateTime;
    }

    public void setaZonedDateTime(ZonedDateTime aZonedDateTime) {
        this.aZonedDateTime = aZonedDateTime;
    }

    public Instant getaInstant() {
        return aInstant;
    }

    public void setaInstant(Instant aInstant) {
        this.aInstant = aInstant;
    }

    public Duration getaDuration() {
        return aDuration;
    }

    public void setaDuration(Duration aDuration) {
        this.aDuration = aDuration;
    }

    public UUID getaUUID() {
        return aUUID;
    }

    public void setaUUID(UUID aUUID) {
        this.aUUID = aUUID;
    }

    public byte[] getaBlob() {
        return aBlob;
    }

    public void setaBlob(byte[] aBlob) {
        this.aBlob = aBlob;
    }

    public String getaBlobContentType() {
        return aBlobContentType;
    }

    public void setaBlobContentType(String aBlobContentType) {
        this.aBlobContentType = aBlobContentType;
    }

    public byte[] getaAnyBlob() {
        return aAnyBlob;
    }

    public void setaAnyBlob(byte[] aAnyBlob) {
        this.aAnyBlob = aAnyBlob;
    }

    public String getaAnyBlobContentType() {
        return aAnyBlobContentType;
    }

    public void setaAnyBlobContentType(String aAnyBlobContentType) {
        this.aAnyBlobContentType = aAnyBlobContentType;
    }

    public byte[] getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    public String getImageBlobContentType() {
        return imageBlobContentType;
    }

    public void setImageBlobContentType(String imageBlobContentType) {
        this.imageBlobContentType = imageBlobContentType;
    }

    public String getaTextBlob() {
        return aTextBlob;
    }

    public void setaTextBlob(String aTextBlob) {
        this.aTextBlob = aTextBlob;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ADTO)) {
            return false;
        }

        ADTO aDTO = (ADTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, aDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ADTO{" +
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
            ", aAnyBlob='" + getaAnyBlob() + "'" +
            ", imageBlob='" + getImageBlob() + "'" +
            ", aTextBlob='" + getaTextBlob() + "'" +
            "}";
    }
}
