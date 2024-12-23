package me.ndkshr.poplar.git

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.ndkshr.poplar.modal.MicroBlog
import me.ndkshr.poplar.modal.MicroBlog.Companion.START_OF_BLOG
import me.ndkshr.poplar.PoplarApplication
import me.ndkshr.poplar.utils.AppUtils
import me.ndkshr.poplar.utils.PrefConst
import me.ndkshr.poplar.utils.SharedPrefUtils
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import java.io.File
import java.io.FileWriter

class JGitManager : GitManager {
    companion object {
        private const val TAG = "JGitManager"
    }

    override suspend fun cloneRepo(repoUri: String) = withContext(Dispatchers.IO) {
        val internalDir = createTempDir()
        val branch = if (AppUtils.isDebug()) {
            "debug"
        } else {
            "main"
        }
        Git.cloneRepository()
            .setBranch(branch)
            .setURI(repoUri)
            .setDirectory(internalDir)
            .call().use {
                Log.d(TAG, "Cloned at ${internalDir?.name}")
                Log.d(TAG, "Branch: $branch")
            }

        internalDir
    }

    override suspend fun persistBlogRemote(microBlog: MicroBlog) = withContext(Dispatchers.IO) {
        val dir = PoplarApplication.gitManager.cloneRepo(Constants.REPO)
        val git = Git.open(dir)

        val readmeFile = File(dir, Constants.INDEX_HTML)

        val docContent = StringBuilder()
        readmeFile.forEachLine { line ->
            docContent.append("$line\n")
        }

        val cont = docContent.replaceFirst(
            Regex(START_OF_BLOG),
            microBlog.toHTML()
        )

        try {
            with(FileWriter(readmeFile)) {
                write("")
                write(cont)
                close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            readmeFile.inputStream().close()
        }

        git.add().addFilepattern(".").call()
        git.commit()
            .setMessage("${microBlog.tag}: ${microBlog.title}")
            .setCommitter(Constants.GIT_USERNAME, Constants.GIT_EMAIL)
            .call()
        val pushCommand = git.push()
        val credentialsProvider = UsernamePasswordCredentialsProvider(Constants.TOKEN, "")
        pushCommand.setCredentialsProvider(credentialsProvider)
        pushCommand.setRemote(Constants.ORIGIN).call()

        // Try persisting locally
        persistBlogLocal(microBlog)

        "Posted"
    }

    override suspend fun persistBlogLocal(microBlog: MicroBlog) = withContext(Dispatchers.IO){
        SharedPrefUtils.saveString(PrefConst.POST_TITLE, microBlog.title)
        SharedPrefUtils.saveString(PrefConst.POST_TAGS, microBlog.tag)
        SharedPrefUtils.saveString(PrefConst.POST_CONTENT, microBlog.content)
    }
}
