package app.allever.android.lib.demo.ui

import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.core.helper.DisplayHelper
import app.allever.android.lib.demo.BR
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.FragmentUiBinding
import app.allever.android.lib.demo.ui.widget.OverlapManager
import app.allever.android.lib.demo.util.TextAndPictureUtil
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import eightbitlab.com.blurview.RenderScriptBlur

class UIFragment : BaseFragment<FragmentUiBinding, UIViewModel>() {
    override fun init() {
        mBinding.pressLikeView.setOnClickListener {
            mBinding.pressLikeView.show()
        }

        //毛玻璃效果
        mBinding.blurView.setupWith(mBinding.blurBg, RenderScriptBlur(context)) // or RenderEffectBlur
            .setBlurRadius(20f)
        mBinding.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        mBinding.blurView.clipToOutline = true

        mBinding.blurBg.setOnClickListener {
            ActivityHelper.startActivity<BlurActivity>()
        }


        //自动滚动的RecyclerView
        val listData = mutableListOf<String>()
        for(i in 0..10) {
            listData.add("主播你好啵${i+1}")
        }
        val autoScrollAdapter = AutoScrollAdapter()
        mBinding.autoScrollRecyclerView.layoutManager = LinearLayoutManager(context)
        mBinding.autoScrollRecyclerView.adapter = autoScrollAdapter
        autoScrollAdapter.data = listData
        mBinding.autoScrollRecyclerView.setScrollOffset(DisplayHelper.dip2px(48))
        mBinding.autoScrollRecyclerView.start()

        //标签换行TextView
        val text = TextAndPictureUtil.getText(
            context,
            "面对同事的请求从来不拒绝，自己却总是在夜晚懊悔,为什么现在的年轻,人都不愿意社交了？",
            R.drawable.default_tag,
            DisplayHelper.dip2px(26),
            DisplayHelper.dip2px(15)
        )
        mBinding.tvTagAutoChangeLine.text = text

        //折叠RecyclerViewItem
        mBinding.recyclerView2.layoutManager = OverlapManager()
        mBinding.recyclerView2.adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.rv_image, listData) {
            override fun convert(holder: BaseViewHolder, item: String) {}
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.autoScrollRecyclerView.stop()
    }

    override fun getMvvmConfig() = MvvmConfig(R.layout.fragment_ui, BR.uiMainVM)
}

class UIViewModel : BaseViewModel() {
    override fun init() {

    }
}

