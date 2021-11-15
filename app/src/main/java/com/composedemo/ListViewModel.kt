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

class SitesState(
    val urls: List<String>,
    val visits: MutableMap<String, Int>,
)

@HiltViewModel
class ListViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : ViewModel() {

    val state by mutableStateOf(
        SitesState(
            ALL_SITES,
            mutableStateMapOf()
        )
    )

    init {
        viewModelScope.launch {
            state.urls.forEach { url ->
                state.visits[url] = dataStore.data.map { preferences ->
                    preferences[intPreferencesKey(url)]
                }.first() ?: 0
            }
        }
    }

    fun incrementVisits(url: String) {
        val newValue = state.visits.getOrElse(url) { 0 } + 1
        state.visits[url] = newValue
        viewModelScope.launch {
            dataStore.edit { settings ->
                settings[intPreferencesKey(url)] = newValue
            }
        }
    }

    companion object {
        private val ALL_SITES = listOf(
            "https://www.google.it/",
            "https://www.android.com/",
            "https://developer.android.com/",
            "https://androiddevs.it/",
        )
    }
}