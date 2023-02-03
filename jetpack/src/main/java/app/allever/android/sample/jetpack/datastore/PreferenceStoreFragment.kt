package app.allever.android.sample.jetpack.datastore

import androidx.datastore.preferences.core.*
import androidx.lifecycle.lifecycleScope
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextDetailAdapter
import app.allever.android.lib.common.adapter.bean.TextDetailItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.ext.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PreferenceStoreFragment : ListFragment<FragmentListBinding, ListViewModel, TextDetailItem>() {
    override fun getAdapter() = TextDetailAdapter()

    override fun getList() = mutableListOf(
        TextDetailItem(
            "创建PreferenceStore",
            "val Context.dataStore: DataStore<Preferences> \nby preferencesDataStore(name = \"config\")"
        ),
        TextDetailItem(
            "创建key",
            "val KEY_INT = intPreferencesKey(\"KEY_INT\")\n支持int/float/double/long/boolean/string"
        ),
        TextDetailItem(
            "写入值",
            "dataStore.edit {\n" +
                    "    it[KEY_INT] = 1\n" +
                    "}"
        ),
        TextDetailItem(
            "读取值", "val intValue = dataStore.data.map {\n" +
                    "    it[KEY_INT] ?: 0\n" +
                    "}\n" +
                    "intValue.collect {\n" +
                    "    log(\"\${it}\")\n" +
                    "}"
        )
    )

    override fun init() {
        super.init()

        val dataStore = requireContext().dataStore

        val KEY_INT = intPreferencesKey("KEY_INT")
        val KEY_FLOAT = floatPreferencesKey("KEY_FLOAT")
        val KEY_DOUBLE = doublePreferencesKey("KEY_DOUBLE")
        val KEY_LONG = longPreferencesKey("KEY_LONG")
        val KEY_STRING = stringPreferencesKey("KEY_STRING")
        val KEY_BOOLEAN = booleanPreferencesKey("KEY_BOOLEAN")

        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreHelper.putInt("KEY_INT", 100)
            val value = DataStoreHelper.getInt("KEY_INT")
            log("$value")
        }

        lifecycleScope.launch(Dispatchers.IO) {
            dataStore.edit {
                it[KEY_FLOAT] = 2F
            }
            val value = dataStore.data.map {
                it[KEY_FLOAT] ?: 0F
            }

            value.collect {
                log("$it")
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            dataStore.edit {
                it[KEY_DOUBLE] = 3.0
            }
            val value = dataStore.data.map {
                it[KEY_DOUBLE] ?: 0.0
            }
            value.collect {
                log("$it")
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            dataStore.edit {
                it[KEY_LONG] = 1000L
            }
            val value = dataStore.data.map {
                it[KEY_LONG] ?: 0L
            }
            value.collect {
                log("$it")
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            dataStore.edit {
                it[KEY_STRING] = "Hello World"
            }
            val stringValue = dataStore.data.map {
                it[KEY_STRING] ?: "Empty"
            }
            stringValue.collect {
                log(it)
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            dataStore.edit {
                it[KEY_BOOLEAN] = true
            }
            val booleanValue = dataStore.data.map {
                it[KEY_BOOLEAN] ?: false
            }
            booleanValue.collect {
                log("$it")
            }
        }

    }
}