package app.allever.android.sample.thirtypart.ui

import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.sample.thirtypart.BR
import app.allever.android.sample.thirtypart.R
import app.allever.android.sample.thirtypart.databinding.FragmentGlideBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

class GlideFragment : BaseMvvmFragment<FragmentGlideBinding, BaseViewModel>() {
    override fun getMvvmConfig() = MvvmConfig(R.layout.fragment_glide, BR.glideVM)

    override fun init() {

        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(this)
            .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic_source%2F64%2F4c%2F7b%2F644c7b6c1a42fac7d48b517084bfb02c.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1671769083&t=f8bd2fbadf0205c011cc930550599981")
            .transition(withCrossFade(factory))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(app.allever.android.lib.common.R.drawable.md_btn_shape)
            .into(mBinding.iv1)

//        Glide.with(this).load("").into(mBinding.iv1)
    }
}