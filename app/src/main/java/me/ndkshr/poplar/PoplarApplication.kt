package me.ndkshr.poplar

import android.app.Application
import me.ndkshr.poplar.git.GitManager
import me.ndkshr.poplar.git.JGitManager
import me.ndkshr.poplar.utils.SharedPrefUtils

class PoplarApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this

        SharedPrefUtils.init(this)
    }

    companion object {
        val gitManager: GitManager = JGitManager()
        var appContext: PoplarApplication? = null
            private set
    }
}
