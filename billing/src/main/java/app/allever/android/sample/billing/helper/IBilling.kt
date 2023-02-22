package app.allever.android.sample.billing.helper

import android.app.Activity
import com.android.billingclient.api.ProductDetails

interface IBilling {
    fun init()
    fun connect()
    fun disConnect()
    fun getProductDetails(productId: String, block: () -> Unit?)
    suspend fun getProductDetailsSuspend(productId: String): ProductDetails?
    suspend fun checkSubscribe(productId: String): Boolean
    suspend fun subscribe(activity: Activity, productDetails: ProductDetails)
    fun reconnectIfNeed()
}