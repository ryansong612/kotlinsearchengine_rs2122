package websearch

import java.lang.IllegalArgumentException

class WebCrawler(val url: URL, private var limit: Int = 10) {
  val cMap = mutableMapOf<URL, WebPage>()
  fun run(u: URL = url, l: Int = limit): Int {
    if (limit == 0)
      return 0
    val webPage1 = url.download()
    if (!cMap.keys.contains(url)) {
      cMap[url] = webPage1
    }
    var nl = l - 1
    val links = webPage1.extractLinks()
    for (link in links) {
      nl = run(link, nl)
    }
    return nl
  }
}
