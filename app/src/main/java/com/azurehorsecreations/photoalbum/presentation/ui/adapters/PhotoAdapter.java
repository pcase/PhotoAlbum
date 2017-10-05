package com.azurehorsecreations.photoalbum.presentation.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.azurehorsecreations.photoalbum.contants.PhotoConstants;
import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;
import com.azurehorsecreations.photoalbum.R;
import com.azurehorsecreations.photoalbum.presentation.ui.PhotoDiffCallback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * PhotoAdapter
 * Renders a photo item
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
        private ArrayList<PhotoMetadata> photoMetadatasList;
        private Context context;
        private final OnItemClickListener listener;
        private boolean isPortrait;

        public PhotoAdapter(Context context, ArrayList<PhotoMetadata> photoMetadataList, OnItemClickListener listener, boolean isPortrait) {
            this.context = context;
            this.photoMetadatasList = photoMetadataList;
            this.listener = listener;
            this.isPortrait = isPortrait;
        }

        @Override
        public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view;
            if (isPortrait) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photo_row_layout, viewGroup, false);
            } else {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photo_row_layout_landscape, viewGroup, false);
            }
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int pos) {
            viewHolder.click(photoMetadatasList.get(pos), listener);
            viewHolder.photo_title.setText(photoMetadatasList.get(pos).getTitle());
            Picasso.with(context).load(PhotoConstants.PHOTO_DOWNLOAD_URL + photoMetadatasList.get(pos)
                    .getFilename()).resize(PhotoConstants.PHOTO_RESIZE_WIDTH, 0)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            photoMetadatasList.get(pos).setImage(bitmap);
                            viewHolder.photo_image.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    });
        }

        @Override
        public int getItemCount() {
            return photoMetadatasList.size();
        }

        public PhotoMetadata getItem(int id) {
        return photoMetadatasList.get(id);
    }

        public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView photo_image;
            TextView photo_title;
            public ViewHolder(View view) {
                super(view);
                photo_title = (TextView)view.findViewById(R.id.title_text);
                photo_image = (ImageView)view.findViewById(R.id.image_view);
            }

            public void click(final PhotoMetadata photoMetadata, final OnItemClickListener listener) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(photoMetadata);
                    }
                });
            }
        }

    public interface OnItemClickListener {
        void onClick(PhotoMetadata item);
    }

    public void updatePhotoListItems(List<PhotoMetadata> photos) {
//        this.photoMetadatasList.clear();
//        this.photoMetadatasList.addAll(photos);

        final PhotoDiffCallback diffCallback = new PhotoDiffCallback(this.photoMetadatasList, photos);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        diffResult.dispatchUpdatesTo(this);
    }
}
