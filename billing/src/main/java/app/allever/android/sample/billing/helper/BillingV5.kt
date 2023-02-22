package app.allever.android.sample.billing.helper

import android.app.Activity
import android.util.Log
import app.allever.android.lib.core.app.App
import app.allever.android.lib.core.ext.toJson
import app.allever.android.lib.core.ext.toast
import com.android.billingclient.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BillingV5 : IBilling{

    private var mIsConnect = false
    private var mNeedReconnect = false

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            log("onPurchasesUpdated: ")
            purchases?.map {
                log("Purchases：${it.toJson()}")
            }
        }


    /***
     * BillingClient 是 Google Play 结算库与应用的其余部分之间进行通信的主接口。
     * BillingClient 为许多常见的结算操作提供了方便的方法，既有同步方法，又有异步方法。
     * 我们强烈建议您一次打开一个活跃的 BillingClient 连接，以避免对某一个事件进行多次 PurchasesUpdatedListener 回调。

     * 如需创建 BillingClient，请使用 newBuilder()。
     * 您可以将任何上下文传递给 newBuilder()，BillingClient 则使用前者来获取应用上下文。这意味着您不必担心内存泄漏。
     * 为了接收有关购买交易的更新，您还必须调用 setListener()，并传递对 PurchasesUpdatedListener 的引用。
     * 此监听器可接收应用中所有购买交易的更新。
     */
    private var billingClient: BillingClient? = null

    override fun init() {

    }


    /***
     * 创建 BillingClient 后，您需要与 Google Play 建立连接。

    如需连接到 Google Play，请调用 startConnection()。连接过程是异步进行的，因此您必须实现 BillingClientStateListener，
    以便在客户端的设置完成后且它准备好发出进一步的请求时接收回调。

    此外，您还必须实现重试逻辑，以处理与 Google Play 失去连接的问题。
    如需实现重试逻辑，请替换 onBillingServiceDisconnected() 回调方法，并确保 BillingClient 先调用 startConnection() 方法以重新连接到 Google Play，然后再发出进一步的请求。
     */
    override fun connect() {
        if (mIsConnect) {
            log("已连接，不需要重新连接")
            return
        }

        if (billingClient == null) {
            billingClient = BillingClient.newBuilder(App.context)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build()
        }

        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    mIsConnect = true
                    log("连接谷歌成功")
                } else {
                    logE("连接谷歌失败: ${billingResult.responseCode} -> ${billingResult.debugMessage}")
                    reconnectIfNeed()
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                mIsConnect = false
                log("已经断开连接谷歌")
                reconnectIfNeed()
            }
        })

    }

    /**
     * 查询商品详情
     *
     * 与 Google Play 建立连接后，您就可以查询可售的商品并将其展示给用户了。

    在将商品展示给用户之前，查询商品详情是非常重要的一步，因为查询会返回本地化的商品信息。对于订阅，请确保您的商品展示符合所有 Play 政策。

    如需查询应用内商品详情，请调用 queryProductDetailsAsync()。

    为了处理该异步操作的结果，您还必须指定实现 ProductDetailsResponseListener 接口的监听器。然后，您可以替换 onProductDetailsResponse()，该方法会在查询完成时通知监听器，如以下示例所示：
     */
    override fun getProductDetails(productId: String, block: () -> Unit?) {
        val product = QueryProductDetailsParams.Product.newBuilder()
            .setProductId(productId)
            .setProductType(BillingClient.ProductType.SUBS)
            .build()

        val productList = ArrayList<QueryProductDetailsParams.Product>()
        productList.add(product)

        val queryProductDetailsParams =
            QueryProductDetailsParams.newBuilder()
                .setProductList(
                    productList
                )
                .build()

        billingClient?.queryProductDetailsAsync(queryProductDetailsParams) { billingResult,
                                                                             productDetailsList ->
            // check billingResult
            // process returned productDetailsList

            val code = billingResult.responseCode
            if (code == BillingClient.BillingResponseCode.OK) {
                log("查询商品成功")
            } else {
                logE("查询商品失败：$code -> ${billingResult.debugMessage}")
            }

            var result: ProductDetails? = null
            productDetailsList.map {
                log("商品详情：${it.toJson()}")
                if (it.productId == productId) {
                    result = it
                }
            }

            if (result == null) {
                logE("找不到商品：$productId")
            } else {
                log("找到该商品: ${result?.toJson()}")
            }
        }
    }
    /**
     * 查询商品详情
     *
     * 与 Google Play 建立连接后，您就可以查询可售的商品并将其展示给用户了。

    在将商品展示给用户之前，查询商品详情是非常重要的一步，因为查询会返回本地化的商品信息。对于订阅，请确保您的商品展示符合所有 Play 政策。

    如需查询应用内商品详情，请调用 queryProductDetailsAsync()。

    为了处理该异步操作的结果，您还必须指定实现 ProductDetailsResponseListener 接口的监听器。然后，您可以替换 onProductDetailsResponse()，该方法会在查询完成时通知监听器，如以下示例所示：
     */
    override suspend fun getProductDetailsSuspend(productId: String) : ProductDetails? = withContext(Dispatchers.IO){
        val product = QueryProductDetailsParams.Product.newBuilder()
            .setProductId(productId)
            .setProductType(BillingClient.ProductType.INAPP)
            .build()

        val productList = ArrayList<QueryProductDetailsParams.Product>()
        productList.add(product)

        val params = QueryProductDetailsParams.newBuilder()
        params.setProductList(productList)

        // leverage queryProductDetails Kotlin extension function
        val productDetailsResult = withContext(Dispatchers.IO) {
            billingClient?.queryProductDetails(params.build())
        }

        val code = productDetailsResult?.billingResult?.responseCode
        if (code == BillingClient.BillingResponseCode.OK) {
            log("查询商品成功")
        } else {
            logE("查询商品失败：$code -> ${productDetailsResult?.billingResult?.debugMessage}")
        }

        var result: ProductDetails? = null
        productDetailsResult?.productDetailsList?.map {
            log("商品详情：${it.toJson()}")
            if (it.productId == productId) {
                result = it
            }
        }

        if (result == null) {
            logE("找不到商品：$productId")
        } else {
            log("找到该商品: ${result?.toJson()}")
        }

        result
    }

    /**
     * 查询商品订阅状态
     *
     * Only active subscriptions and non-consumed one-time purchases are returned.
     * This method uses a cache of Google Play Store app without initiating a network request.
     *
     */
    override suspend fun checkSubscribe(productId: String): Boolean {

        var isSubscribed = false
        val params =
            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build()

        //
//        billingClient.queryPurchasesAsync(params, object : PurchasesResponseListener{
//            override fun onQueryPurchasesResponse(billingResult: BillingResult, purchaseList: MutableList<Purchase>) {
//
//            }
//        })

        //协程方式
        val result = billingClient?.queryPurchasesAsync(params)

        val code = result?.billingResult?.responseCode
        if (code == BillingClient.BillingResponseCode.OK) {
            log("查询商品订阅状态成功")
        } else {
            logE("查询商品订阅状态失败：$code -> ${result?.billingResult?.debugMessage}")
        }

        result?.purchasesList?.map { purchase ->
            purchase.products.map {
                log("订单：${purchase.orderId} -> 商品id：$it")
                if (it == productId) {
                    isSubscribed = true
                }
            }
        }

        if (isSubscribed) {
            log("已经订阅: $productId")
        } else {
            log("未订阅: $productId")
        }

        return isSubscribed
    }

    /**
     * 如需从应用发起购买请求，请从应用的主线程调用 launchBillingFlow() 方法。
     * 此方法接受对 BillingFlowParams 对象的引用，该对象包含通过调用 queryProductDetailsAsync() 获取的相关 ProductDetails 对象。
     * 如需创建 BillingFlowParams 对象，请使用 BillingFlowParams.Builder 类。
     */
    override suspend fun subscribe(activity: Activity, productDetails: ProductDetails) {
        // An activity reference from which the billing flow will be launched.

        val selectedOfferToken =
            productDetails.subscriptionOfferDetails?.get(0)?.offerToken ?: return

        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                .setProductDetails(productDetails)
                // to get an offer token, call ProductDetails.subscriptionOfferDetails()
                // for a list of offers that are available to the user
                .setOfferToken(selectedOfferToken)
                .build()
        )

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

// Launch the billing flow
        val billingResult = billingClient?.launchBillingFlow(activity, billingFlowParams)
        billingResult
    }

    /***
     * to get an offer token, call ProductDetails.subscriptionOfferDetails()
     */
    suspend fun getOfferToken() {
    }

    override fun reconnectIfNeed() {
        if (mNeedReconnect) {
            App.mainHandler.postDelayed({
                connect()
            }, 1000)
        }
    }

    override fun disConnect() {
        log("已断开谷歌连接")
        billingClient?.endConnection()
        mNeedReconnect = false
        billingClient = null
        mIsConnect = false
    }

    private fun log(msg: String) {
        toast(msg)
        Log.d("ILogger", msg)
    }

    private fun logE(msg: String) {
        toast(msg)
        Log.e("ILogger", msg )
    }
}