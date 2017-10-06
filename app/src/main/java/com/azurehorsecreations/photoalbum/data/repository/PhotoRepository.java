package com.azurehorsecreations.photoalbum.data.repository;

import com.azurehorsecreations.photoalbum.data.network.IPhotoAPI;
import com.azurehorsecreations.photoalbum.data.network.RestPhotoClient;
import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;
import com.azurehorsecreations.photoalbum.domain.repository.IPhotoRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 * PhotoRepository
 * Gets the photos and them as an RxJava Observable
 */

public class PhotoRepository implements IPhotoRepository {
    private static String TAG = "PhotoRepository";

    @Override
    public Observable<List<PhotoMetadata>> getPhotoMetadata() {
        return Observable.defer(() -> RestPhotoClient.getInstance().create(IPhotoAPI.class).fetchPhotoMetadata());
    }
}