package com.samfonsec.fuzecs.data.api

import com.samfonsec.fuzecs.model.MatchesResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchApi {

    @GET(MATCHES_API)
    suspend fun getMatches(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = PAGE_LIMIT
    ): MatchesResult

    @GET(MATCHES_API)
    suspend fun getMatchDetail(
        @Query("id") matchId: Int
    ): MatchesResult

    companion object {
        private const val MATCHES_API = "matches/upcoming"
        private const val MATCH_DETAILS_API = "character/{id}/quote"

        private const val PAGE_LIMIT = 30
    }
}