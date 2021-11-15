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

    val visits = mutableStateMapOf<String, Int>()

    val sites by mutableStateOf(ALL_SITES)

    init {
        viewModelScope.launch {
            sites.forEach {
                visits[it] = dataStore.data.map { preferences ->
                    preferences[intPreferencesKey(it)]
                }.first() ?: 0
            }
        }
    }

    fun incrementVisits(url: String) {
        val newValue = visits.getOrElse(url) { 0 } + 1
        visits[url] = newValue
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