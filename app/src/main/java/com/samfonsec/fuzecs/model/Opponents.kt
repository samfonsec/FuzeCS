package com.samfonsec.fuzecs.model

data class Opponents(
    val opponent: Opponent
)

data class Opponent(
    val id: String,
    val name: String,
    val image_url: String
)

