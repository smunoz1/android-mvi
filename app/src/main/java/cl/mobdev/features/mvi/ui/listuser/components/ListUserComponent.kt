package cl.mobdev.features.pocmvi.ui.listuser.components
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cl.mobdev.features.mvi.R

@Composable
fun ListUserComponent(listUser: List<String>) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,) {
        item {
            Card(
                border = BorderStroke(1.dp, Color.Blue),
            ) {
                Row {
                    Text(
                        text = stringResource(id = R.string.poc_display_info),
                        modifier = Modifier
                            .padding(20.dp)
                            .weight(1f),
                        textAlign = TextAlign.Center,
                    )
                    Icon(
                        modifier = Modifier
                            .padding(10.dp),
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Info"
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),)
        }
        items(items = listUser) { name ->
            CardItemSection(name = name)
        }
    }
}

@Composable
fun CardItemSection(name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth().padding(bottom = 12.dp),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(text = name)
        }
    }
}


