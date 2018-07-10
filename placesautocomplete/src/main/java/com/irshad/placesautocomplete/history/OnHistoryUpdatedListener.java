package com.irshad.placesautocomplete.history;

import android.support.annotation.NonNull;

import com.irshad.placesautocomplete.model.Place;

import java.util.List;

public interface OnHistoryUpdatedListener {
    public void onHistoryUpdated(@NonNull List<Place> updatedHistory);
}
