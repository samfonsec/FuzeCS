package com.samfonsec.fuzecs.utils

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

fun View.show() {
    isVisible = true
}

fun View.hide() {
    isVisible = false
}

fun View.invisible() {
    isInvisible = true
}

