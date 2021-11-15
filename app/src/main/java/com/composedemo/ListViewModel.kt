package com.composedemo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var state by mutableStateOf<Lce<SitesState>>(Lce.Loading)

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            state = Lce.Loading
            state = Lce.Success(
                SitesState(
                    ALL_SITES,
                    mutableStateMapOf()
                ).also { newState ->
                    newState.urls.forEach { url ->
                        newState.visits[url] = dataStore.data.map { preferences ->
                            preferences[intPreferencesKey(url)]
                        }.first() ?: 0
                    }
                }
            )
        }
    }

    fun incrementVisits(url: String) {
        state.data?.let {
            val newValue = it.visits.getOrElse(url) { 0 } + 1
            it.visits[url] = newValue
            viewModelScope.launch {
                dataStore.edit { settings ->
                    settings[intPreferencesKey(url)] = newValue
                }
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