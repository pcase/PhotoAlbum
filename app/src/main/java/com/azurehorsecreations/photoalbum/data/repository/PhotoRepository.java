package com.azurehorsecreations.photoalbum.data.repository;

import com.azurehorsecreations.photoalbum.data.network.RestClientSingleton;
import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;
import com.azurehorsecreations.photoalbum.domain.repository.IPhotoRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 * PhotoRepository
 * Provides access to the photos
 */

public class PhotoRepository implements IPhotoRepository {
    private static String TAG = "PhotoRepository";

    @Override
    public Observable<List<PhotoMetadata>> getPhotoMetadata() {
        return Observable.defer(() -> RestClientSingleton.getInstance().getPhotoMetadata());
    }
}