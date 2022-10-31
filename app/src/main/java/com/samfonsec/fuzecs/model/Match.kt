package com.samfonsec.fuzecs.model

import java.util.*

data class Match(
    val id: Int,
    val begin_at: Date,
    val games: List<Game>,
    val league: League,
    val serie: Serie,
    val opponents: List<Opponents>,
) {
    fun getLeagueAndSerie() = "${league.name} | ${serie.name}"

    fun getFirstTeam() = opponents.firstOrNull()?.opponent

    fun getSecondTeam() = opponents.getOrNull(SECOND_TEAM_INDEX)?.opponent

    companion object {
        private const val SECOND_TEAM_INDEX = 1
    }
}