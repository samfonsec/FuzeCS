package com.samfonsec.fuzecs.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samfonsec.fuzecs.data.api.Status
import com.samfonsec.fuzecs.data.repository.MatchRepository
import com.samfonsec.fuzecs.model.Match
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MatchRepository
) : ViewModel() {

    private val _onResult = MutableLiveData<Status<List<Match>>>()
    val onResult: LiveData<Status<List<Match>>> = _onResult

    fun getMatches(page: Int) = viewModelScope.launch {
        _onResult.postValue(Status.Loading)
        repository.getMatches(page).run {
            if (isSuccessful)
                _onResult.postValue(Status.Success(body()))
            else
                _onResult.postValue(Status.Error(errorBody().toString()))
        }
    }
}