package com.composedemo

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor() : ViewModel() {

    val sites = mutableStateListOf(
        "https://www.google.it/",
        "https://www.android.com/",
        "https://developer.android.com/",
        "https://androiddevs.it/",
    )
}