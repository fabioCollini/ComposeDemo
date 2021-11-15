package com.composedemo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor() : ViewModel() {

    val sites by mutableStateOf(
        listOf(
            WebSite("https://www.google.it/"),
            WebSite("https://www.android.com/"),
            WebSite("https://developer.android.com/"),
            WebSite("https://androiddevs.it/"),
        )
    )
}