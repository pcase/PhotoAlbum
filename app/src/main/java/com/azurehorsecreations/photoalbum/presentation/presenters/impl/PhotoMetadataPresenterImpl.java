package com.azurehorsecreations.photoalbum.presentation.presenters.impl;

import com.azurehorsecreations.photoalbum.domain.interactors.IPhotoMetadataInteractor;
import com.azurehorsecreations.photoalbum.domain.interactors.impl.PhotoMetadataInteractorImpl;
import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;
import com.azurehorsecreations.photoalbum.presentation.presenters.IPhotoMetadataPresenter;
import com.azurehorsecreations.photoalbum.presentation.ui.IPhotoView;
import com.azurehorsecreations.photoalbum.presentation.ui.navigation.INavigator;

import java.util.List;

public class PhotoMetadataPresenterImpl implements IPhotoMetadataPresenter,
        IPhotoMetadataInteractor.Callback {

    private IPhotoView mView;
    private INavigator mNavigator;
    private IPhotoMetadataInteractor mInteractor;

    public PhotoMetadataPresenterImpl() {
        mInteractor = new PhotoMetadataInteractorImpl(this);
    }

    @Override
    public void loadPhotos() {
        mView.showProgress();
        mInteractor.execute();
    }

    @Override
    public void onPhotoMetadataRetrieved(List<PhotoMetadata> photos) {
        mView.hideProgress();
        mView.displayPhotoInformation(photos);
    }

    @Override
    public void onRetrievalFailed(String error) {
        mView.hideProgress();
        // TODO handle error
    }

    @Override
    public void attachView(IPhotoView view) {
        mView = view;
    }

    @Override
    public void detachView(IPhotoView view) {
    }

    @Override
    public void setNavigator(INavigator navigator) {
        this.mNavigator = navigator;
    }

    @Override
    public void navigateToNewScreen() {
        this.mNavigator.launchActivity();
    }
}
