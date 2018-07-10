package com.irshad.placesautocomplete.network;

import android.net.Uri;

import com.irshad.placesautocomplete.model.PlacesAutocompleteResponse;
import com.irshad.placesautocomplete.model.PlacesDetailsResponse;

import java.io.IOException;

public interface PlacesHttpClient {
    PlacesAutocompleteResponse executeAutocompleteRequest(Uri uri) throws IOException;

    PlacesDetailsResponse executeDetailsRequest(Uri uri) throws IOException;
}
