package com.irshad.placesautocompletedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.irshad.placesautocomplete.PlacesApi;
import com.irshad.placesautocomplete.adapter.AbstractPlacesAutocompleteAdapter;
import com.irshad.placesautocomplete.history.AutocompleteHistoryManager;
import com.irshad.placesautocomplete.model.AutocompleteResultType;
import com.irshad.placesautocomplete.model.Place;

public class TestPlacesAutocompleteAdapter extends AbstractPlacesAutocompleteAdapter {

    public TestPlacesAutocompleteAdapter(final Context context, final PlacesApi api, final AutocompleteResultType resultType, final AutocompleteHistoryManager history) {
        super(context, api, resultType, history);
    }

    @Override
    protected View newView(final ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.places_autocomplete_item, parent, false);
    }

    @Override
    protected void bindView(final View view, final Place item) {
        ((TextView) view).setText(item.description);
    }
}
