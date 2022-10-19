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
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
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