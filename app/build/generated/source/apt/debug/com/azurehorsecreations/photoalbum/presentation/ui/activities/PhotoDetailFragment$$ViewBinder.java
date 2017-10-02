// Generated code from Butter Knife. Do not modify!
package com.azurehorsecreations.photoalbum.presentation.ui.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PhotoDetailFragment$$ViewBinder<T extends com.azurehorsecreations.photoalbum.presentation.ui.activities.PhotoDetailFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558513, "field 'title'");
    target.title = finder.castView(view, 2131558513, "field 'title'");
    view = finder.findRequiredView(source, 2131558514, "field 'description'");
    target.description = finder.castView(view, 2131558514, "field 'description'");
    view = finder.findRequiredView(source, 2131558512, "field 'image'");
    target.image = finder.castView(view, 2131558512, "field 'image'");
  }

  @Override public void unbind(T target) {
    target.title = null;
    target.description = null;
    target.image = null;
  }
}
