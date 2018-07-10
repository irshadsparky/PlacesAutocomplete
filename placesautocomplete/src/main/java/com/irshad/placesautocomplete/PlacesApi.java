package com.irshad.placesautocomplete;

import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.irshad.placesautocomplete.model.AutocompleteResultType;
import com.irshad.placesautocomplete.model.PlacesAutocompleteResponse;
import com.irshad.placesautocomplete.model.PlacesDetailsResponse;
import com.irshad.placesautocomplete.network.PlacesHttpClient;
import com.irshad.placesautocomplete.util.LocationUtils;

import java.io.IOException;

/**
 * An Abstraction for the Google Maps Places API. Manages the building of requests to the API and
 * executing them using the provided {@link PlacesHttpClient}
 */
public class PlacesApi {
    public static final AutocompleteResultType DEFAULT_RESULT_TYPE = AutocompleteResultType.ADDRESS;

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String PATH_AUTOCOMPLETE = "autocomplete";
    private static final String PATH_DETAILS = "details";
    private static final String PATH_JSON = "json";
    private static final String PARAMETER_LOCATION = "location";
    private static final String PARAMETER_RADIUS = "radius";
    private static final String PARAMETER_INPUT = "input";
    private static final String PARAMETER_KEY = "key";
    private static final String PARAMETER_TYPE = "types";
    private static final String PARAMETER_PLACE_ID = "placeid";
    private static final String PARAMETER_LANGUAGE = "language";

    private static final Long NO_BIAS_RADIUS = 20000000L;
    private static final Location NO_BIAS_LOCATION;

    static {
        NO_BIAS_LOCATION = new Location("none");
        NO_BIAS_LOCATION.setLatitude(0.0d);
        NO_BIAS_LOCATION.setLongitude(0.0d);
    }

    @NonNull
    private final PlacesHttpClient httpClient;

    @NonNull
    private final String googleApiKey;

    @Nullable
    private Location currentLocation;

    @Nullable
    private Long radiusM;

    @Nullable
    private String languageCode;

    private boolean locationBiasEnabled = true;

    public PlacesApi(@NonNull final PlacesHttpClient httpClient, @NonNull final String googleApiKey) {
        this.httpClient = httpClient;
        this.googleApiKey = googleApiKey;
    }

    /**
     * @return if the Places API is currently going to return results biased to the device's current
     * location
     */
    public boolean isLocationBiasEnabled() {
        return locationBiasEnabled;
    }

    /**
     * Allows for enabling and disabling location biasing in the Places api.
     *
     * @param enabled is biasing should be enabled. true by default.
     */
    public void setLocationBiasEnabled(boolean enabled) {
        locationBiasEnabled = enabled;
    }

    /**
     * @return the radius, in meters.
     */
    public Long getRadiusMeters() {
        return radiusM;
    }

    /**
     * @param radiusM The radius from the provided location to bias results with. By default,
     *                     the Places API biases with x meters. To disable
     *                     the bias radius but maintain the biasing, use the
     *                     {@link PlacesApi#NO_BIAS_RADIUS}
     */
    public void setRadiusMeters(final Long radiusM) {
        this.radiusM = radiusM;
    }

    /**
     * @return the current location in use for location biasing. By default, biasing uses geoip
     */
    @Nullable
    public Location getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Sets the location that will be used for biasing the Place results. The API will favor Places
     * close to the set location when producing results
     *
     * @param currentLocation the Location to bias results towards
     */
    public void setCurrentLocation(@Nullable final Location currentLocation) {
        this.currentLocation = currentLocation;
    }


    /**
     *
     * @return the languageCode code
     */
    @Nullable
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the languageCode code used for autocomplete and place details calls.
     * List of supportable codes can be seen in <a href="https://developers.google.com/maps/faq#languagesupport">documentation</a>
     *
     * @param language the languageCode code
     */
    public void setLanguageCode(@Nullable String language) {
        this.languageCode = language;
    }

    /**
     * Performs autocompletion for the given input text and the type of response desired. This is a
     * synchronous call, you must provide your own Async if you need it
     * @param input the textual input that will be autocompleted
     * @param type the response type from the api
     * @throws IOException
     */
    public PlacesAutocompleteResponse autocomplete(final String input, final AutocompleteResultType type) throws IOException {
        final String finalInput = input == null ? "" : input;

        final AutocompleteResultType finalType = type == null ? DEFAULT_RESULT_TYPE : type;

        Uri.Builder uriBuilder = Uri.parse(PLACES_API_BASE)
                .buildUpon()
                .appendPath(PATH_AUTOCOMPLETE)
                .appendPath(PATH_JSON)
                .appendQueryParameter(PARAMETER_KEY, googleApiKey)
                .appendQueryParameter(PARAMETER_INPUT, finalInput);

        if (finalType != AutocompleteResultType.NO_TYPE) {
            uriBuilder.appendQueryParameter(PARAMETER_TYPE, finalType.getQueryParam());
        }

        if (locationBiasEnabled && currentLocation != null) {
            uriBuilder.appendQueryParameter(PARAMETER_LOCATION, LocationUtils.toLatLngString(currentLocation));
        }

        if (locationBiasEnabled && radiusM != null) {
            uriBuilder.appendQueryParameter(PARAMETER_RADIUS, radiusM.toString());
        }

        if (!locationBiasEnabled) {
            uriBuilder.appendQueryParameter(PARAMETER_LOCATION, LocationUtils.toLatLngString(NO_BIAS_LOCATION));
            uriBuilder.appendQueryParameter(PARAMETER_RADIUS, NO_BIAS_RADIUS.toString());
        }

        if (languageCode != null) {
            uriBuilder.appendQueryParameter(PARAMETER_LANGUAGE, languageCode);
        }

        return httpClient.executeAutocompleteRequest(uriBuilder.build());
    }

    /**
     * Fetches the PlaceDetails for the given place_id. This is a
     * synchronous call, you must provide your own Async if you need it
     * @param placeId the Google Maps Places API Place ID for the place you desire details of
     * @return the details for the place id
     * @throws IOException
     */
    public PlacesDetailsResponse details(final String placeId) throws IOException {
        Uri.Builder uriBuilder = Uri.parse(PLACES_API_BASE)
                .buildUpon()
                .appendPath(PATH_DETAILS)
                .appendPath(PATH_JSON)
                .appendQueryParameter(PARAMETER_KEY, googleApiKey)
                .appendQueryParameter(PARAMETER_PLACE_ID, placeId);

        if (languageCode != null) {
            uriBuilder.appendQueryParameter(PARAMETER_LANGUAGE, languageCode);
        }


        return httpClient.executeDetailsRequest(uriBuilder.build());
    }
}
