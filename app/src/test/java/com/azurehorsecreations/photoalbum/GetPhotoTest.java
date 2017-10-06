package com.azurehorsecreations.photoalbum;

import com.azurehorsecreations.photoalbum.data.repository.PhotoRepository;
import com.azurehorsecreations.photoalbum.domain.executor.IExecutor;
import com.azurehorsecreations.photoalbum.domain.executor.IMainThread;
import com.azurehorsecreations.photoalbum.domain.interactors.IPhotoMetadataInteractor;
import com.azurehorsecreations.photoalbum.domain.interactors.impl.PhotoMetadataInteractorImpl;
import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;
import com.azurehorsecreations.photoalbum.presentation.presenters.impl.PhotoMetadataPresenterImpl;
import com.azurehorsecreations.photoalbum.presentation.ui.IPhotoView;
import com.azurehorsecreations.photoalbum.presentation.ui.PhotoDiffCallback;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
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
    private PhotoMetadataPresenterImpl mPresenter;

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

    // Test that the Interactor gets photos

    @Test
    public void testInteractorCallsbackonPhotoMetadataReceived() throws Exception {
        // Setup
        List<PhotoMetadata> mockedList = getPhotoMetadataList();
        final Observable<List<PhotoMetadata>> observable = Observable.just(mockedList);
        when(mRepository.getPhotoMetadata()).thenReturn(observable);
        PhotoMetadataInteractorImpl interactor = new PhotoMetadataInteractorImpl(
                mExecutor,
                mMainThread,
                mCallback,
                mRepository);

        // Test
        interactor.run();

        // Verify
        Mockito.verify(mRepository).getPhotoMetadata();
        Mockito.verifyNoMoreInteractions(mRepository);
        Mockito.verify(mCallback).onPhotoMetadataRetrieved(mockedList);
    }

    // Test that the when the Presenter is requested to load the photos, the interactor executes and calls back
    // the presenter with the photos.

    @Test
    public void testPresenterGetsCalledBackFromInteractor() throws Exception {
        // Setup
        List<PhotoMetadata> mockedList = getPhotoMetadataList();
        final Observable<List<PhotoMetadata>> observable = Observable.just(mockedList);
        PhotoMetadataPresenterImpl presenter = new PhotoMetadataPresenterImpl(mPhotoMetadataInteractor);

        when(mRepository.getPhotoMetadata())
                .thenReturn(observable);

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                presenter.onPhotoMetadataRetrieved(mockedList);
                return null;
            }
        }).when(mPhotoMetadataInteractor).execute();
        doNothing().when(mView).showProgress();
        presenter.attachView(mView);

        // Test
        presenter.loadPhotos();

        // Verify
        Mockito.verify(mPhotoMetadataInteractor).execute();
        Mockito.verify(mView).hideProgress();
    }

    // Test that the when the Presenter is requested to load the photos, it gets the photos from the
    // interactor and calls the view.

    @Test
    public void testPresenterCallsTheView() throws Exception {
        // Setup
        List<PhotoMetadata> mockedList = getPhotoMetadataList();
        final Observable<List<PhotoMetadata>> observable = Observable.just(mockedList);
        PhotoMetadataPresenterImpl presenter = new PhotoMetadataPresenterImpl(mPhotoMetadataInteractor);

        when(mRepository.getPhotoMetadata())
                .thenReturn(observable);

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                presenter.onPhotoMetadataRetrieved(mockedList);
                return null;
            }
        }).when(mPhotoMetadataInteractor).execute();
        doNothing().when(mView).showProgress();
        presenter.attachView(mView);

        // Test
        presenter.loadPhotos();

        // Verify
        Mockito.verify(mPhotoMetadataInteractor).execute();
        Mockito.verify(mView).displayPhotoInformation(mockedList);
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