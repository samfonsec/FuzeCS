package com.samfonsec.fuzecs.data.dataSource

import com.samfonsec.fuzecs.data.api.MatchApi
import com.samfonsec.fuzecs.data.repository.MatchRepository
import com.samfonsec.fuzecs.model.Match
import com.samfonsec.fuzecs.model.OpponentsResponse
import retrofit2.Response
import javax.inject.Inject

class MatchDataSource @Inject constructor(
    private val api: MatchApi
) : MatchRepository {

    override suspend fun getRunningMatches(): Response<List<Match>> = api.getRunningMatches()

    override suspend fun getUpcomingMatches(page: Int): Response<List<Match>> = api.getUpcomingMatches(page)

    override suspend fun getOpponents(matchId: Int): Response<OpponentsResponse> = api.getOpponents(matchId)
}