package me.ndkshr.poplar.ui.component

import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebView(
    modifier: Modifier = Modifier,
    reloadTrigger: MutableState<Boolean>
) {

    val mUrl = "https://ndkshr.github.io/ndkshrPoplar"

    AndroidView(
        factory = {
            WebView(it).apply {
                this.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView,
                        request: WebResourceRequest
                    ): Boolean {
                        // Load the URL in the same WebView
                        view.loadUrl(request.url.toString())
                        return true
                    }
                }
                loadUrl(mUrl)
            }
        },
        modifier = modifier,
        update = { webView ->
            // Reload logic
            if (reloadTrigger.value) {
                webView.reload()
                reloadTrigger.value = false // Reset the trigger
            }
        }
    )
}
