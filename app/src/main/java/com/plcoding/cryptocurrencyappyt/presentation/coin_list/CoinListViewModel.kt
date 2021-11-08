package com.plcoding.cryptocurrencyappyt.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coins.GetCoinsUseCase
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coins.SearchCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
    private val searchCoinsUseCase: SearchCoinsUseCase
) : ViewModel() {

    private val _state = mutableStateOf<CoinListState>(CoinListState())
    val state: State<CoinListState> = _state

    private var fullCoinList: List<Coin> = emptyList()

    init {
        getCoins()
    }

    fun onEvent(event: CoinListEvents) {
        when (event) {
            CoinListEvents.EndSearch -> {
                _state.value = state.value.copy(isSearching = false, searchQuery = "", coins = fullCoinList ?: emptyList())
            }
            is CoinListEvents.Search -> {
                val filteredList: List<Coin> = if (!fullCoinList.isEmpty()) {
                    searchCoinsUseCase(fullCoinList, event.query)
                } else {
                    emptyList()
                }
                _state.value = state.value.copy(searchQuery = event.query, coins = filteredList)
            }
            CoinListEvents.StartSearch -> {
                _state.value = state.value.copy(isSearching = true)
            }
        }
    }

    private fun getCoins() {
        getCoinsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CoinListState(coins = result.data ?: emptyList())
                    result.data?.let {
                        fullCoinList = it
                    }
                }
                is Resource.Error -> {
                    _state.value = CoinListState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = CoinListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}