// Generated code from Butter Knife. Do not modify!
package com.azurehorsecreations.photoalbum.presentation.ui.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PhotoDetailPagerActivity$$ViewBinder<T extends com.azurehorsecreations.photoalbum.presentation.ui.activities.PhotoDetailPagerActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493002, "field 'viewPager'");
    target.viewPager = finder.castView(view, 2131493002, "field 'viewPager'");
  }

  @Override public void unbind(T target) {
    target.viewPager = null;
  }
}
