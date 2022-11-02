package com.samfonsec.fuzecs.viewModel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samfonsec.fuzecs.data.api.Status
import com.samfonsec.fuzecs.data.repository.MatchRepository
import com.samfonsec.fuzecs.model.Match
import com.samfonsec.fuzecs.utils.Constants.FIRST_PAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MatchRepository
) : ViewModel() {

    private val _onResult = MutableLiveData<Status<List<Match>>>()
    val onResult: LiveData<Status<List<Match>>> = _onResult

    private val liveMatches: MutableList<Match> = mutableListOf()

    fun getRunningMatches() = viewModelScope.launch {
        _onResult.postValue(Status.Loading)
        repository.getRunningMatches().run {
            if (isSuccessful) {
                body()?.let { liveMatches += it }
                getUpcomingMatches(FIRST_PAGE)
            } else
                _onResult.postValue(Status.Error(RUNNING_MATCHES_ERROR))
        }
    }

    fun loadMoreUpcomingMatches(page: Int) = viewModelScope.launch {
        _onResult.postValue(Status.Loading)
        getUpcomingMatches(page, true)
    }

    private suspend fun getUpcomingMatches(page: Int, error: Boolean = false) {
        repository.getUpcomingMatches(page).run {
            if (isSuccessful) {
                val list = arrayListOf<Match>()
                if (page == FIRST_PAGE)
                    list += liveMatches

                body()?.let { list += it }
                _onResult.postValue(Status.Success(list.filter { it.getFirstTeam() != null && it.getSecondTeam() != null }))
            } else
                _onResult.postValue(Status.Error(UPCOMING_MATCHES_ERROR))
        }
    }

    companion object {
        const val RUNNING_MATCHES_ERROR = 0
        const val UPCOMING_MATCHES_ERROR = 1
    }
}