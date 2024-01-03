package app.allever.android.sample.project

//import app.allever.android.sample.login.LoginMainFragment
import app.allever.android.learning.audiovideo.AudioVideoMainActivity
import app.allever.android.lib.common.CommonFragment
import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.demo.UIMainFragment
import app.allever.android.lib.widget.fragment.EmptyFragment
import app.allever.android.sample.billing.BillingMainActivity
import app.allever.android.sample.cleaner.CleanerMainActivity
import app.allever.android.sample.designpattern.DesignPatternMainActivity
import app.allever.android.sample.function.FunctionMainFragment
import app.allever.android.sample.function.interceptor.FirstInterceptor
import app.allever.android.sample.function.interceptor.RealChain
import app.allever.android.sample.function.interceptor.SecondInterceptor
import app.allever.android.sample.gaodemap.GaoDeMapMainListFragment
import app.allever.android.sample.jetpack.JetpackMainFragment
import app.allever.android.sample.jni.JniMainActivity
import app.allever.android.sample.kotlin.KotlinMainFragment
import app.allever.android.sample.learning.android.LearningAndroidMainActivity
import app.allever.android.sample.material.design.MaterialDesignMainListFragment
import app.allever.android.sample.performance.PerformanceMainFragment
import app.allever.android.sample.project.app.GuideActivity
import app.allever.android.sample.project.app.LogoFragment
import app.allever.android.sample.toolbox.ToolBoxMainFragment
import app.allever.android.sample.videoeditor.VideoEditorMainActivity

class MainListFragment : ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {

    override fun getAdapter() = TextClickAdapter()

    override fun getList() = mutableListOf(
        TextClickItem("设计Logo") {
            FragmentActivity.start<LogoFragment>(it.title)
        },
        TextClickItem("设计宣传图") {
            ActivityHelper.startActivity<GuideActivity> { }
        },
        TextClickItem("清理大师(Demo)") {
            ActivityHelper.startActivity<CleanerMainActivity> { }
        },
        TextClickItem("LearningAndroid") {
            ActivityHelper.startActivity<LearningAndroidMainActivity> { }
        },
        TextClickItem("UI交互效果") {
            FragmentActivity.start<UIMainFragment>(it.title)
        },
        TextClickItem("Jetpack") {
            FragmentActivity.start<JetpackMainFragment>(it.title)
        },
        TextClickItem("Kotlin") {
            FragmentActivity.start<KotlinMainFragment>(it.title)
        },
        TextClickItem("ThirtyPart") {
            FragmentActivity.start<EmptyFragment>(it.title)
        },
        TextClickItem("功能实现") {
            FragmentActivity.start<FunctionMainFragment>(it.title)
        },
        TextClickItem("音视频") {
            ActivityHelper.startActivity<AudioVideoMainActivity> { }
        },
        TextClickItem("JNI") {
            ActivityHelper.startActivity<JniMainActivity> { }
        },
        TextClickItem("性能优化") {
            FragmentActivity.start<PerformanceMainFragment>(it.title)
        },
        TextClickItem("页面样式") {
            FragmentActivity.start<CommonFragment>(it.title)
        },
        TextClickItem("第三方登录/分享") {
//            FragmentActivity.start<LoginMainFragment>(it.title)
        },
        TextClickItem("谷歌内购/订阅/支付") {
            ActivityHelper.startActivity<BillingMainActivity> { }
        },
        TextClickItem("设计模式") {
            ActivityHelper.startActivity<DesignPatternMainActivity> { }
        },
        TextClickItem("视频编辑") {
            ActivityHelper.startActivity<VideoEditorMainActivity> { }
        },
        TextClickItem("Material Design") {
            FragmentActivity.start<MaterialDesignMainListFragment>(it.title)
        },
        TextClickItem("H5 交互") {
            FragmentActivity.start<H5Fragment>(it.title)
        },
        TextClickItem("工具箱") {
            FragmentActivity.start<ToolBoxMainFragment>(it.title)
        },

        TextClickItem("高德地图") {
            FragmentActivity.start<GaoDeMapMainListFragment>(it.title)
        },
    )

    override fun init() {
        super.init()
        RealChain.Builder.create()
            .addInterceptor(FirstInterceptor())
            .addInterceptor(SecondInterceptor())
            .build()
            .process()
    }
}