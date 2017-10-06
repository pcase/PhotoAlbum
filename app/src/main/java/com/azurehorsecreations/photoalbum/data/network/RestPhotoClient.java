package com.azurehorsecreations.photoalbum.data.network;

import com.azurehorsecreations.photoalbum.contants.PhotoConstants;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RestPhotoClient
 * Creates the Retrofit client that will call the photo API
 */

public class RestPhotoClient {
    private static Retrofit retrofit = null;

    public static Retrofit getInstance() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(PhotoConstants.PHOTO_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}