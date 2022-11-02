package com.samfonsec.fuzecs.data.repository

import com.samfonsec.fuzecs.model.Match
import com.samfonsec.fuzecs.model.OpponentsResponse
import retrofit2.Response

interface MatchRepository {
    suspend fun getRunningMatches(): Response<List<Match>>
    suspend fun getUpcomingMatches(page: Int): Response<List<Match>>
    suspend fun getOpponents(matchId: Int): Response<OpponentsResponse>
}