package com.azurehorsecreations.photoalbum.domain.repository;

import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;

import java.util.List;

import io.reactivex.Observable;

/**
 * IPhotoRepository
 * Interface for the photo repository
 */

public interface IPhotoRepository {
    Observable<List<PhotoMetadata>> getPhotoMetadata();
}
