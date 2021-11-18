package com.composedemo

class SitesState(
    val urls: MutableList<String>,
    val visits: MutableMap<String, Int>,
    private var sortByName: Boolean = true,
) {
    init {
        sort()
    }

    fun toggleSortOrder() {
        sortByName = !sortByName
        sort()
    }

    fun incrementVisits(url: String): Int {
        return (visits.getOrElse(url) { 0 } + 1).also { newValue ->
            visits[url] = newValue
            sort()
        }
    }

    private fun sort() {
        if (sortByName) {
            urls.sortBy { it }
        } else {
            urls.sortByDescending { visits[it] ?: 0 }
        }
    }
}