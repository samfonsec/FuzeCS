package com.samfonsec.fuzecs.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.load
import coil.transform.Transformation
import com.samfonsec.fuzecs.R

fun ImageView.loadImageOrPlaceholder(
    imageUri: String?,
    @DrawableRes placeholderResId: Int = R.drawable.placeholder_circle
) {
    imageUri?.let {
        load(it) {
            crossfade(true)
            placeholder(placeholderResId)
        }
    } ?: load(placeholderResId)
}