package com.azurehorsecreations.photoalbum.presentation.ui;

import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;

import java.util.List;

/**
 * IPhotoView
 * Interface for views that display photos
 */

public interface IPhotoView extends IBaseView {
    void displayPhotoInformation(List<PhotoMetadata> photo);
}
