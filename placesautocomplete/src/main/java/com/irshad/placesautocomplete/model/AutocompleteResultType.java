package com.irshad.placesautocomplete.model;

/**
 * Corresponds to GoogleMapsPlacesAutoCompleteTextView attribute resultType
 */
public enum AutocompleteResultType {
    /**
     * Any location
     */
    GEOCODE("geocode"),
    /**
     * Any location represented by a full postal address
     */
    ADDRESS("address"),
    /**
     * Any location represented by a full postal address that is a business establishment
     */
    ESTABLISHMENT("establishment"),
    /**
     * Any location or establishment
     */
    NO_TYPE("no_type");


    private final String queryParam;

    AutocompleteResultType(final String queryParam) {
        this.queryParam = queryParam;
    }

    public String getQueryParam() {
        return queryParam;
    }

    public static AutocompleteResultType fromEnum(int enumerated) {
        return AutocompleteResultType.values()[enumerated];
    }
}
