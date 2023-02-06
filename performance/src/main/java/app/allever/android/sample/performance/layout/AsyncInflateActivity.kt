package app.allever.android.sample.performance.layout

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import app.allever.android.lib.core.base.AbstractActivity
import app.allever.android.sample.performance.R

class AsyncInflateActivity : AbstractActivity() {
    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AsyncLayoutInflater(this).inflate(
            R.layout.activity_async_inflate,
            null
        ) { view, resid, parent ->
            setContentView(view)
        }
    }
}