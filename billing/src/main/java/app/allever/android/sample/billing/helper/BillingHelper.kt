package app.allever.android.sample.billing.helper

import android.app.Activity
import com.android.billingclient.api.ProductDetails

object BillingHelper : IBilling {
    lateinit var mBillingProxy: IBilling
    fun initBilling(billingProxy: IBilling) {
        mBillingProxy = billingProxy
    }

    override fun init() {
        mBillingProxy.init()
    }

    override fun connect() {
        mBillingProxy.connect()
    }

    override fun disConnect() {
        mBillingProxy.disConnect()
    }

    override fun getProductDetails(productId: String, block: () -> Unit?) {
        mBillingProxy.getProductDetails(productId, block)
    }

    override suspend fun getProductDetailsSuspend(productId: String): ProductDetails? {
        return mBillingProxy.getProductDetailsSuspend(productId)
    }

    override suspend fun checkSubscribe(productId: String): Boolean {
        return mBillingProxy.checkSubscribe(productId)
    }

    override suspend fun subscribe(activity: Activity, productDetails: ProductDetails) {
        mBillingProxy.subscribe(activity, productDetails)
    }

    override fun reconnectIfNeed() {
        mBillingProxy.reconnectIfNeed()
    }
}