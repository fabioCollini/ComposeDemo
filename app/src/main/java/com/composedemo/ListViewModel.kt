package com.composedemo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composedemo.utils.Lce
import com.composedemo.utils.getInt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : ViewModel() {

    var state by mutableStateOf<Lce<SitesState>>(Lce.Loading())

    init {
        refresh()
    }

    fun toggleSortOrder() {
        state.data?.toggleSortOrder()
    }

    fun refresh() {
        viewModelScope.launch {
            state = Lce.Loading()
            state = Lce.Success(
                loadState()
            )
        }
    }

    private suspend fun loadState() = SitesState(
        mutableStateListOf(elements = ALL_SITES),
        mutableStateMapOf(pairs = ALL_SITES.map { it to dataStore.getInt(it) }.toTypedArray()),
        sortByName = state.data?.sortByName ?: true,
    ).also { delay(2000) }

    fun incrementVisits(url: String) {
        state.data?.let {
            val newValue = it.incrementVisits(url)
            viewModelScope.launch {
                dataStore.edit { settings ->
                    settings[intPreferencesKey(url)] = newValue
                }
            }
        }
    }

    companion object {
        private val ALL_SITES = arrayOf(
            "https://www.google.it/",
            "https://www.android.com/",
            "https://developer.android.com/",
            "https://androiddevs.it/",
        )
    }
}