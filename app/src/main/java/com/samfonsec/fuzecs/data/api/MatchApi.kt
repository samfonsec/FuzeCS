package com.samfonsec.fuzecs.data.api

import com.samfonsec.fuzecs.model.DetailsResponse
import com.samfonsec.fuzecs.model.Match
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchApi {

    @GET(RUNNING_MATCHES_API)
    suspend fun getRunningMatches(): Response<List<Match>>

    @GET(UPCOMING_MATCHES_API)
    suspend fun getUpcomingMatches(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = PAGE_LIMIT
    ): Response<List<Match>>

    @GET(MATCH_DETAILS_API)
    suspend fun getMatchDetails(
        @Query("id") matchId: Int
    ): Response<DetailsResponse>

    companion object {
        private const val RUNNING_MATCHES_API = "matches/running"
        private const val UPCOMING_MATCHES_API = "matches/upcoming"
        private const val MATCH_DETAILS_API = "matches/{id}"

        private const val PAGE_LIMIT = 30
    }
}