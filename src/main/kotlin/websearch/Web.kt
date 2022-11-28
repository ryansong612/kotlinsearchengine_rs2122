package websearch

import org.jsoup.Jsoup.connect
import org.jsoup.nodes.Document

class URL(val url: String) {
  fun download(): WebPage =
    WebPage(connect(url).get())
  override fun toString() = url
  override fun equals(other: Any?): Boolean {
    return if (other is URL) {
      url == other.url
    } else {
      false
    }
  }
}

class WebPage(val doc: Document) {
  fun extractWords(): List<String> {
    return doc.text()
      .filter { it.isLetter() || it == ' ' }
      .split(' ')
      .map { it.lowercase() }
  }

  fun extractLinks(): List<URL> {
    val aTags = doc.getElementsByTag("a")
    val links = mutableListOf<URL>()
    for (aTag in aTags) {
      if (aTag != null) {
        val link = URL(aTag.attr("href"))
        links.add(link)
      }
    }
    return links
  }
}
