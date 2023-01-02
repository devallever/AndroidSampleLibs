package app.allever.android.learning.audiovideo

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import app.allever.android.lib.core.function.media.MediaBean

open class BasePlayerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    constructor(
        basePlayerHandler: BasePlayerHandler,
        context: Context,
        attrs: AttributeSet? = null
    ) : this(context, attrs)

    protected lateinit var mMediaBean: MediaBean


}