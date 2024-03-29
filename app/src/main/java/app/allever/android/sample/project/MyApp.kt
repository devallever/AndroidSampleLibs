package app.allever.android.sample.project

import app.allever.android.lib.common.function.network.reponse.BaseResponse
import app.allever.android.lib.core.app.App
import app.allever.android.lib.core.function.datastore.DataStore
import app.allever.android.lib.core.function.datastore.DefaultStore
import app.allever.android.lib.core.function.imageloader.ImageLoader
import app.allever.android.lib.core.function.mediapicker.MediaPickerHelper
import app.allever.android.lib.core.function.network.HttpConfig
import app.allever.android.lib.core.function.permission.DefaultPermissionEngine
import app.allever.android.lib.core.function.permission.PermissionHelper
import app.allever.android.lib.imageloader.glide.GlideLoader
import app.allever.android.lib.network.ApiService
import app.allever.android.lib.widget.Widget
import app.allever.android.lib.widget.mediapicker.MediaPicker
import app.allever.android.sample.billing.helper.BillingHelper
import app.allever.android.sample.billing.helper.BillingV5
//import app.allever.android.sample.billing.helper.BillingV4
import app.allever.android.sample.function.im.function.MyEmojiProvider
import app.allever.android.sample.function.im.function.db.IMDB
import com.vanniktech.emoji.EmojiManager
import skin.support.SkinCompatManager
import skin.support.app.SkinAppCompatViewInflater
import skin.support.app.SkinCardViewInflater
import skin.support.constraint.app.SkinConstraintViewInflater
import skin.support.design.app.SkinMaterialViewInflater

class MyApp : App() {
    override fun init() {
        ImageLoader.init(this, GlideLoader, ImageLoader.Builder.create())
        PermissionHelper.init(DefaultPermissionEngine)
        Widget.init(this)
        HttpConfig.baseUrl("https://www.wanandroid.com/")
            .baseResponseClass(BaseResponse::class.java)
            .header("Sample", "Hello")
            .init(ApiService)
        initSkin()

        EmojiManager.install(MyEmojiProvider())

        MediaPickerHelper.init(MediaPicker)

        IMDB.init(this)

        DataStore.init(DefaultStore())

        BillingHelper.init(BillingV5())

    }

    private fun initSkin() {
        SkinCompatManager.withoutActivity(this)
            .addInflater(SkinAppCompatViewInflater()) // 基础控件换肤初始化
            .addInflater(SkinMaterialViewInflater()) // material design 控件换肤初始化[可选]
            .addInflater(SkinConstraintViewInflater()) // ConstraintLayout 控件换肤初始化[可选]
            .addInflater(SkinCardViewInflater()) // CardView v7 控件换肤初始化[可选]
            .setSkinStatusBarColorEnable(false) // 关闭状态栏换肤，默认打开[可选]
            .setSkinWindowBackgroundEnable(false) // 关闭windowBackground换肤，默认打开[可选]
            .loadSkin()
    }
}