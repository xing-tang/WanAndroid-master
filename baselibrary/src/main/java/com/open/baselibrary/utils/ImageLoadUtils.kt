package com.open.baselibrary.utils

import android.view.View
import android.widget.ImageView
import com.open.baselibrary.base.app.GlideApp

object ImageLoadUtils {

    fun displayImage(view: View, url: Any?, imageView: ImageView) {
        GlideApp.with(view)
            .load(url)
            .into(imageView)
    }

}