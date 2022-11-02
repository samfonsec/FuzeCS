package com.samfonsec.fuzecs.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class League(
    val id: Int,
    val image_url: String?,
    val name: String?
): Parcelable