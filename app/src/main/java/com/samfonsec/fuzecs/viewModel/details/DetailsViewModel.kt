package com.samfonsec.fuzecs.viewModel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samfonsec.fuzecs.data.api.Status
import com.samfonsec.fuzecs.data.repository.MatchRepository
import com.samfonsec.fuzecs.model.Match
import com.samfonsec.fuzecs.model.OpponentsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MatchRepository
) : ViewModel() {

    private val _onResult = MutableLiveData<Status<OpponentsResponse>>()
    val onResult: LiveData<Status<OpponentsResponse>> = _onResult

    fun getOpponents(matchId: Int) = viewModelScope.launch {
        _onResult.postValue(Status.Loading)
        repository.getOpponents(matchId).run {
            if (isSuccessful) {
                _onResult.postValue(Status.Success(body()))
            } else
                _onResult.postValue(Status.Error(0))
        }
    }
}