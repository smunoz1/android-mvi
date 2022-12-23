package cl.mobdev.features.mvi.ui.listuser.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
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
fun DefaultComponent(getListUserEvent: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,) {
        Card(
            border = BorderStroke(1.dp, Color.Blue),
        ) {
            Row {
                Text(
                    text = stringResource(id = R.string.poc_pressing_the_bottom_button_will_launch_a_user_intent),
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

        Button(
            modifier = Modifier.padding(vertical = 50.dp),
            onClick = {
                getListUserEvent()
            }) {
            Text(text = stringResource(id = R.string.poc_get_user))
        }
    }
}
