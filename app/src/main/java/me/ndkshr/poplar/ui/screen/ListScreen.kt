package me.ndkshr.poplar.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import me.ndkshr.poplar.ui.component.WebView
import me.ndkshr.poplar.ui.theme.PoplarTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ListScreen(modifier: Modifier = Modifier, newBlogTrigger: MutableState<Boolean>) {
    val reloadTrigger = remember { mutableStateOf(false) }

    ConstraintLayout {
        val (reloadButton, addBlogButton, webView) = createRefs()

        WebView(Modifier, reloadTrigger)

        IconButton (
            onClick = {
                reloadTrigger.value = true
            },
            Modifier.constrainAs(reloadButton) {
                top.linkTo(parent.top, 16.dp)
                start.linkTo(parent.start, 16.dp)
            },
            true
        ) {
            Icon(Icons.Default.Refresh, contentDescription = "Example Icon", tint = Color.Black)
        }

        ElevatedButton(
            onClick = {
                newBlogTrigger.value = true
            },
            modifier
                .constrainAs(addBlogButton) {
                    top.linkTo(reloadButton.top)
                    bottom.linkTo(reloadButton.bottom)
                    end.linkTo(parent.end)
                }
                .wrapContentWidth()
                .padding(16.dp),
            true,
        ) {
            Text("Blog +")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    val state = remember { mutableStateOf(false) }
    PoplarTheme {
        ListScreen(Modifier, state)
    }
}

@Preview
@Composable
fun FilledIconButton_Test() {
    PoplarTheme {
        ElevatedButton(
            {},
            Modifier,
            true
        ) {
            Text("Reload")
        }
    }
}