package com.azurehorsecreations.photoalbum.presentation.presenters;

import com.azurehorsecreations.photoalbum.presentation.ui.navigation.INavigator;

/**
 * ISplashPresenter
 * Interface for splash presenters
 */

public interface ISplashPresenter {
    void setNavigator(INavigator navigator);
    void navigateToNewScreen();
}
