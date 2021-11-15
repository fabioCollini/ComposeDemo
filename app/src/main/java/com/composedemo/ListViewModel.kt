package com.composedemo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : ViewModel() {

    private val visits = mutableStateMapOf<String, Int>()

    val sites by mutableStateOf(
        listOf(
            createHolder("https://www.google.it/"),
            createHolder("https://www.android.com/"),
            createHolder("https://developer.android.com/"),
            createHolder("https://androiddevs.it/"),
        )
    )

    init {
        viewModelScope.launch {
            sites.forEach {
                visits[it.url] = dataStore.data.map { preferences ->
                    preferences[intPreferencesKey(it.url)]
                }.first() ?: 0
            }
        }
    }

    private fun createHolder(url: String) =
        WebSiteHolder(url, visits) {
            incrementVisits(url)
        }

    private fun incrementVisits(url: String) {
        val newValue = visits.getOrElse(url) { 0 } + 1
        visits[url] = newValue
        viewModelScope.launch {
            dataStore.edit { settings ->
                settings[intPreferencesKey(url)] = newValue
            }
        }
    }
}