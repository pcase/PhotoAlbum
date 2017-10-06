package com.azurehorsecreations.photoalbum.presentation.presenters;

import com.azurehorsecreations.photoalbum.presentation.ui.IPhotoView;
import com.azurehorsecreations.photoalbum.presentation.ui.navigation.INavigator;

/**
 * IhotoMetadataPresenterImpl
 * Interface for photo presenters
 **/

public interface IPhotoMetadataPresenter {
    void attachView (IPhotoView view);
    void setNavigator(INavigator navigator);
    void navigateToNewScreen();
    void loadPhotos();
}
