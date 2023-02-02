package app.allever.android.sample.jetpack.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import app.allever.android.lib.core.app.App
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "config")

object DataStoreHelper {
    private val mDatsStore = App.context.dataStore
    suspend fun putInt(key: String, value: Int) {
        val KEY = intPreferencesKey(key)
        mDatsStore.edit {
            it[KEY] = value
        }
    }

    suspend fun getInt(key: String, block: (value: Int) -> Unit) {
        val KEY = intPreferencesKey(key)
        val value = mDatsStore.data.map {
            it[KEY] ?: 0
        }

        value.collect {
            block(it)
        }
    }
}