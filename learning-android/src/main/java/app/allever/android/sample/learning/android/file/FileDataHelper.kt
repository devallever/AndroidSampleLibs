package app.allever.android.sample.learning.android.file

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.documentfile.provider.DocumentFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *    author : fySpring
 *    date   : 2021/3/18 7:00 PM
 *    desc   : 针对Android R 的适配，实现访问 Android/data 目录
 */
object FileDataHelper {

    private const val EXTERNAL_STORAGE_PROVIDER_AUTHORITY =
        "com.android.externalstorage.documents"
    private const val ANDROID_DOCUMENT_ID = "primary:Android"
    //如果你需要访问 obb 目录，把 data 改成 obb 即可
    private const val ANDROID_DATA_DOCUMENT_ID = "primary:Android/data"

    private val androidDataTreeUri = DocumentsContract.buildTreeDocumentUri(
        EXTERNAL_STORAGE_PROVIDER_AUTHORITY,
        ANDROID_DATA_DOCUMENT_ID
    )

    // Android/data 目录 uri
    private val androidChildDataTreeUri = DocumentsContract.buildChildDocumentsUriUsingTree(
        androidDataTreeUri,
        ANDROID_DATA_DOCUMENT_ID
    )


    //获取 data目录下所有文件夹uri
    @SuppressLint("Range")
    suspend fun getAndroidDataUri(contentResolver: ContentResolver): MutableMap<String, Uri> = withContext(Dispatchers.IO){
        val packageMap: MutableMap<String, Uri> = mutableMapOf()
        val cursor = contentResolver.query(androidChildDataTreeUri,
            arrayOf(DocumentsContract.Document.COLUMN_DISPLAY_NAME,
                DocumentsContract.Document.COLUMN_DOCUMENT_ID,
                DocumentsContract.Document.COLUMN_MIME_TYPE), null, null, null)
        cursor?.let {
            while (it.moveToNext()) {
                val name = it.getString(it.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME))
                val id = it.getString(it.getColumnIndex(DocumentsContract.Document.COLUMN_DOCUMENT_ID))
                val type = it.getString(it.getColumnIndex(DocumentsContract.Document.COLUMN_MIME_TYPE))
                if (type == DocumentsContract.Document.MIME_TYPE_DIR) {
                    packageMap[name] = DocumentsContract.buildChildDocumentsUriUsingTree(androidChildDataTreeUri, id)
                }
            }
        }
        cursor?.close()
        return@withContext packageMap
    }

    //获取URI下 cache 目录，替换名称也可以获取其他目录
    @SuppressLint("Range")
    suspend fun getAndroidDataCacheUri(contentResolver: ContentResolver, uri: Uri): Uri? = withContext(Dispatchers.IO) {
        var result: Uri? = null
        val cursor = contentResolver.query(uri,
            arrayOf(DocumentsContract.Document.COLUMN_DISPLAY_NAME, DocumentsContract.Document.COLUMN_DOCUMENT_ID, DocumentsContract.Document.COLUMN_MIME_TYPE),
            null, null, null)
        cursor?.let {
            while (it.moveToNext()) {
                val name = it.getString(it.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME))
                val type = it.getString(it.getColumnIndex(DocumentsContract.Document.COLUMN_MIME_TYPE))
                if (type == DocumentsContract.Document.MIME_TYPE_DIR && name.toLowerCase() == "cache") {
                    val id = it.getString(it.getColumnIndex(DocumentsContract.Document.COLUMN_DOCUMENT_ID))
                    result = DocumentsContract.buildChildDocumentsUriUsingTree(uri, id)
                }
            }
        }
        cursor?.close()
        return@withContext result
    }


    @SuppressLint("Range")
    suspend fun scanFile(contentResolver: ContentResolver, uri: Uri): MutableList<DocumentData> = withContext(Dispatchers.IO){
        val documentList = mutableListOf<DocumentData>()
        val cursor = contentResolver.query(
            uri,
            arrayOf(
                DocumentsContract.Document.COLUMN_DISPLAY_NAME,
                DocumentsContract.Document.COLUMN_MIME_TYPE,
                DocumentsContract.Document.COLUMN_DOCUMENT_ID,
                DocumentsContract.Document.COLUMN_SIZE,
                DocumentsContract.Document.COLUMN_LAST_MODIFIED
            ),
            null, null, null
        )
        cursor?.let {
            while (it.moveToNext()) {
                val name =
                    it.getString(it.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME))
                val type =
                    it.getString(it.getColumnIndex(DocumentsContract.Document.COLUMN_MIME_TYPE))
                val id =
                    it.getString(it.getColumnIndex(DocumentsContract.Document.COLUMN_DOCUMENT_ID))
                val size = it.getLong(it.getColumnIndex(DocumentsContract.Document.COLUMN_SIZE))
                val date =
                    it.getLong(it.getColumnIndex(DocumentsContract.Document.COLUMN_LAST_MODIFIED))
                if (type == DocumentsContract.Document.MIME_TYPE_DIR) {
                    documentList.addAll(scanFile(contentResolver, DocumentsContract.buildChildDocumentsUriUsingTree(uri, id)))
                } else {
                    documentList.add(DocumentData(id, size, name, uri, date, type))
                }
            }
        }

        cursor?.close()
        return@withContext documentList
    }

    @SuppressLint("Range")
    suspend fun scanFileForSize(contentResolver: ContentResolver, uri: Uri): Long = withContext(Dispatchers.IO){
        var totalSize = 0L
        val cursor = contentResolver.query(
            uri,
            arrayOf(
                DocumentsContract.Document.COLUMN_DISPLAY_NAME,
                DocumentsContract.Document.COLUMN_MIME_TYPE,
                DocumentsContract.Document.COLUMN_DOCUMENT_ID,
                DocumentsContract.Document.COLUMN_SIZE
            ),
            null, null, null
        )
        cursor?.let {
            while (it.moveToNext()) {
                val name =
                    it.getString(it.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME))
                val type =
                    it.getString(it.getColumnIndex(DocumentsContract.Document.COLUMN_MIME_TYPE))
                val id =
                    it.getString(it.getColumnIndex(DocumentsContract.Document.COLUMN_DOCUMENT_ID))
                val size = it.getLong(it.getColumnIndex(DocumentsContract.Document.COLUMN_SIZE))
                if (type == DocumentsContract.Document.MIME_TYPE_DIR) {
                    totalSize += scanFileForSize(
                        contentResolver,
                        DocumentsContract.buildChildDocumentsUriUsingTree(uri, id)
                    )
                } else {
                    totalSize += size
                }
            }
        }

        cursor?.close()
        return@withContext totalSize
    }

    /**
     * 删除文件
     */
    suspend fun deleteCurDocument(context: Context, uri: Uri): Boolean = withContext(Dispatchers.IO) {
        try {
            return@withContext DocumentsContract.deleteDocument(context.contentResolver, uri)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext false
    }

    /**
     * 申请所有文件管理权限
     */
    fun requestForManageAllFilePermission(context: Activity, code: Int) {
        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
        intent.data = Uri.parse("package:${context.packageName}")
        context.startActivityForResult(intent, code)
    }

    /**
     * 判断是否获取MANAGE_EXTERNAL_STORAGE权限
     */
    @RequiresApi(Build.VERSION_CODES.R)
    fun isHaveAllManagePermission() : Boolean {
        return Environment.isExternalStorageManager()
    }

    /**
     * 直接获取 data 权限
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun startForDataPermission(activity: Activity, code: Int) {
        Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or
                    Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION or
                    Intent.FLAG_GRANT_PREFIX_URI_PERMISSION
            putExtra(
                DocumentsContract.EXTRA_INITIAL_URI,
                DocumentFile.fromTreeUri(activity, androidDataTreeUri)?.uri
            )
        }.also {
            activity.startActivityForResult(it, code)
        }
    }


    /**
     * 判断是否已经获取了 data 权限
     */
    fun isDataGrant(context: Context): Boolean {
        for (persistedUriPermission in context.contentResolver.persistedUriPermissions) {
            if ((persistedUriPermission.uri == androidDataTreeUri) &&
                persistedUriPermission.isWritePermission &&
                persistedUriPermission.isReadPermission
            ) {
                return true
            }
        }
        return false
    }
}