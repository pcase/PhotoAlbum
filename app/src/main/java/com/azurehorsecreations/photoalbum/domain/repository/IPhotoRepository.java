package com.azurehorsecreations.photoalbum.domain.repository;

import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;

import java.util.List;

import io.reactivex.Observable;

/**
 * IPhotoRepository
 * Interface for the photo repository which is responsible for getting photos.
 */

public interface IPhotoRepository {
    Observable<List<PhotoMetadata>> getPhotoMetadata();
}
