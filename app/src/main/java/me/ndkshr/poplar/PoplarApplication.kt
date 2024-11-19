package me.ndkshr.poplar

import android.app.Application
import me.ndkshr.poplar.git.GitManager
import me.ndkshr.poplar.git.JGitManager

class PoplarApplication: Application() {
    companion object {
        val gitManager: GitManager = JGitManager()
    }
}
