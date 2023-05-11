package app.allever.android.sample.material.design

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.base.adapter.Pager2Adapter
import app.allever.android.lib.core.helper.DisplayHelper
import app.allever.android.lib.core.helper.ViewHelper
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.widget.ext.setupViewPager2
import app.allever.android.lib.widget.fragment.EmptyFragment
import app.allever.android.sample.material.design.databinding.ActivityMaterialDesignMainBinding

class MDMainActivity : BaseActivity<ActivityMaterialDesignMainBinding, BaseViewModel>() {
    override fun init() {
        ViewHelper.setMarginTop(binding.toolbar, DisplayHelper.getStatusBarHeight(this))

        val list = arrayListOf(
            "#Hot",
            "#Trending",
            "#Cat",
            "#Animal",
            "#Holiday",
            "#Dog",
            "#Girl",
            "#Boy",
            "#Sticker"
        )
        val listFragment = mutableListOf<Fragment>()
        list.map {
            listFragment.add(EmptyFragment(it))
        }
        binding.viewPager.adapter = Pager2Adapter(
            this, listFragment
        )
        binding.tabLayout.setupViewPager2(
            binding.viewPager,
            list
        ) {

        }

    }

    override fun inflateChildBinding() = ActivityMaterialDesignMainBinding.inflate(layoutInflater)

    override fun showTopBar(): Boolean {
        return false
    }
}