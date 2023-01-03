package cl.mobdev.features.mvi.ui.listshopping.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cl.mobdev.features.mvi.R

@Composable
fun EmptyComponent(retryEvent: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,) {
        Text(
            text = stringResource(id = R.string.poc_no_data_has_been_found),
            modifier = Modifier.padding(vertical = 20.dp))
        Button(
            onClick = { retryEvent.invoke() },
            modifier = Modifier.padding(vertical = 20.dp)
        ) {
            Text(text = stringResource(id = R.string.poc_retry))
        }
    }
}
