package com.azurehorsecreations.photoalbum.presentation.ui;

import android.support.v7.util.DiffUtil;

import com.azurehorsecreations.photoalbum.domain.model.PhotoMetadata;

import java.util.List;

/**
 * PhotoDiffCallback
 * Callback used by DiffUtil to compare two lists for RecyclerView
 */

public class PhotoDiffCallback extends DiffUtil.Callback {
    private final List<PhotoMetadata> mOldPhotoList;
    private final List<PhotoMetadata> mNewPhotoList;

    public PhotoDiffCallback (List<PhotoMetadata> oldPhotoList, List<PhotoMetadata> newPhotoList) {
        this.mOldPhotoList = oldPhotoList;
        this.mNewPhotoList = newPhotoList;
    }

    @Override
    public int getOldListSize() {
        return mOldPhotoList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewPhotoList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldPhotoList.get(oldItemPosition) == mNewPhotoList.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldPhotoList.get(oldItemPosition).filename
                .equals(mNewPhotoList.get(newItemPosition).filename) &&
                mOldPhotoList.get(oldItemPosition).description.equals(mNewPhotoList.get(
                newItemPosition).description);
    }
}
