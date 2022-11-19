package app.allever.android.sample.function.im.ui.adapter.provider

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import app.allever.android.lib.core.function.imageloader.load
import app.allever.android.sample.function.R
import app.allever.android.sample.function.im.message.BaseMessage
import app.allever.android.sample.function.im.message.ImageMessage
import com.chad.library.adapter.base.viewholder.BaseViewHolder

abstract class ImageMsgProvider : BaseMsgProvider() {
    override fun convert(helper: BaseViewHolder, item: BaseMessage) {
        if (item !is ImageMessage) return
        setUserInfo(helper, item)
        val resource = item.url.ifEmpty {
            item.path
        }

        helper.getView<View>(R.id.widthImgContainer).isVisible = item.isWidthImg()
        helper.getView<View>(R.id.heightImgContainer).isVisible = !item.isWidthImg()

        val ivId = if (item.isWidthImg()) {
            R.id.ivWidthImg
        } else {
            R.id.ivHeightImg
        }

        helper.getView<ImageView>(ivId).load(resource)
    }
}