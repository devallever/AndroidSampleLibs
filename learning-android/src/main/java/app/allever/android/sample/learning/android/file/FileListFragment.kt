package app.allever.android.sample.learning.android.file

import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextDetailClickAdapter
import app.allever.android.lib.common.adapter.bean.TextDetailClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.app.App

class FileListFragment : ListFragment<FragmentListBinding, ListViewModel, TextDetailClickItem>() {
    override fun getAdapter() = TextDetailClickAdapter()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getList() = mutableListOf(
        TextDetailClickItem(
            "context.cacheDir.absolutePath",
            App.context.cacheDir.absolutePath
        ) {

        },
        TextDetailClickItem(
            "context.externalCacheDir.absolutePath",
            App.context.externalCacheDir?.absolutePath ?: ""
        ) {

        },
        TextDetailClickItem(
            "context.dataDir.absolutePath(N)",
            App.context.dataDir.absolutePath ?: ""
        ) {

        },
        TextDetailClickItem(
            "context.filesDir.absolutePath",
            App.context.filesDir.absolutePath ?: ""
        ) {

        },
        TextDetailClickItem(
            "context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).absolutePath",
            App.context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath ?: ""
        ) {

        },
        TextDetailClickItem(
            "Environment.getExternalStorageDirectory().absolutePath(过时)",
            Environment.getExternalStorageDirectory().absolutePath
        ) {

        }
    )
}