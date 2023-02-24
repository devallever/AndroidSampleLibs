package app.allever.android.sample.billing.helper

import android.app.Activity

/**
 * 包名要和gp上传的一致
 * 要用非中国地区账号
 * 要梯子
 */
object BillingHelper : IBilling {
    private var mBinning: IBilling? = null

    fun init(billing: BaseBilling) {
        mBinning = billing
    }

    override fun connect() {
        mBinning?.connect()
    }

    override fun disConnect() {
        mBinning?.disConnect()
    }

    override fun getProductDetails(
        productIdList: MutableList<String>,
        finish: ((success: Boolean, code: Int, message: String) -> Unit)?
    ) {
        mBinning?.getProductDetails(productIdList, finish)
    }

    override fun subScribe(
        activity: Activity,
        productId: String,
        finish: (success: Boolean, code: Int, message: String) -> Unit
    ) {
        mBinning?.subScribe(activity, productId, finish)
    }

    override fun checkScribeStatus(finish: (success: Boolean, code: Int, message: String) -> Unit) {
        mBinning?.checkScribeStatus(finish)
    }
}