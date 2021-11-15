package com.composedemo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor() : ViewModel() {

    private val visits = mutableStateMapOf<String, Int>()

    val sites by mutableStateOf(
        listOf(
            createHolder("https://www.google.it/"),
            createHolder("https://www.android.com/"),
            createHolder("https://developer.android.com/"),
            createHolder("https://androiddevs.it/"),
        )
    )

    private fun createHolder(url: String) =
        WebSiteHolder(url, visits) {
            visits[url] = visits.getOrElse(url) { 0 } + 1
        }
}