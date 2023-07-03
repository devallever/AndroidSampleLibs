package app.allever.android.sample.toolbox.dailytools

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.toolbox.databinding.ActivityRulerBinding
import app.allever.android.sample.toolbox.widget.RulerView

/**
 * 刻度尺
 */
class RulerActivity : BaseActivity<ActivityRulerBinding, BaseViewModel>() {
    override fun init() {
        binding.rulerView.unitType = RulerView.Unit.CM
    }

    override fun inflateChildBinding(): ActivityRulerBinding {
        return ActivityRulerBinding.inflate(layoutInflater)
    }

    override fun showTopBar() = false

    override fun isFullScreen() = true
}