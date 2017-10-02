package com.azurehorsecreations.photoalbum.domain.interactors.impl;

import com.azurehorsecreations.photoalbum.data.repository.PhotoRepository;
import com.azurehorsecreations.photoalbum.domain.executor.IExecutor;
import com.azurehorsecreations.photoalbum.domain.executor.IMainThread;
import com.azurehorsecreations.photoalbum.domain.executor.impl.ThreadExecutor;
import com.azurehorsecreations.photoalbum.domain.interactors.IPhotoMetadataInteractor;
import com.azurehorsecreations.photoalbum.domain.interactors.base.AbstractInteractor;
import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;
import com.azurehorsecreations.photoalbum.presentation.MainThreadImpl;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * PhotoMetadataInteractorImpl
 * This interactor gets the photos from the repository and returns the results via a callback
 */
public class PhotoMetadataInteractorImpl extends AbstractInteractor implements IPhotoMetadataInteractor {

    private Callback mCallback;
    private PhotoRepository mPhotoRepository;

    public PhotoMetadataInteractorImpl(Callback callback) {
        super(ThreadExecutor.getInstance(), MainThreadImpl.getInstance());
        mCallback = callback;
        mPhotoRepository = new PhotoRepository();
    }

    public PhotoMetadataInteractorImpl(IExecutor executor, IMainThread mainThread,
                                       Callback callback, PhotoRepository repository) {
        super(executor, mainThread);
        mCallback = callback;
        mPhotoRepository = repository;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRetrievalFailed("No results :(");
            }
        });
    }

    public void postMessage(final List<PhotoMetadata> photos) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onPhotoMetadataRetrieved(photos);
            }
        });
    }

    @Override
    public void run() {
        mPhotoRepository.getPhotoMetadata()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PhotoMetadata>>() {

                    @Override
                    public void onError(Throwable e) {
                        notifyError();
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<PhotoMetadata> photos) {
                        postMessage(photos);
                    }
                });
    }
}
