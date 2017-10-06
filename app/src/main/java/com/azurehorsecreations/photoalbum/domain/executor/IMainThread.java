package com.azurehorsecreations.photoalbum.domain.executor;

/**
 * IMainThread
 * This interface defines a class that will enable interactors to run certain operations on the main UI thread.
 */
public interface IMainThread {
    void post(final Runnable runnable);
}
