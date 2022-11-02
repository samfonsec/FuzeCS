package com.samfonsec.fuzecs.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(
    val id: Int,
    val image_url: String?,
    val first_name: String?,
    val name: String?
) : Parcelable