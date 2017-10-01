package com.azurehorsecreations.photoalbum;

import android.provider.ContactsContract;

import com.azurehorsecreations.photoalbum.data.network.IPhotoAPIService;
import com.azurehorsecreations.photoalbum.data.network.RestClientSingleton;
import com.azurehorsecreations.photoalbum.data.repository.PhotoRepository;
import com.azurehorsecreations.photoalbum.domain.executor.IExecutor;
import com.azurehorsecreations.photoalbum.domain.executor.IMainThread;
import com.azurehorsecreations.photoalbum.domain.executor.impl.ThreadExecutor;
import com.azurehorsecreations.photoalbum.domain.interactors.IPhotoMetadataInteractor;
import com.azurehorsecreations.photoalbum.domain.interactors.impl.PhotoMetadataInteractorImpl;
import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;
import com.azurehorsecreations.photoalbum.domain.repository.IPhotoRepository;
import com.azurehorsecreations.photoalbum.presentation.MainThreadImpl;
import com.azurehorsecreations.photoalbum.presentation.presenters.IPhotoMetadataPresenter;
import com.azurehorsecreations.photoalbum.presentation.presenters.impl.PhotoMetadataPresenterImpl;
import com.azurehorsecreations.photoalbum.presentation.ui.IPhotoView;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.parameterized.TestWithParameters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;

import static android.R.attr.dependency;
import static android.R.attr.description;
import static android.R.attr.maxItemsPerRow;
import static android.R.attr.textAppearanceSearchResultTitle;
import static android.R.attr.textOff;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
/**
 * Tests our product interactor.
 */
public class GetPhotoTest {

    @Mock
    private IMainThread mMainThread;

    @Mock
    private IExecutor mExecutor;

    @Mock
    private IPhotoMetadataInteractor.Callback mCallback;

    @Mock
    PhotoMetadataInteractorImpl mPhotoMetadataInteractor;

    @Mock
    private PhotoRepository mRepository;

    @Mock
    private IPhotoView mView;

    // Initialize RxAndroidPlugins with a different Scheduler.
    // The default scheduler returned by AndroidSchedulers.mainThread()
    // is an instance of LooperScheduler. It relies on Android dependencies
    // that are not available in JUnit tests.
    @BeforeClass
    public static void setUpRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mMainThread = new TestMainThread();
    }

    // Test the Interactor

    @Test
    public void testInteractorCallsbackonPhotoMetadataReceived() throws Exception {
        // Setup
        List<PhotoMetadata> list = getPhotoMetadataList();
        final Observable<List<PhotoMetadata>> observable = Observable.just(list);
        when(mRepository.getPhotoMetadata())
                .thenReturn(observable);
        PhotoMetadataInteractorImpl interactor = new PhotoMetadataInteractorImpl(
                mExecutor,
                mMainThread,
                mCallback,
                mRepository
        );

        // Test
        interactor.run();

        // Verify
        Mockito.verify(mRepository).getPhotoMetadata();
        Mockito.verifyNoMoreInteractions(mRepository);
        Mockito.verify(mCallback).onPhotoMetadataRetrieved(list);
    }

    private List<PhotoMetadata> getPhotoMetadataList() {
        List<PhotoMetadata> list = new ArrayList<>();
        PhotoMetadata data = new PhotoMetadata();
        data.description = "description1";
        data.title = "title1";
        data.filename = "file1.txt";
        list.add(data);
        data = new PhotoMetadata();
        data.description = "description2";
        data.title = "title2";
        data.filename = "file2.txt";
        list.add(data);
        return list;
    }
}