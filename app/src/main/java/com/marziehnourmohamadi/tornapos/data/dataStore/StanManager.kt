package com.marziehnourmohamadi.tornapos.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Locale

private val Context.dataStore by preferencesDataStore(name = "iso_prefs")

class StanManager(private val context: Context) {
    companion object {
        private val KEY_LAST_STAN = intPreferencesKey("last_stan")
    }
    val stanFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[KEY_LAST_STAN] ?: 0
    }
    suspend fun getNextStan(): String {
        val current = stanFlow.first()
        val next = if (current >= 999_999) 1 else current + 1
        context.dataStore.edit { settings ->
            settings[KEY_LAST_STAN] = next
        }
        return String.format(Locale.US,"%06d", next)
    }
}