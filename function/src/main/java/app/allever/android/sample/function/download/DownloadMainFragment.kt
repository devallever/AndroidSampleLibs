package app.allever.android.sample.function.download

import android.Manifest
import android.os.Build
import androidx.lifecycle.lifecycleScope
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.function.imageloader.ImageLoader
import app.allever.android.lib.core.function.permission.PermissionHelper
import app.allever.android.lib.core.function.permission.PermissionListener
import app.allever.android.lib.core.helper.copyToAlbum
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.function.R
import kotlinx.coroutines.launch

/**
 *@Description
 *@author: zq
 *@date: 2024/1/16
 */
class DownloadMainFragment : ListFragment<FragmentListBinding, BaseViewModel, TextClickItem>() {

    private val permissionsList = java.util.ArrayList<String>().apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    override fun getAdapter() = TextClickAdapter()

    override fun getList(): MutableList<TextClickItem> = mutableListOf(
        TextClickItem("下载并保存图片") {
            performDownload()
        }
    )

    private fun performDownload() {
        //
        if (PermissionHelper.hasPermissionOrigin(requireContext(), permissionsList)) {
            downloadAndSave()
        } else {
            PermissionHelper.requestPermission(
                requireContext(), object : PermissionListener {
                    override fun onAllGranted() {
                        downloadAndSave()
                    }
                }, permissionsList
            )
        }
    }

    private fun downloadAndSave() {
        lifecycleScope.launch {
            val url = "https://img0.baidu.com/it/u=962361882,2281204904&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500"
            ImageLoader.download(url) {success, file ->
                file?.copyToAlbum(requireContext(), file.name, "AndroidSampleLibs")
            }
        }
    }
}