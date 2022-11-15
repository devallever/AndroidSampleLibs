package app.allever.android.sample.project

import app.allever.android.lib.common.function.network.reponse.BaseResponse
import app.allever.android.lib.core.app.App
import app.allever.android.lib.core.function.imageloader.ImageLoader
import app.allever.android.lib.core.function.network.HttpConfig
import app.allever.android.lib.core.function.permission.DefaultPermissionEngine
import app.allever.android.lib.core.function.permission.PermissionHelper
import app.allever.android.lib.imageloader.glide.GlideLoader
import app.allever.android.lib.network.ApiService
import app.allever.android.lib.widget.Widget

class MyApp : App() {
    override fun init() {
        ImageLoader.init(this, GlideLoader, ImageLoader.Builder.create())
        PermissionHelper.init(DefaultPermissionEngine)
        Widget.init(this)
        HttpConfig.baseUrl("https://www.wanandroid.com/")
            .baseResponseClass(BaseResponse::class.java)
            .header("Sample", "Hello")
            .init(ApiService)
    }
}