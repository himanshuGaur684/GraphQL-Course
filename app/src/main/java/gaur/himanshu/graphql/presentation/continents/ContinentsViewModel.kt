package gaur.himanshu.graphql.presentation.continents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gaur.himanshu.graphql.domain.use_cases.FetchContinentUseCase
import gaur.himanshu.graphql.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import src.main.graphql.ContinentFetchingQuery
import javax.inject.Inject

@HiltViewModel
class ContinentsViewModel @Inject constructor(
    private val fetchContinentUseCase: FetchContinentUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow(ContinentScreen.UiState())
    val uiState: StateFlow<ContinentScreen.UiState> get() = _uiState.asStateFlow()

    init {
        getContinents()
    }

    fun getContinents() = fetchContinentUseCase.invoke()
        .onEach { networkResult ->
            when (networkResult) {
                is NetworkResult.Error -> _uiState.update { ContinentScreen.UiState(error = networkResult.message.toString()) }
                is NetworkResult.Loading -> _uiState.update { ContinentScreen.UiState(isLoading = true) }
                is NetworkResult.Success -> _uiState.update { ContinentScreen.UiState(data = networkResult.data) }
            }

        }.launchIn(viewModelScope)


}


object ContinentScreen {

    data class UiState(
        val isLoading: Boolean = false,
        val error: String = "",
        val data: ContinentFetchingQuery.Data? = null
    )

}