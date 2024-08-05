package gaur.himanshu.graphql.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gaur.himanshu.graphql.domain.use_cases.FetchContinentsUseCase
import gaur.himanshu.graphql.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import src.main.graphql.FetchDetailsQuery
import javax.inject.Inject

@HiltViewModel
class FetchDetailsViewModel @Inject constructor(
    private val fetchContinentsUseCase: FetchContinentsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FetchDetails.UiState())
    val uiState: StateFlow<FetchDetails.UiState> get() = _uiState.asStateFlow()

    fun fetchDetails(code: String) = fetchContinentsUseCase.invoke(code)
        .onEach { networkResult ->
            when (networkResult) {
                is NetworkResult.Error -> _uiState.update { FetchDetails.UiState(error = networkResult.message.toString()) }
                is NetworkResult.Loading -> _uiState.update { FetchDetails.UiState(isLoading = true) }
                is NetworkResult.Success -> _uiState.update { FetchDetails.UiState(data = networkResult.data) }
            }
        }.launchIn(viewModelScope)


}

object FetchDetails {

    data class UiState(
        val isLoading: Boolean = false,
        val error: String = "",
        val data: FetchDetailsQuery.Data? = null
    )

}
