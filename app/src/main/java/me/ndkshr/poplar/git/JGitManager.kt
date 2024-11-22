package me.ndkshr.poplar.git

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.ndkshr.poplar.MicroBlog
import me.ndkshr.poplar.MicroBlog.Companion.START_OF_BLOG
import me.ndkshr.poplar.PoplarApplication
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import java.io.File
import java.io.FileWriter

class JGitManager : GitManager {
    companion object {
        private const val TAG = "JGitManager"
    }

    override suspend fun cloneRepo(repoUri: String) = withContext(Dispatchers.IO) {
        val tmpDir = createTempDir()
        Git.cloneRepository()
            .setURI(repoUri)
            .setDirectory(tmpDir)
            .call().use {
                Log.d(TAG, "Cloned at ${tmpDir.name}")
            }

        tmpDir
    }

    override suspend fun postBlog(microBlog: MicroBlog) = withContext(Dispatchers.IO) {
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

        "Posted"
    }
}
