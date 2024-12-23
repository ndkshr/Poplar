package me.ndkshr.poplar.git

import me.ndkshr.poplar.modal.MicroBlog
import java.io.File

interface GitManager {
    suspend fun cloneRepo(repoUri: String): File?
    suspend fun persistBlogRemote(microBlog: MicroBlog): String
    suspend fun persistBlogLocal(microBlog: MicroBlog)
}