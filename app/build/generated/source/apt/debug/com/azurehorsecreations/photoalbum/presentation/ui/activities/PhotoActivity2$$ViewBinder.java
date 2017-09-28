// Generated code from Butter Knife. Do not modify!
package com.azurehorsecreations.photoalbum.presentation.ui.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PhotoActivity2$$ViewBinder<T extends com.azurehorsecreations.photoalbum.presentation.ui.activities.PhotoActivity2> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492973, "field 'mMessageTextView'");
    target.mMessageTextView = finder.castView(view, 2131492973, "field 'mMessageTextView'");
    view = finder.findRequiredView(source, 2131492972, "field 'mRecyclerView'");
    target.mRecyclerView = finder.castView(view, 2131492972, "field 'mRecyclerView'");
    view = finder.findRequiredView(source, 2131492974, "field 'mProgressBar'");
    target.mProgressBar = finder.castView(view, 2131492974, "field 'mProgressBar'");
  }

  @Override public void unbind(T target) {
    target.mMessageTextView = null;
    target.mRecyclerView = null;
    target.mProgressBar = null;
  }
}
