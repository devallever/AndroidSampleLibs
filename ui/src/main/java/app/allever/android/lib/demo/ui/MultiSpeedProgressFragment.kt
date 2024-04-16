package app.allever.android.lib.demo.ui

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.demo.databinding.FragmentMultiSpeedProgressBinding
import app.allever.android.lib.mvvm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MultiSpeedProgressFragment: BaseFragment<FragmentMultiSpeedProgressBinding, BaseViewModel>() {

    private val DURATION = 20 * 1000L

    private var firstPercent = 0.7
    private var firstDuration = 0.2 * DURATION
    private var firstStep = (firstDuration / (firstPercent * 100)).toInt().toLong()

    private var secondPercent = 0.2
    private var secondDuration = 0.3 * DURATION
    private var secondStep = (secondDuration / (secondPercent * 100)).toInt().toLong()

    private var finalPercent = 0.1
    private var finalDuration = 0.5 * DURATION
    private var finalStep = (finalDuration / (finalPercent * 100)).toInt().toLong()

    private var isLoading = false

    private var finishFlag = false
    override fun inflate() = FragmentMultiSpeedProgressBinding.inflate(layoutInflater)

    override fun init() {
        mBinding.apply {
            root.setOnClickListener {
                finishFlag = true
            }
            var step = firstStep
            finishFlag = false
            lifecycleScope.launch (Dispatchers.Default){
                isLoading = true
                var progress = 0
                var usedTime = 0F
                while (isActive && progress < 100) {
                    delay(step)
                    step = if (finishFlag) {
                        10
                    }else if (progress > 100 - finalPercent * 100) {
                        finalStep
                    } else if (progress > firstPercent * 100) {
                        secondStep
                    } else {
                        firstStep
                    }
                    usedTime += step
                    progress += 1
                    log("usedTime = ${usedTime / 1000f}")

                    launch(Dispatchers.Main) {
                        progressBar.progress = progress
                        tvLoading.text = "${progress}%"
                    }
                    if (progress >= 100) {
                        requireActivity().finish()
                    }
                }
                isLoading = false
            }
        }
    }
}