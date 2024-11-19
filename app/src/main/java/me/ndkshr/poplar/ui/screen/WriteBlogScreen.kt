package me.ndkshr.poplar.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import me.ndkshr.poplar.MicroBlog

@Composable
fun WriteBlogScreen(
    backTrigger: MutableState<Boolean>,
    postBlogTrigger: MutableState<MicroBlog?>,
) {
    val titleContent = remember { mutableStateOf("") }
    val bodyContent = remember { mutableStateOf("") }
    val tagContent = remember { mutableStateOf("") }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (backBtn, pageName, titleView, contentView, tagView, submitBtn) = createRefs()

        Text(
            "Blog +",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .constrainAs(pageName) {
                    top.linkTo(parent.top)
                },
            fontSize = 40.sp
        )

        ElevatedButton(
            {
                backTrigger.value = false
            },
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(backBtn) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        ) {
            Text("Back")
        }

        OutlinedTextField(
            value = titleContent.value,
            label = {Text("Title*")},
            onValueChange = { newValue ->
                titleContent.value = newValue
            },
            maxLines = 3,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .constrainAs(titleView) {
                    top.linkTo(pageName.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        OutlinedTextField(
            value = bodyContent.value,
            label = {Text("Body goes here*")},
            onValueChange = { newValue ->
                bodyContent.value = newValue
            },
            minLines = 15,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .constrainAs(contentView) {
                    top.linkTo(titleView.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        OutlinedTextField(
            value = tagContent.value,
            label = {Text("Tags*")},
            onValueChange = { newValue ->
                tagContent.value = newValue
            },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .constrainAs(tagView) {
                    top.linkTo(contentView.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        ElevatedButton(
            onClick = {
                if (titleContent.value.isNotEmpty() && bodyContent.value.isNotEmpty() && tagContent.value.isNotEmpty()) {
                    postBlogTrigger.value = MicroBlog(
                        titleContent.value, bodyContent.value, tagContent.value
                    )
                    backTrigger.value = false
                }
            },
            Modifier
                .constrainAs(submitBtn) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .padding(20.dp),
            true,
        ) {
            Text("Blog +")
        }
    }
}

@Preview
@Composable
fun WriteBlogScreen_Test() {
    val bt = remember { mutableStateOf(false) }
    val pt = remember { mutableStateOf<MicroBlog?>(null) }
    WriteBlogScreen(bt, pt)
}
