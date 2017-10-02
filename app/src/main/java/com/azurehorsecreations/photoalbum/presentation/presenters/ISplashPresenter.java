package com.azurehorsecreations.photoalbum.presentation.presenters;

import com.azurehorsecreations.photoalbum.presentation.ui.navigation.INavigator;


public interface ISplashPresenter {
    void setNavigator(INavigator navigator);
    void navigateToNewScreen();
}
