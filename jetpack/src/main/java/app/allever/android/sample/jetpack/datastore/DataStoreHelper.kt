package app.allever.android.sample.jetpack.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import app.allever.android.lib.core.app.App
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "config")

object DataStoreHelper {
    private val mDatsStore = App.context.dataStore

    suspend fun putInt(key: String, value: Int) {
        mDatsStore.edit {
            it[intPreferencesKey(key)] = value
        }
    }

    suspend fun getInt(key: String): Int {
        return mDatsStore.data.map {
            it[intPreferencesKey(key)] ?: 0
        }.first()
    }
//
//    private fun getPreferenceKey(key: String, value: Any): Preferences.Key<*>? {
//
//    }
}