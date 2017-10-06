package com.azurehorsecreations.photoalbum.presentation.ui.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.azurehorsecreations.photoalbum.R;
import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;
import com.azurehorsecreations.photoalbum.presentation.ui.adapters.PhotoDetailPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ProductDetailPagerActivity
 * Displays the photo details in a swipeable viewer.
 * It currently only displays one fragment.
 **/

public class PhotoDetailPagerActivity extends AppCompatActivity {
    private static final String PHOTO = "PHOTO";
    private PhotoMetadata photo;
    private PhotoMetadata[] photoArray;
    FragmentPagerAdapter adapterViewPager;
    List<Fragment> fragments;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);
        ButterKnife.bind(this);
        Parcelable parcelablePhoto = getIntent().getParcelableExtra("PHOTO");
        photoArray = new PhotoMetadata[1];
        photoArray[0] = (PhotoMetadata)parcelablePhoto;
        fragments = new ArrayList<>();
        fragments.add(PhotoDetailFragment.newInstance(photoArray[0]));
        adapterViewPager = new PhotoDetailPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapterViewPager);
    }
}
