package app.allever.android.sample.thirtypart.ui

import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.thirtypart.databinding.FragmentGlideBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

class GlideFragment : BaseFragment<FragmentGlideBinding, BaseViewModel>() {

    override fun inflate() = FragmentGlideBinding.inflate(layoutInflater)

    override fun init() {

        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(this)
            .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2020-07-14%2F5f0d6def76ce8.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1671712929&t=a9d6d7ea6749ff3fd7b50612e47ec7bb")
            .transition(withCrossFade(factory))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(app.allever.android.lib.core.R.drawable.shape_cccccc)
            .into(mBinding.iv1)

//        Glide.with(this).load("").into(mBinding.iv1)
    }
}