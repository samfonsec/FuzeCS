package com.samfonsec.fuzecs.data.api

import com.samfonsec.fuzecs.model.Match
import com.samfonsec.fuzecs.model.OpponentsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MatchApi {

    @GET(RUNNING_MATCHES_API)
    suspend fun getRunningMatches(): Response<List<Match>>

    @GET(UPCOMING_MATCHES_API)
    suspend fun getUpcomingMatches(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = PAGE_LIMIT
    ): Response<List<Match>>

    @GET(OPPONENTS_API)
    suspend fun getOpponents(
        @Path("id") matchId: Int
    ): Response<OpponentsResponse>

    companion object {
        private const val RUNNING_MATCHES_API = "csgo/matches/running"
        private const val UPCOMING_MATCHES_API = "csgo/matches/upcoming"
        private const val OPPONENTS_API = "matches/{id}/opponents"

        private const val PAGE_LIMIT = 30
    }
}