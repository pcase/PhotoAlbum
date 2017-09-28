package com.azurehorsecreations.photoalbum.presentation.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azurehorsecreations.photoalbum.contants.PhotoConstants;
import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;
import com.azurehorsecreations.photoalbum.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by pattycase on 9/27/17.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
        private ArrayList<PhotoMetadata> photoMetadatasList;
        private Context context;
        private final OnItemClickListener listener;

        public PhotoAdapter(Context context, ArrayList<PhotoMetadata> photoMetadataList, OnItemClickListener listener) {
            this.context = context;
            this.photoMetadatasList = photoMetadataList;
            this.listener = listener;
        }

        @Override
        public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photo_row_layout, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int pos) {
            viewHolder.click(photoMetadatasList.get(pos), listener);
            viewHolder.photo_title.setText(photoMetadatasList.get(pos).getTitle());
            Picasso.with(context).load(PhotoConstants.PHOTO_DOWNLOAD_URL + photoMetadatasList.get(pos).getFilename()).resize(120, 60).into(viewHolder.photo_image);
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
}
