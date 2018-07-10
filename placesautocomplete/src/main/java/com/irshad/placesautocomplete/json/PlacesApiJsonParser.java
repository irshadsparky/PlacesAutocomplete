package com.irshad.placesautocomplete.json;

import com.irshad.placesautocomplete.model.Place;
import com.irshad.placesautocomplete.model.PlacesAutocompleteResponse;
import com.irshad.placesautocomplete.model.PlacesDetailsResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface PlacesApiJsonParser {
    PlacesAutocompleteResponse autocompleteFromStream(InputStream is) throws JsonParsingException;

    PlacesDetailsResponse detailsFromStream(InputStream is) throws JsonParsingException;

    List<Place> readHistoryJson(InputStream in) throws JsonParsingException;

    void writeHistoryJson(OutputStream os, List<Place> places) throws JsonWritingException;
}
