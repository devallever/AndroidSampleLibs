package app.allever.android.sample.function.im.ui.adapter.provider

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import app.allever.android.lib.core.function.imageloader.load
import app.allever.android.sample.function.R
import app.allever.android.sample.function.im.message.BaseMessage
import app.allever.android.sample.function.im.message.VideoMessage
import com.chad.library.adapter.base.viewholder.BaseViewHolder

abstract class VideoMsgProvider : BaseMsgProvider() {
    override fun convert(helper: BaseViewHolder, item: BaseMessage) {
        if (item !is VideoMessage) return
        setUserInfo(helper, item)
        val resource = item.url.ifEmpty {
            item.path
        }

        helper.getView<View>(R.id.widthVideoContainer).isVisible = item.isWidthVideo()
        helper.getView<View>(R.id.heightVideoContainer).isVisible = !item.isWidthVideo()

        val ivId = if (item.isWidthVideo()) {
            R.id.ivWidthVideoCover
        } else {
            R.id.ivHeightVideoCover
        }

        helper.getView<ImageView>(ivId).load(resource)
    }
}