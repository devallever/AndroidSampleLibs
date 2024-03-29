package app.allever.android.lib.demo.ui.sticktop

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.demo.databinding.ActivityBaseTwoViewStickyTopBinding
import app.allever.android.lib.mvvm.base.BaseViewModel

class BaseTwoViewStickyTopActivity :
    BaseActivity<ActivityBaseTwoViewStickyTopBinding, BaseTwoViewStickyTopViewModel>() {

    override fun inflateChildBinding() = ActivityBaseTwoViewStickyTopBinding.inflate(layoutInflater)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun init() {
        initTopBar("基础吸顶")
        var firstViewHeight = 0
        binding.firstView.post {
            firstViewHeight = binding.firstView.height
        }
        binding.scrollView.setOnScrollChangeListener(object : View.OnScrollChangeListener {
            override fun onScrollChange(
                v: View?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (firstViewHeight == 0) {
                    return
                }

//                log("scrollY = $scrollY, firstViewHeight = $firstViewHeight")
                if (scrollY >= firstViewHeight) {
                    //重点 通过距离变化隐藏内外固定栏实现
                    binding.tvStickyTopViewFirst.visibility = View.VISIBLE
                    binding.tvStickyTopView.visibility = View.INVISIBLE
                } else {
                    binding.tvStickyTopViewFirst.visibility = View.INVISIBLE
                    binding.tvStickyTopView.visibility = View.VISIBLE
                }
            }

        })
    }
}

class BaseTwoViewStickyTopViewModel : BaseViewModel() {
    override fun init() {

    }
}