package websearch

class WebCrawler(val startFrom: URL, private var limit: Int = 10) {
  val cMap = mutableMapOf<URL, WebPage>()
  val cList = mutableListOf<URL>()
  val errors = mutableListOf<URL>()
  fun run(u: URL = startFrom, l: Int = limit): Int {
    if (l == 0)
      return 0
    val webPage1: WebPage
    try {
      webPage1 = u.download()
    } catch (e: Throwable) {
      println("Invalid URL")
      return l
    }
    if (!cList.contains(u)) {
      cList.add(u)
      cMap[u] = webPage1
    } else {
      return l
    }
    println(u)
    var nl = l - 1
    val links = webPage1.extractLinks()
    println(links)
    for (link in links) {
      nl = run(link, nl)
    }
    return nl
  }

  fun dump(): Map<URL, WebPage> = cMap
}

fun main() {
  val crawler = WebCrawler(startFrom = URL("http://www.bbc.co.uk"))
  crawler.run()
  val searchEngine = SearchEngine(crawler.dump())
  searchEngine.compileIndex()
  println(searchEngine.searchFor("news"))
  val stuff = mutableMapOf((URL("https://www.bbc.co.uk") to "Happy"))
  println(stuff.containsKey(URL("https://www.bbc.co.uk")))
}
