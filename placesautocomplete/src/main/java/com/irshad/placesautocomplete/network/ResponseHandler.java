package com.irshad.placesautocomplete.network;

import com.irshad.placesautocomplete.json.JsonParsingException;

import java.io.InputStream;

interface ResponseHandler<T> {
    T handleStreamResult(InputStream is) throws JsonParsingException;
}
