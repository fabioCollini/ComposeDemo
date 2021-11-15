package com.composedemo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class WebSite(
    val url: String,
    val icon: String = "${url}favicon.ico",
) {
    var visits: Int by mutableStateOf(0)
}