package com.azurehorsecreations.photoalbum.presentation.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.azurehorsecreations.photoalbum.R;
import com.azurehorsecreations.photoalbum.data.repository.PhotoRepository;
import com.azurehorsecreations.photoalbum.domain.executor.impl.ThreadExecutor;
import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;
import com.azurehorsecreations.photoalbum.presentation.MainThreadImpl;
import com.azurehorsecreations.photoalbum.presentation.presenters.IPhotoMetadataPresenter;
import com.azurehorsecreations.photoalbum.presentation.presenters.impl.PhotoMetadataPresenterImpl;
import com.azurehorsecreations.photoalbum.presentation.ui.EndlessRecyclerViewScrollListener;
import com.azurehorsecreations.photoalbum.presentation.ui.adapters.PhotoAdapter;
import com.azurehorsecreations.photoalbum.presentation.ui.navigation.PhotoNavigator;
import com.azurehorsecreations.photoalbum.presentation.presenters.IPhotoMetadataPresenter.View;
import com.azurehorsecreations.photoalbum.presentation.ui.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotoActivity extends AppCompatActivity implements View, PhotoAdapter.OnItemClickListener {
    private static final String TAG = "PhotoActivity";
    private static final int NUMBER_OF_COLUMNS = 1;
    private boolean isPortrait = true;
    private GridLayoutManager gridLayoutManager;

    private EndlessRecyclerViewScrollListener mScrollListener;

    @Bind(R.id.message_view)
    TextView mMessageTextView;

    @Bind(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @Bind(R.id.progressbar)
    protected ProgressBar mProgressBar;

    protected IPhotoMetadataPresenter mPresenter;
    protected PhotoAdapter mAdapter;

    private List<PhotoMetadata> photoMetadataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresenter = new PhotoMetadataPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new PhotoRepository()
        );

        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        setRecyclerGridLayout(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
        mScrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.resume();
            }
        };
        mRecyclerView.addOnScrollListener(mScrollListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    private void setRecyclerGridLayout(boolean isPortrait) {
        if (isPortrait) {
            gridLayoutManager = new GridLayoutManager(this, 1);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        }
        else{
            gridLayoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            isPortrait = false;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            isPortrait = true;
        }
        setRecyclerGridLayout(isPortrait);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(android.view.View.VISIBLE);
//        Toast.makeText(this, R.string.retrieving, Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(android.view.View.INVISIBLE);
//        Toast.makeText(this, R.string.retrieved, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
        mMessageTextView.setText(message);
    }

    @Override
    public void displayPhotoInformation(List<PhotoMetadata> photos) {
        if (mAdapter != null && mAdapter.getItemCount() > 0) {
            List<PhotoMetadata> currentList = new ArrayList<>();
            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                currentList.add(mAdapter.getItem(i));
            }
            currentList.addAll(photos);
            mAdapter = new PhotoAdapter(this, (ArrayList<PhotoMetadata>) currentList, this, isPortrait);
            mAdapter.notifyDataSetChanged();
        } else {
            List<PhotoMetadata> photoList = new ArrayList<>();
            photoList.addAll(photos);
            mAdapter = new PhotoAdapter(this, (ArrayList<PhotoMetadata>) photoList, this, isPortrait);
            mAdapter.notifyDataSetChanged();
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(PhotoMetadata photo) {
        mPresenter.setNavigator(new PhotoNavigator(this, PhotoDetailPagerActivity.class, photo));
        mPresenter.navigateToNewScreen();
    }
}