package app.allever.android.sample.videoeditor.gesture.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.widget.FrameLayout
import app.allever.android.lib.core.ext.log
import com.alexvasilkov.gestures.GestureController
import com.alexvasilkov.gestures.Settings
import com.alexvasilkov.gestures.views.interfaces.GestureView

class VideoEditFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs), GestureView,
    GestureController.OnGestureListener {
    private val gestureController by lazy {
        GestureController(this)
    }

    init {
        //official
        gestureController.settings
            .setMaxZoom(2f)
            .setDoubleTapZoom(-1f) // Falls back to max zoom level
            .setPanEnabled(true)
            .setZoomEnabled(true)
            .setDoubleTapEnabled(true)
            .setRotationEnabled(true)
            .setRestrictRotation(false)
            .setOverscrollDistance(0f, 0f)
            .setOverzoomFactor(2f)
            .setFillViewport(false)
            .setFitMethod(Settings.Fit.INSIDE)
            .setGravity(Gravity.CENTER)

        // Gesture controller settings
//        gestureController.settings
//            .setRotationEnabled(true)
//            .setDoubleTapEnabled(true)
//            .setFitMethod(Settings.Fit.INSIDE)
//            .setBoundsType(Settings.Bounds.INSIDE)
//            .setMinZoom(0.5f)
//            .setMaxZoom(0f)
//            .setImage(1, 1)
//            .disableBounds()

        gestureController.setOnGesturesListener(this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureController.onTouch(this, event)
    }

    override fun onDown(event: MotionEvent) {
        log("onDown")
    }

    override fun onUpOrCancel(event: MotionEvent) {
        log("onUpOrCancel")
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        log("onSingleTapUp")
        return false
    }

    override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
        log("onSingleTapConfirmed")
        return false
    }

    override fun onLongPress(event: MotionEvent) {
        log("onLongPress")
    }

    override fun onDoubleTap(event: MotionEvent): Boolean {
        log("onDoubleTap")
        return false
    }

    override fun getController(): GestureController {
        return gestureController
    }

    private fun log(msg: String) {
        log(this::class.java.simpleName, msg)
    }
}