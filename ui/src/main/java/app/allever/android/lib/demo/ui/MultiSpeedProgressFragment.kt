package app.allever.android.lib.demo.ui

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.demo.databinding.FragmentMultiSpeedProgressBinding
import app.allever.android.lib.mvvm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MultiSpeedProgressFragment: BaseFragment<FragmentMultiSpeedProgressBinding, BaseViewModel>() {

    private val DURATION = 10 * 1000L

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

    private val multiStepProgress by lazy {
        MultiStepProgress().apply {
            duration = 10 * 1000L
            listSpeed.add(Speed(duration, 0.4, 0.1))
            listSpeed.add(Speed(duration, 0.3, 0.2))
            listSpeed.add(Speed(duration, 0.2, 0.3))
            listSpeed.add(Speed(duration, 0.1, 0.4))
        }
    }
    override fun inflate() = FragmentMultiSpeedProgressBinding.inflate(layoutInflater)

    override fun init() {
//        mBinding.apply {
//            root.setOnClickListener {
//                finishFlag = true
//            }
//            var step = firstStep
//            finishFlag = false
//            lifecycleScope.launch (Dispatchers.Default){
//                isLoading = true
//                var progress = 0
//                var usedTime = 0F
//                while (isActive && progress < 100) {
//                    delay(step)
//                    step = if (finishFlag) {
//                        10
//                    }else if (progress <= firstPercent * 100) {
//                        firstStep
//                    } else if (progress <= (firstPercent + secondPercent) * 100) {
//                        secondStep
//                    } else if (progress <= (firstPercent + secondPercent + finalPercent) * 100){
//                        finalStep
//                    } else {
//                        firstStep
//                    }
//                    usedTime += step
//                    progress += 1
//                    log("usedTime = ${usedTime / 1000f}")
//
//                    launch(Dispatchers.Main) {
//                        progressBar.progress = progress
//                        tvLoading.text = "${progress}%"
//                    }
//                    if (progress >= 100) {
//                        requireActivity().finish()
//                    }
//                }
//                isLoading = false
//            }
//
//        }

        mBinding.root.setOnClickListener {
            multiStepProgress.finish()
        }
        lifecycleScope.launch {
            multiStepProgress.start {
                mBinding.progressBar.progress = it
                mBinding.tvLoading.text = "${it}%"
                if (it >= 100) {
                    requireActivity().finish()
                }
            }
        }
    }



    class MultiStepProgress {
        //total duration
        var duration = 0L
        //level speed
        val listSpeed: MutableList<Speed> = mutableListOf()
        private var finishFlag = false
        private var isLoading = false
        private fun getStep(progress: Int) : Long {
            if (finishFlag) {
                return 10L
            }
            var totalPercent = 0.0
            listSpeed.mapIndexed { index, speed ->
                totalPercent += speed.progressPercent
//                log("progress = $progress, totalPercent = ${totalPercent * 100}")
                if (progress <= totalPercent * 100) {
                    return speed.step
                }
            }
            return 10L
        }

        fun reset() {
            isLoading = false
            finishFlag = false
        }
        fun finish() {
            finishFlag = true
        }

        suspend fun start(block:(progress: Int) -> Unit) {
            if (isLoading) {
                return
            }
            finishFlag = false
            withContext(Dispatchers.IO) {
                var progress = 0
                var usedTime = 0F
                while (isActive && progress < 100) {
                    val step = getStep(progress)
                    delay(step)
                    usedTime += step
                    progress += 1
                    log("usedTime = ${usedTime / 1000f}")

                    launch(Dispatchers.Main) {
                        block.invoke(progress)
                    }
                }
            }
        }
    }

    data class Speed(
        val total : Long=  0L,
        var progressPercent : Double =  0.0,
        var durationPercent : Double = 0.0)  {

        var step = 10L
        init {
            step = (durationPercent * total / (progressPercent * 100)).toInt().toLong()
            log("step = $step")
        }
    }
}