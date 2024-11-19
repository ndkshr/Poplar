package me.ndkshr.poplar.git

import me.ndkshr.poplar.MicroBlog
import org.eclipse.jgit.api.PushCommand
import java.io.File

interface GitManager {
    suspend fun cloneRepo(repoUri: String): File
    suspend fun postBlog(microBlog: MicroBlog): String
}