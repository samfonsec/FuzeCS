package com.samfonsec.fuzecs.utils

import android.widget.ImageView
import coil.load
import com.samfonsec.fuzecs.R

fun ImageView.loadIfNotNull(imageUri: String?) = imageUri?.let {
    load(it) {
        crossfade(true)
        placeholder(R.drawable.img_circle)
    }
}