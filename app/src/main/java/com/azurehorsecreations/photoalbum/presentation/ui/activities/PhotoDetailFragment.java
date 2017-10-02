package com.azurehorsecreations.photoalbum.presentation.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.azurehorsecreations.photoalbum.R;
import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ProductDetailFragment
 * Fragment for the product detail page. Displays photo, title and description.
 **/

public class PhotoDetailFragment extends Fragment {
    private static final String TAG = "PhotoDetailFrag";
    private PhotoMetadata photo;

    @Bind(R.id.title_text)
    TextView title;

    @Bind(R.id.description_text)
    TextView description;

    @Bind(R.id.photo_image)
    ImageView image;

    public static PhotoDetailFragment newInstance(PhotoMetadata photo) {
        PhotoDetailFragment fragmentFirst = new PhotoDetailFragment();
        Bundle args = new Bundle();
        args.putString("title", photo.getTitle());
        args.putString("description", photo.getDescription());
        args.putParcelable("image", photo.getImage());
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photo = new PhotoMetadata();
        photo.setTitle(getArguments().getString("title", ""));
        photo.setDescription(getArguments().getString("description", ""));
        photo.setImage(getArguments().getParcelable("image"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.content_photo_detail, container, false);
        ButterKnife.bind(this, view);

        if (photo.getTitle() != null) {
            title.setText(photo.getTitle());
        }

        if (photo.getDescription() != null) {
            description.setText(photo.getDescription());
        }

        if (photo.getImage() != null) {
            image.setImageBitmap(photo.getImage());
        }
        return view;
    }
}
