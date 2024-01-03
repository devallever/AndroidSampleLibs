package app.allever.android.sample.cleaner.function

import android.graphics.drawable.Drawable

class ProcessInfo {
    var itemName: String? = null
    var itemIcon: Drawable? = null
    var itemSize: Long = 0
    var isChecked = false
    var isSystem = false
    var packageName: String? = null
}