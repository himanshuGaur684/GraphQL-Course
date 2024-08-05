package gaur.himanshu.graphql.presentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FetchDetails(fetchDetailsViewModel: FetchDetailsViewModel) {

    val uiState by fetchDetailsViewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

    }

    if (uiState.error.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = uiState.error.toString())
        }
    }

    uiState.data?.continent?.let { continent ->
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = continent.name, style = MaterialTheme.typography.headlineLarge)

            continent?.countries?.let {
                it.forEach {
                    Text(text = it.name, style = MaterialTheme.typography.headlineSmall)
                }
            }


        }

    }


}