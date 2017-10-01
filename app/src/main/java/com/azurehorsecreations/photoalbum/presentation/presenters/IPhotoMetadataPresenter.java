package com.azurehorsecreations.photoalbum.presentation.presenters;

import com.azurehorsecreations.photoalbum.domain.interactors.IPhotoMetadataInteractor;
import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;
import com.azurehorsecreations.photoalbum.presentation.ui.navigation.INavigator;
import com.azurehorsecreations.photoalbum.presentation.presenters.base.IBasePresenter;
import com.azurehorsecreations.photoalbum.presentation.ui.IBaseView;

import java.util.List;


public interface IPhotoMetadataPresenter extends IBasePresenter {
    void setNavigator(INavigator navigator);
    void navigateToNewScreen();
    void setInteractor(IPhotoMetadataInteractor interactor);
}
