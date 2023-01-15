package app.allever.android.learning.audiovideo

import android.database.Cursor
import android.provider.MediaStore
import android.text.TextUtils
import app.allever.android.lib.core.app.App

object VideoViewHelper {

    private fun checkVideoAvailable(path: String?): Boolean {
        if (TextUtils.isEmpty(path)) {
            return false
        }

        val cr = App.context.contentResolver
        var cursor: Cursor? = null
        try {
            cursor = cr.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Video.VideoColumns._ID,
                    MediaStore.Video.VideoColumns.DATA,
                    MediaStore.Video.VideoColumns.DURATION
                ),
                MediaStore.Video.VideoColumns.DATA + " = ? ", arrayOf(path),
                MediaStore.Video.VideoColumns.DATE_TAKEN + " DESC" + ", " + MediaStore.Video.VideoColumns._ID + " ASC"
            )

            if (cursor == null) {
                return false
            }

            if (cursor.moveToFirst()) {
                val durationIndex = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION)
                //有些文件后缀为视频格式，却不是视频文件，长度为0， 需要排除
                val time = cursor.getLong(durationIndex)
                if (time <= 0) {
                    return false
                }
            } else {
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            cursor?.close()
            return false
        }

        return true
    }

}