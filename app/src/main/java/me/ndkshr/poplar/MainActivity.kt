package me.ndkshr.poplar

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.ndkshr.poplar.modal.MicroBlog
import me.ndkshr.poplar.ui.screen.ListScreen
import me.ndkshr.poplar.ui.screen.WriteBlogScreen
import me.ndkshr.poplar.ui.theme.PoplarTheme

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()
        setContent {
            PoplarTheme {
                val newBlogTrigger = remember { mutableStateOf(false) }
                val postBlogTrigger = remember { mutableStateOf<MicroBlog?>(null) }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (newBlogTrigger.value) {
                        WriteBlogScreen(newBlogTrigger, postBlogTrigger)
                    } else {
                        ListScreen(Modifier, newBlogTrigger)
                    }

                    postBlogTrigger.value?.let { blog ->
                        lifecycleScope.launch {
                            val result = PoplarApplication.gitManager.persistBlogRemote(blog)
                            Log.d(TAG, "$result -> ${blog.title}")
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@MainActivity, "$result -> ${blog.title}", Toast.LENGTH_SHORT).show()
                            }
                            postBlogTrigger.value = null
                        }
                    }
                }
            }
        }
    }

    private fun hideSystemUI() {

        //Hides the ugly action bar at the top
        actionBar?.hide()

        //Hide the status bars

        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }
}
