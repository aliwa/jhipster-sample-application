package com.aliwa.myapp.domain.enumeration;

/**
 * The AEnum enumeration.
 */
public enum AEnum {
    FRENCH("French"),
    ENGLISH("English"),
    SPANISH("Spanish");

    private final String value;

    AEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
