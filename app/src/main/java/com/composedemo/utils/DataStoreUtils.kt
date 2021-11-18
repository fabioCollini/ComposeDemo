package com.composedemo.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


suspend fun <T> DataStore<Preferences>.get(key: Preferences.Key<T>): T? {
    return data.map { it[key] }.first()
}

suspend fun DataStore<Preferences>.getInt(key: String, defaultValue: Int = 0): Int {
    return data.map { it[intPreferencesKey(key)] }.first() ?: defaultValue
}
