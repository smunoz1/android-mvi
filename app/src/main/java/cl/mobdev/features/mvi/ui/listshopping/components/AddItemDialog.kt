package cl.mobdev.features.mvi.ui.listshopping.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cl.mobdev.features.mvi.R

@Composable
fun AddItemDialog(show: Boolean, onDismiss: () -> Unit, onItemAdded: (String) -> Unit) {
    var myItem by remember { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.poc_add_an_item),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp),
                    fontWeight = FontWeight.Bold
                )
                TextField(
                    value = myItem,
                    onValueChange = { myItem = it },
                    singleLine = true,
                    maxLines = 1,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(onClick = {
                    onItemAdded(myItem)
                    myItem = ""
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(id = R.string.poc_add_item),)
                }
            }
        }
    }
}