package app.allever.android.sample.billing.helper

import android.app.Activity

interface IBilling {
    fun connect()
    fun disConnect()
    fun getProductDetails(
        productIdList: MutableList<String>,
        finish: ((success: Boolean, code: Int, message: String) -> Unit)?
    )

    fun subScribe(
        activity: Activity,
        productId: String,
        finish: (success: Boolean, code: Int, message: String) -> Unit
    )

    fun checkScribeStatus(finish: (success: Boolean, code: Int, message: String) -> Unit)
}