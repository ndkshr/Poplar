package me.ndkshr.poplar

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class MicroBlog(
    val title: String,
    val content: String,
    val tag: String
) {
        companion object {
            const val START_OF_BLOG = "<!--NDKSHRPOPLAR-->"
            private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        }

        fun toHTML() =
        """
${START_OF_BLOG}

<!--START-->
<h1 class="c2" id="h.hmvd5kvp20sd">
  <span class="c8">${title}</span>
</h1>
<p class="c4">
  <span class="c9">${content}</span>
</p>
<p class="c0">
  <span class="c1"></span>
</p>
<p class="c4">
  <span class="c1">${formatter.format(LocalDateTime.now())}</span>
</p>
<p class="c4">
  <span class="c1">Tag: ${tag}</span>
</p>
<hr>
<p class="c0">
  <span class="c1"></span>
</p>
<p class="c0">
  <span class="c1"></span>
</p>
<!--END-->
            
        """

    fun canPost(): Boolean = title.isNotEmpty() && content.isNotEmpty() && tag.isNotEmpty()
}