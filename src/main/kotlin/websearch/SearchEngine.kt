package websearch

class SearchEngine(private val webMap: Map<URL, WebPage>) {
  private var index: Map<String, List<SearchResult>> = emptyMap()
  fun compileIndex() {
    val urlToWords = webMap.mapValues { entry -> entry.value.extractWords() }
    val pairs = mutableListOf<List<Pair<String, URL>>>()
    urlToWords.keys.forEach { k ->
      pairs.add(
        urlToWords[k]!!.map { w -> Pair(w, k) })
    }
    val pairs2 = pairs.flatten()  // word url pairs in a list
    val wordGroup = pairs2.groupBy { it.first }
    index = wordGroup.mapValues { entry -> rank(entry.value) }
  }


  private fun rank(urlToWords: List<Pair<String, URL>>): List<SearchResult> {
    val uniqueUrls = mutableListOf<URL>()
    val urls = urlToWords.map { it.second }
    for (url in urls) {
      if (uniqueUrls.contains(url).not()) {
        uniqueUrls.add(url)
      }
    }
    val ranks = mutableListOf<SearchResult>()
    for (url in uniqueUrls) {
      val freq = urls.filter { it == url }.size
      ranks.add(SearchResult(url, freq))
    }
    return ranks.sortedBy { -it.numRefs }
  }

  fun searchFor(query: String): SearchResultsSummary {
    return SearchResultsSummary(query, index.getOrDefault(query, listOf()))
  }
}

class SearchResult(val url: URL, val numRefs: Int)

class SearchResultsSummary(val query: String, val results: List<SearchResult>) {
  override fun toString(): String {
    var str = "Result for \"$query\":\n"
    for (result in results) {
      str += "  ${result.url} - ${result.numRefs} references\n"
    }
    return str
  }
}




