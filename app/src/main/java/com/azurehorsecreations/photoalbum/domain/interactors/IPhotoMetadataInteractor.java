package com.azurehorsecreations.photoalbum.domain.interactors;

import com.azurehorsecreations.photoalbum.domain.interactors.base.IInteractor;
import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;

import java.util.List;

/**
 * IPhotoMetadataInteractor
 * Interface for interactors that retrieve photos
 */

public interface IPhotoMetadataInteractor extends IInteractor {
    interface Callback {
        void onPhotoMetadataRetrieved(List<PhotoMetadata> photos);
        void onRetrievalFailed(String error);
    }
}
