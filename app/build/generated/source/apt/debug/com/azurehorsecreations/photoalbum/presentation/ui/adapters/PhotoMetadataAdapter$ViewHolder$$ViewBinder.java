// Generated code from Butter Knife. Do not modify!
package com.azurehorsecreations.photoalbum.presentation.ui.adapters;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PhotoMetadataAdapter$ViewHolder$$ViewBinder<T extends com.azurehorsecreations.photoalbum.presentation.ui.adapters.PhotoMetadataAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493001, "field 'titleTextView'");
    target.titleTextView = finder.castView(view, 2131493001, "field 'titleTextView'");
    view = finder.findRequiredView(source, 2131493002, "field 'descriptionTextView'");
    target.descriptionTextView = finder.castView(view, 2131493002, "field 'descriptionTextView'");
    view = finder.findRequiredView(source, 2131493003, "field 'filenameTextView'");
    target.filenameTextView = finder.castView(view, 2131493003, "field 'filenameTextView'");
  }

  @Override public void unbind(T target) {
    target.titleTextView = null;
    target.descriptionTextView = null;
    target.filenameTextView = null;
  }
}
