package com.azurehorsecreations.photoalbum.presentation.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.azurehorsecreations.photoalbum.R;
import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;
import com.azurehorsecreations.photoalbum.presentation.presenters.IPhotoMetadataPresenter;
import com.azurehorsecreations.photoalbum.presentation.presenters.impl.PhotoMetadataPresenterImpl;
import com.azurehorsecreations.photoalbum.presentation.ui.EndlessRecyclerViewScrollListener;
import com.azurehorsecreations.photoalbum.presentation.ui.IPhotoView;
import com.azurehorsecreations.photoalbum.presentation.ui.adapters.PhotoAdapter;
import com.azurehorsecreations.photoalbum.presentation.ui.navigation.PhotoNavigator;
import com.azurehorsecreations.photoalbum.presentation.ui.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotoActivity extends AppCompatActivity implements IPhotoView, PhotoAdapter.OnItemClickListener {
    private static final String TAG = "PhotoActivity";
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
        mPresenter = new PhotoMetadataPresenterImpl();
        mPresenter.attachView(this);
        setLayoutForOrientation(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        mScrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.loadPhotos();
            }
        };
        mRecyclerView.addOnScrollListener(mScrollListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadPhotos();
    }

    private void setLayoutForOrientation(boolean isPortrait) {
        if (isPortrait) {
            gridLayoutManager = new GridLayoutManager(this, 1);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        }
        else{
            gridLayoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        }
        mAdapter = new PhotoAdapter(this, (ArrayList<PhotoMetadata>) photoMetadataList, this, isPortrait);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        isPortrait = newConfig.orientation == Configuration.ORIENTATION_PORTRAIT;
        setLayoutForOrientation(isPortrait);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(android.view.View.INVISIBLE);
    }

    @Override
    public void showError(String message) {
        mMessageTextView.setText(message);
    }

    @Override
    public void displayPhotoInformation(List<PhotoMetadata> photos) {
        photoMetadataList.clear();
        if (mAdapter != null && mAdapter.getItemCount() > 0) {
            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                photoMetadataList.add(mAdapter.getItem(i));
            }
        }
        photoMetadataList.addAll(photos);
        mAdapter = new PhotoAdapter(this, (ArrayList<PhotoMetadata>) photoMetadataList, this, isPortrait);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(PhotoMetadata photo) {
        mPresenter.setNavigator(new PhotoNavigator(this, PhotoDetailPagerActivity.class, photo));
        mPresenter.navigateToNewScreen();
    }
}
