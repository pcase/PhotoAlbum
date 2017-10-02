package com.azurehorsecreations.photoalbum.presentation.presenters.impl;

import com.azurehorsecreations.photoalbum.presentation.presenters.ISplashPresenter;
import com.azurehorsecreations.photoalbum.presentation.ui.navigation.INavigator;

/**
 * SplashPresenterImpl
 * Presenter for displaying the splash screen
 */

public class SplashPresenterImpl implements ISplashPresenter {
    private INavigator mNavigator;

    @Override
    public void setNavigator(INavigator navigator) {
        this.mNavigator = navigator;
    }

    @Override
    public void navigateToNewScreen() {
        this.mNavigator.launchActivity();
    }
}
