package com.azurehorsecreations.photoalbum.presentation.presenters.impl;

import com.azurehorsecreations.photoalbum.data.repository.PhotoRepository;
import com.azurehorsecreations.photoalbum.domain.executor.IExecutor;
import com.azurehorsecreations.photoalbum.domain.executor.IMainThread;
import com.azurehorsecreations.photoalbum.domain.interactors.IPhotoMetadataInteractor;
import com.azurehorsecreations.photoalbum.domain.interactors.impl.PhotoMetadataInteractorImpl;
import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;
import com.azurehorsecreations.photoalbum.presentation.presenters.IPhotoMetadataPresenter;
import com.azurehorsecreations.photoalbum.presentation.presenters.base.AbstractPresenter;
import com.azurehorsecreations.photoalbum.presentation.ui.IBaseView;
import com.azurehorsecreations.photoalbum.presentation.ui.IPhotoView;
import com.azurehorsecreations.photoalbum.presentation.ui.navigation.INavigator;

import java.util.List;

public class PhotoMetadataPresenterImpl extends AbstractPresenter implements IPhotoMetadataPresenter,
        IPhotoMetadataInteractor.Callback {

    private IPhotoView mView;
    private PhotoRepository mPhotoRepository;
    private INavigator mNavigator;
    private IPhotoMetadataInteractor mInteractor;

    public PhotoMetadataPresenterImpl(IExecutor executor, IMainThread mainThread,
                                      IPhotoView view, PhotoRepository productRepository) {
        super(executor, mainThread);
        mView = view;
        mPhotoRepository = productRepository;
    }

    @Override
    public void attachView(IBaseView view) {
        mView = (IPhotoView)view;
    }

    @Override
    public void detachView(IBaseView view) {

    }

    @Override
    public void resume() {

        mView.showProgress();

        mInteractor = new PhotoMetadataInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mPhotoRepository
        );

        mInteractor.execute();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {
        mView.showError(message);
    }

    @Override
    public void onPhotoMetadataRetrieved(List<PhotoMetadata> photos) {
        mView.hideProgress();
        mView.displayPhotoInformation(photos);
    }

    @Override
    public void onRetrievalFailed(String error) {
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void setNavigator(INavigator navigator) {
        this.mNavigator = navigator;
    }

    @Override
    public void navigateToNewScreen() {
        this.mNavigator.launchActivity();
    }

    @Override
    public void setInteractor(IPhotoMetadataInteractor interactor) {
        this.mInteractor = interactor;
    }
}
