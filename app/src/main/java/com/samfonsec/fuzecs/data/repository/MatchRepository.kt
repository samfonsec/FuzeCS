package com.samfonsec.fuzecs.data.repository

import com.samfonsec.fuzecs.model.DetailsResponse
import com.samfonsec.fuzecs.model.Match
import retrofit2.Response

interface MatchRepository {

    suspend fun getRunningMatches(): Response<List<Match>>
    suspend fun getUpcomingMatches(page: Int): Response<List<Match>>
    suspend fun getMatchDetails(matchId: Int): Response<DetailsResponse>
}