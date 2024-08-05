package gaur.himanshu.graphql.presentation.continents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ContinentScreen(
    modifier: Modifier = Modifier,
    continentsViewModel: ContinentsViewModel,
    onClick: (String) -> Unit
) {

    val uiState by continentsViewModel.uiState.collectAsStateWithLifecycle()

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

    uiState.data?.continents?.let { list ->

        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(list) {
                Card(modifier = Modifier.clickable { onClick.invoke(it.code) }
                    .padding(8.dp)
                    .fillMaxWidth()) {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

        }

    }


}