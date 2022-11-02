package com.samfonsec.fuzecs.model

import android.os.Parcelable
import com.samfonsec.fuzecs.utils.Constants.SECOND_TEAM_INDEX
import kotlinx.parcelize.Parcelize
import kotlin.math.max

@Parcelize
data class Opponents(
    val opponent: Opponent
) : Parcelable

@Parcelize
data class Opponent(
    val id: String,
    val name: String,
    val image_url: String,
    val players: List<Player>
) : Parcelable

data class OpponentsResponse(
    val opponents: List<Opponent>
) {
    fun getPlayers(): List<Pair<Player?, Player?>> {
        val players = mutableListOf<Pair<Player?, Player?>>()
        val team1 = opponents.firstOrNull()
        val team2 = opponents.getOrNull(SECOND_TEAM_INDEX)

        val listSize = max(team1.playerListSize(), team2.playerListSize())
        for (i in 0 until listSize) {
            players.add(i, Pair(team1?.players?.get(i), team2?.players?.get(i)))
        }

        return players
    }

    private fun Opponent?.playerListSize() = this?.players?.size ?: 0
}

