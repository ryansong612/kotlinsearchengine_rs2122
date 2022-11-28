package websearch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class URL(val url: String) {
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
}


fun main() {
  val urls = listOf(URL("http://a.com/a"), URL("http://a.com/a"), URL("http://b.com/b"),
    URL("http://a.com/a"))
}
