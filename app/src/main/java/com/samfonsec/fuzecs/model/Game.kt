package com.samfonsec.fuzecs.model

import java.util.*

data class Game(
    val id: Int,
    val match_id: Int,
    val begin_at: Date,
    val end_at: Date,
    val finished: Boolean,
    val status: String
)