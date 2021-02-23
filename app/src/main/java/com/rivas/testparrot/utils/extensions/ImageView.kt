package com.rivas.testparrot.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rivas.testparrot.AndroidApp

internal fun ImageView.loadImage(image: String) {
    Glide.with(AndroidApp.appContext)
        .load(image)
        .into(this)
}
