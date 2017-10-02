package com.azurehorsecreations.photoalbum.data.network;

import com.azurehorsecreations.photoalbum.contants.PhotoConstants;
import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;


/**
 * IPhotoAPIService
 * Defines the API endpoints
 */

public interface IPhotoAPIService {
    @GET(PhotoConstants.PHOTO_METADATA_END_URL)
    Observable<List<PhotoMetadata>> fetchPhotoMetadata();
}
