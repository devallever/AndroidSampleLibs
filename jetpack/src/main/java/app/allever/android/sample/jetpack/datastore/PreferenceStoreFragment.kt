package app.allever.android.sample.jetpack.datastore

import androidx.lifecycle.lifecycleScope
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextDetailAdapter
import app.allever.android.lib.common.adapter.bean.TextDetailItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.ext.log
import kotlinx.coroutines.Dispatchers
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
        val KEY_INT = ("KEY_INT")
        val KEY_FLOAT = ("KEY_FLOAT")
        val KEY_DOUBLE = ("KEY_DOUBLE")
        val KEY_LONG = ("KEY_LONG")
        val KEY_STRING = ("KEY_STRING")
        val KEY_BOOLEAN = ("KEY_BOOLEAN")

        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreHelper.putInt(KEY_INT, 1)
            val value = DataStoreHelper.getInt(KEY_INT)
            log("$value")
        }

        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreHelper.putFloat(KEY_FLOAT, 2F)
            val value = DataStoreHelper.getFloat(KEY_FLOAT)
            log("$value")
        }

        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreHelper.putDouble(KEY_DOUBLE, 3.0)
            val value = DataStoreHelper.getDouble(KEY_DOUBLE)
            log("$value")
        }

        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreHelper.putLong(KEY_LONG, 4L)
            val value = DataStoreHelper.getLong(KEY_LONG)
            log("$value")
        }

        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreHelper.putBoolean(KEY_BOOLEAN, true)
            val value = DataStoreHelper.getBoolean(KEY_BOOLEAN)
            log("$value")
        }

        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreHelper.putString(KEY_STRING, "Hello")
            val value = DataStoreHelper.getString(KEY_STRING)
            log(value)
        }

    }
}