package com.samfonsec.fuzecs.model

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

data class Match(
    val id: Int,
    val begin_at: Date?,
    val league: League,
    val serie: Serie,
    val status: String,
    val opponents: List<Opponents>,
) {
    fun getLeagueAndSerie() = serie.name.takeIf { !it.isNullOrEmpty() }?.let {
        "${league.name}\n$it"
    } ?: league.name

    fun getFirstTeam() = opponents.firstOrNull()?.opponent

    fun getSecondTeam() = opponents.getOrNull(SECOND_TEAM_INDEX)?.opponent

    fun getFormattedTime(todayText: String): String? = begin_at?.run {
        val pattern = when {
            isToday() -> "'$todayText', $TIME_FORMAT"
            isInTheCurrentWeek() -> CURRENT_WEEK_FORMAT
            else -> DEFAULT_FORMAT
        }
        val locale = Locale.getDefault()
        return SimpleDateFormat(pattern, locale).format(this)
            .replaceFirstChar { it.titlecase(locale) }
            .apply {
                if (isInTheCurrentWeek())
                    replace(".", "")
            }
    }

    fun isLive() = status == MatchStatus.RUNNING.name.lowercase()

    private fun Date.isToday() = DateUtils.isToday(time)

    private fun Date.isInTheCurrentWeek(): Boolean {
        val currentCalendar = Calendar.getInstance()
        val week = currentCalendar[Calendar.WEEK_OF_YEAR]
        val year = currentCalendar[Calendar.YEAR]

        val targetCalendar = Calendar.getInstance()
        targetCalendar.time = this
        val targetWeek = targetCalendar[Calendar.WEEK_OF_YEAR]
        val targetYear = targetCalendar[Calendar.YEAR]
        return week == targetWeek && year == targetYear
    }

    companion object {
        private const val SECOND_TEAM_INDEX = 1
        private const val TIME_FORMAT = "HH:mm"
        private const val CURRENT_WEEK_FORMAT = "EE, $TIME_FORMAT"
        private const val DEFAULT_FORMAT = "dd.MM, $TIME_FORMAT"
    }
}