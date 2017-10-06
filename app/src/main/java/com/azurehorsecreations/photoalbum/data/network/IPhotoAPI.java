package com.azurehorsecreations.photoalbum.data.network;

import com.azurehorsecreations.photoalbum.contants.PhotoConstants;
import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;


/**
 * IPhotoAPI
 * Defines the photo API endpoints
 */

public interface IPhotoAPI {
    @GET(PhotoConstants.PHOTO_METADATA_END_URL)
    Observable<List<PhotoMetadata>> fetchPhotoMetadata();
}
