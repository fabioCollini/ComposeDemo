package com.composedemo

class WebSiteHolder(
    val url: String,
    private val visitsMap: Map<String, Int>,
    val incrementVisit: () -> Unit,
) {
    val icon: String = "${url}favicon.ico"

    val visits: Int get() = visitsMap[url] ?: 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WebSiteHolder

        if (url != other.url) return false

        return true
    }

    override fun hashCode() = url.hashCode()
}