package com.irshad.placesautocomplete;

import com.irshad.placesautocomplete.model.Place;

public class PlaceDetailsLoadingFailure extends Throwable {

    private final Place source;

    public PlaceDetailsLoadingFailure(final Place source) {
        this.source = source;
    }

    public PlaceDetailsLoadingFailure(final Place source, final Throwable error) {
        super(error);
        this.source = source;
    }

    public Place getSourcePlace() {
        return source;
    }
}
