package app.allever.android.sample.learning.android.file

import android.net.Uri

class DocumentData(
    var id: String,
    var size: Long,
    var name: String,
    var uri: Uri,
    var date: Long,
    var type: String
)  {
}