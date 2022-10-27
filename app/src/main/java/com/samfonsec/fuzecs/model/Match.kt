package com.samfonsec.fuzecs.model

import java.util.*

data class Match(
    val id: Int,
    val begin_at: Date,
    val games: List<Game>,
    val league: League,
)