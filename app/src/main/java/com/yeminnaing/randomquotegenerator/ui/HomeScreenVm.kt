package com.yeminnaing.randomquotegenerator.ui

import androidx.compose.ui.input.key.Key.Companion.H
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeminnaing.randomquotegenerator.repositories.GetQuoteRepo
import com.yeminnaing.randomquotegenerator.response.QuoteResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeScreenVm @Inject constructor(
    private val repo: GetQuoteRepo,
) : ViewModel() {
    private val _homeScreenState = MutableStateFlow<HomeScreenStates>(HomeScreenStates.Empty)
    val homeScreenStates = _homeScreenState.asStateFlow()

    init {
        getQuote()
    }

     fun getQuote() {
        _homeScreenState.value= HomeScreenStates.Loading
        val id = (1..1454).random()
        viewModelScope.launch {
          when (val response = repo.getQuote(id)) {
                is com.yeminnaing.randomquotegenerator.Resources.Error -> {
                    _homeScreenState.value=HomeScreenStates.Error(response.error)
                }
                is com.yeminnaing.randomquotegenerator.Resources.Success -> {
                    _homeScreenState.value=HomeScreenStates.Success(response.data)
                }
            }
        }
    }

    sealed interface HomeScreenStates {
        data object Empty : HomeScreenStates
        data object Loading : HomeScreenStates
        data class Success(val data: QuoteResponse) : HomeScreenStates
        data class Error(val error: String) : HomeScreenStates
    }
}