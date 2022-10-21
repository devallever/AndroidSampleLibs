package app.allever.android.lib.demo

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.demo.databinding.ActivityUserCenterBinding
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.appbar.AppBarLayout
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener
import com.scwang.smart.refresh.layout.util.SmartUtil
import kotlin.math.absoluteValue

class UserCenterActivity: BaseActivity<ActivityUserCenterBinding, UserCenterViewModel>() {

    val maxHeight = SmartUtil.dp2px(506F)
    var isRefreshIng = false


    override fun showTopBar() = false

    override fun init() {

        binding.appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                val totalRange: Int = appBarLayout?.totalScrollRange ?: 0
                val alpha: Float = verticalOffset.absoluteValue.toFloat() / totalRange
                if (isRefreshIng) return
                //当刷新时会意外回调一次verticalOffset = 0
                binding.idUserBigAvator.translationY = verticalOffset * 0.8f
                if (alpha > 0.2) {
                    binding.flTitle.alpha = if (alpha > 0.4) 1f else alpha
//                    io.rong.imkit.utils.StatusBarUtil.setStatusBarDarkTheme(activity, true)
                } else {
                    binding.flTitle.alpha = alpha
//                    io.rong.imkit.utils.StatusBarUtil.setStatusBarDarkTheme(
//                        this,
//                        false
//                    )
                }
            }
        })

        binding.smartRefreshLayout.setOnMultiListener(object : SimpleMultiListener() {
            override fun onHeaderMoving(
                header: RefreshHeader?,
                isDragging: Boolean,
                percent: Float,
                offset: Int,
                headerHeight: Int,
                maxDragHeight: Int,
            ) {
                isRefreshIng = true
                if (offset == 0) {
                    binding.mBanner.visibility = View.VISIBLE
                    isRefreshIng = false
                } else {
                    binding.idUserBigAvator.visibility = View.VISIBLE
                    binding.mBanner.visibility = View.GONE
                }
                val fl = offset * 0.8F
                binding.idUserBigAvator.translationY = offset.toFloat()
                val scale: Float = 1 + (fl / maxHeight)
                binding.idUserBigAvator.scaleX = scale
                binding.idUserBigAvator.scaleY = scale
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                postDelay({
                    binding.smartRefreshLayout.finishRefresh(true)
                }, 2000)
            }
        })


        val listData = mutableListOf<String>()
        for(i in 0..100) {
            listData.add("主播你好啵${i+1}")
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.rv_item_text, listData) {
            override fun convert(holder: BaseViewHolder, item: String) {}
        }

    }

    override fun getContentMvvmConfig() = MvvmConfig(R.layout.activity_user_center, BR.userCenterVM)
}

class UserCenterViewModel: BaseViewModel() {
    override fun init() {

    }
}