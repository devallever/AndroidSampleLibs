package app.allever.android.lib.demo.ui

import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.core.helper.DisplayHelper
import app.allever.android.lib.demo.BR
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.ActivityUiMainBinding
import app.allever.android.lib.demo.databinding.RvItemTextBinding
import app.allever.android.lib.demo.ui.widget.OverlapManager
import app.allever.android.lib.demo.util.TextAndPictureUtil
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import eightbitlab.com.blurview.RenderScriptBlur

class UIMainActivity : BaseActivity<ActivityUiMainBinding, UIMainViewModel>() {
    override fun init() {
        initTopBar("UI交互")

        binding.pressLikeView.setOnClickListener {
            binding.pressLikeView.show()
        }

        //毛玻璃效果
        binding.blurView.setupWith(binding.blurBg, RenderScriptBlur(this)) // or RenderEffectBlur
            .setBlurRadius(20f)
        binding.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        binding.blurView.clipToOutline = true

        binding.blurBg.setOnClickListener {
            ActivityHelper.startActivity<BlurActivity>()
        }


        //自动滚动的RecyclerView
        val listData = mutableListOf<String>()
        for(i in 0..10) {
            listData.add("主播你好啵${i+1}")
        }
        val autoScrollAdapter = AutoScrollAdapter()
        binding.autoScrollRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.autoScrollRecyclerView.adapter = autoScrollAdapter
        autoScrollAdapter.data = listData
        binding.autoScrollRecyclerView.setScrollOffset(DisplayHelper.dip2px(48))
        binding.autoScrollRecyclerView.start()

        //标签换行TextView
        val text = TextAndPictureUtil.getText(
            this,
            "面对同事的请求从来不拒绝，自己却总是在夜晚懊悔,为什么现在的年轻,人都不愿意社交了？",
            R.drawable.default_tag,
            DisplayHelper.dip2px(26),
            DisplayHelper.dip2px(15)
        )
        binding.tvTagAutoChangeLine.text = text

        //折叠RecyclerViewItem
        binding.recyclerView2.layoutManager = OverlapManager()
        binding.recyclerView2.adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.rv_image, listData) {
            override fun convert(holder: BaseViewHolder, item: String) {}
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        binding.autoScrollRecyclerView.stop()
    }

    override fun getContentMvvmConfig() = MvvmConfig(R.layout.activity_ui_main, BR.uiMainVM)
}

class UIMainViewModel : BaseViewModel() {
    override fun init() {

    }
}

class AutoScrollAdapter :
    BaseQuickAdapter<String, BaseDataBindingHolder<RvItemTextBinding>>(R.layout.rv_item_text) {
    override fun convert(holder: BaseDataBindingHolder<RvItemTextBinding>, item: String) {
        val binding = holder.dataBinding ?: return
        binding.tvText.text = item
    }

    override fun getItem(position: Int): String {
        val newPosition = position % data.size
        return data[newPosition]
    }

    override fun getItemViewType(position: Int): Int {
        val realPosition = position % data.size
        return super.getItemViewType(realPosition)
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }
}