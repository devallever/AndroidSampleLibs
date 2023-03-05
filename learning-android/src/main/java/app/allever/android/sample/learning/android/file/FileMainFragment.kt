package app.allever.android.sample.learning.android.file

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.text.format.Formatter
import androidx.annotation.RequiresApi
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.lifecycleScope
import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.app.App
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.ext.logE
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.core.function.permission.PermissionHelper
import app.allever.android.lib.core.function.permission.PermissionListener
import app.allever.android.lib.core.util.FileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class FileMainFragment : ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    override fun getAdapter() = TextClickAdapter()

    override fun getList() = mutableListOf(
        TextClickItem("请求权限") {
            requestPermission()
        },
        TextClickItem("文件路径和对应的api") {
            FragmentActivity.start<FileListFragment>(it.title)
        },
        TextClickItem("访问所有应用的缓存目录") {
            test()
        },
        TextClickItem("跳转文件访问权限") {
            if (FileDataHelper.isDataGrant(App.context)) {
                toast("已授权")
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startFor(requireActivity())
                }
            }
        }
    )

    @SuppressLint("WrongConstant")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var uri: Uri? = null
        if (requestCode == 1000 && data?.data.also { uri = it } != null) {
            App.context.contentResolver.takePersistableUriPermission(uri!!,
                data?.flags !! and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            )
        }
    }

    private fun test() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val dataDir = File(App.context.externalCacheDir?.parent ?: "").parent
                val dataDirFile = File("$dataDir")
                log("dataDir = $dataDir")
                val uri = pathToUri(dataDir)
                val documentFile = DocumentFile.fromSingleUri(App.context, uri)
                val list = FileDataHelper.getAndroidDataUri(App.context.contentResolver)
                var totalCacheSize = 0L
                list.map {
                    log("appCacheDir = ${it.key}")
//                    if (it.key == "org.xm.app.text.translator") {
                        val path = "${dataDir}${File.separator}${it.key}${File.separator}cache"
                        val pathDir = File(path)
                        if (pathDir.exists()) {
                            val targetUri = pathToUri(path)
                            val size = FileDataHelper.scanFileForSize(App.context.contentResolver, targetUri)
                            totalCacheSize += size
                            log("缓存大小：${it.key} -> ${Formatter.formatFileSize(App.context, size)}")


//                            val result = FileDataHelper.deleteCurDocument(App.context, targetUri)
//                            if (result) {
//                                log("删除成功")
//                            } else {
//                                logE("删除失败")
//                            }
                        } else {
//                            log("没缓存")
                        }

//                    }
                }
                log("全部缓存大小：${Formatter.formatFileSize(App.context, totalCacheSize)}")
                return@launch
                dataDirFile.list()?.map {
                    val appCacheDir = "${dataDir}${File.separator}${it}${File.separator}cache"
                    log("appCacheDir = $appCacheDir")
                    val file = File(appCacheDir)
                    if (file.exists()) {
                        if (it == "app.allever.android.learning.audiovideo") {
                            val result = FileUtils.delete(file)
                            if (result) {
                                log("删除成功")
                            } else {
                                logE("删除失败")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun requestPermission() {
        PermissionHelper.requestPermission(requireContext(), object : PermissionListener {
            override fun onAllGranted() {
                toast("全部授权")
            }

        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun startFor(activity: Activity) {
        val uri =
            Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fdata")
        val documentFile = DocumentFile.fromTreeUri(activity, uri)
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        intent.flags = (Intent.FLAG_GRANT_READ_URI_PERMISSION
                or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                or Intent.FLAG_GRANT_PREFIX_URI_PERMISSION)
        assert(documentFile != null)
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, documentFile!!.uri)
        startActivityForResult(intent, 1000)
    }

    fun pathToUri(path: String): Uri {
        return Uri.parse(
            "content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fdata/document/primary%3A" + path.replace(
                "/storage/emulated/0/",
                ""
            ).replace("/", "%2F")
        )
    }
}