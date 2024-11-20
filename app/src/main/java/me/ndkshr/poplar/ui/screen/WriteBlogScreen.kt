package me.ndkshr.poplar.ui.screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import me.ndkshr.poplar.MicroBlog
import me.ndkshr.poplar.ui.component.SaveBlogDialog
import me.ndkshr.poplar.ui.theme.PoplarTheme
import me.ndkshr.poplar.ui.theme.Purple80

@Composable
fun WriteBlogScreen(
    backTrigger: MutableState<Boolean>,
    postBlogTrigger: MutableState<MicroBlog?>,
) {
    var microBlog: MicroBlog? = null

    val dialogTrigger = remember { mutableStateOf(false) }

    val titleContent = remember { mutableStateOf("") }
    val bodyContent = remember { mutableStateOf("") }
    val tagContent = remember { mutableStateOf("") }
    val context = LocalContext.current

    BackHandler {
        backTrigger.value = false
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (backBtn, pageName, titleView, contentView, tagView, submitBtn) = createRefs()

        Text(
            "Blog +",
            modifier = Modifier
                .wrapContentWidth()
                .padding(16.dp)
                .constrainAs(pageName) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            fontSize = 40.sp,
            color = Color.Black,
            textDecoration = TextDecoration.Underline
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

        TextField(
            value = tagContent.value,
            label = { Text("Tags*", fontSize = 10.sp) },
            onValueChange = { newValue ->
                tagContent.value = newValue
            },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .constrainAs(tagView) {
                    top.linkTo(titleView.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            textStyle = TextStyle(fontSize = 12.sp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Color.Black,
                unfocusedLabelColor = Color.Unspecified,
                focusedLabelColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        TextField(
            value = titleContent.value,
            label = { Text("Title*") },
            onValueChange = { newValue ->
                titleContent.value = newValue
            },
            maxLines = 3,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                .constrainAs(titleView) {
                    top.linkTo(pageName.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            textStyle = TextStyle(fontSize = 40.sp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Color.Black,
                unfocusedLabelColor = Color.Unspecified,
                focusedLabelColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        TextField(
            value = bodyContent.value,
            label = { Text("Body goes here*") },
            onValueChange = { newValue ->
                bodyContent.value = newValue
            },
            minLines = 5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .constrainAs(contentView) {
                    top.linkTo(tagView.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Color.Black,
                unfocusedLabelColor = Color.Unspecified,
                focusedLabelColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        ElevatedButton(
            onClick = {
                microBlog =
                    MicroBlog(titleContent.value, bodyContent.value, tagContent.value)
                if (microBlog?.canPost() == true) {
                    dialogTrigger.value = true
                } else {
                    Toast.makeText(context, "Some Fields are empty", Toast.LENGTH_SHORT).show()
                }
            },
            Modifier
                .constrainAs(submitBtn) {
                    top.linkTo(backBtn.top)
                    bottom.linkTo(backBtn.bottom)
                    end.linkTo(backBtn.start)
                }
                .padding(end = 16.dp),
            colors = ButtonColors(Purple80, Color.Black, Color.White, Color.White),
            enabled = true,
        ) { Text("Post") }

        if (dialogTrigger.value) {
            SaveBlogDialog(
                onDismissRequest = { dialogTrigger.value = false },
                onConfirmation = {
                    dialogTrigger.value = false
                    postBlogTrigger.value = microBlog
                    backTrigger.value = false
                    println("Confirmation registered") // Add logic here to handle confirmation.
                },
                dialogTitle = "Publish this micro-blog?",
                dialogText = "Confirmation will save and push the micro-blog to GitHub. You will not be able to make changes to the contents.",
                icon = Icons.Default.Info
            )
        }


    }
}

@Preview
@Composable
fun WriteBlogScreen_Test() {
    val bt = remember { mutableStateOf(false) }
    val pt = remember { mutableStateOf<MicroBlog?>(null) }
    PoplarTheme(darkTheme = true) {
        WriteBlogScreen(bt, pt)
    }
}
