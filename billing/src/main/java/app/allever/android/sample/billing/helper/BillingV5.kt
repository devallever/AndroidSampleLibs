package app.allever.android.sample.billing.helper

import android.app.Activity
import android.text.TextUtils
import app.allever.android.lib.core.app.App
import app.allever.android.lib.core.ext.toJson
import com.android.billingclient.api.*

class BillingV5 : BaseBilling() {

    private var mProduceDetailsMap = mutableMapOf<String, ProductDetails>()
    private var mPurchaseCallback: ((success: Boolean, code: Int, message: String) -> Unit)? = null
    private var mPurchaseList = mutableListOf<Purchase>()

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->

            App.mainHandler.post {
                val code = billingResult.responseCode
                val message = billingResult.debugMessage

                if (code == BillingClient.BillingResponseCode.OK && !purchases.isNullOrEmpty()) {
                    //订阅成功
                    mPurchaseCallback?.invoke(true, code, message)
                    log("订阅成功")
                } else if (code == BillingClient.BillingResponseCode.USER_CANCELED) {
                    //取消订阅
                    mPurchaseCallback?.invoke(false, code, "Cancel")
                    log("取消订阅")
                } else {
                    //失败
                    mPurchaseCallback?.invoke(false, code, message.ifEmpty { "Fail" })
                    logE("订阅失败: $code -> $message")
                }

                log("onPurchasesUpdated: ")
                purchases?.map {
                    log("Purchases：${it.toJson()}")
                }
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

    override fun getProductDetails(
        productIdList: MutableList<String>,
        finish: ((success: Boolean, code: Int, message: String) -> Unit)?
    ) {
        val productList = ArrayList<QueryProductDetailsParams.Product>()

        productIdList.map {
            val product = QueryProductDetailsParams.Product.newBuilder()
                .setProductId(it)
                .setProductType(BillingClient.ProductType.SUBS)
                .build()
            productList.add(product)
        }

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


            productDetailsList.map {
                mProduceDetailsMap[it.productId] = it
                log("商品详情：${it.toJson()}")
            }

            val code = billingResult.responseCode
            val message = billingResult.debugMessage
            App.mainHandler.post {
                if (code == BillingClient.BillingResponseCode.OK) {
                    log("查询商品成功")
                    if (mProduceDetailsMap.isEmpty()) {
                        finish?.invoke(false, code, "找不到商品，可能不支持")
                    } else {
                        finish?.invoke(true, code, message)
                    }
                } else {
                    logE("查询商品失败：$code -> $message")
                    finish?.invoke(false, code, message)
                }
            }
        }
    }

    override fun subScribe(
        activity: Activity,
        productId: String,
        finish: (success: Boolean, code: Int, message: String) -> Unit
    ) {
//        mPurchaseCallback = finish
//
//        val productDetail = mProduceDetailsMap[productId]
//        if (productDetail == null) {
//            finish.invoke(false, -1, DEFAULT_ERROR_MSG)
//            return
//        }
//
//        val selectedOfferToken = productDetail.subscriptionOfferDetails?.get(0)?.offerToken ?: ""
//        if (TextUtils.isEmpty(selectedOfferToken)) {
//            finish.invoke(false, -1, DEFAULT_ERROR_MSG)
//            return
//        }
//
//
//        mProduceDetailsMap[productId]?.let {
//            buy(it, mPurchaseList, activity, "weekly", finish)
//        }
//        return

        mPurchaseCallback = finish
        val productDetail = mProduceDetailsMap[productId]
        if (productDetail == null) {
            App.mainHandler.post {
                finish.invoke(false, -1, DEFAULT_ERROR_MSG)
            }
            return
        }

        val selectedOfferToken = productDetail.subscriptionOfferDetails?.get(0)?.offerToken ?: ""
        if (TextUtils.isEmpty(selectedOfferToken)) {
            App.mainHandler.post {
                finish.invoke(false, -1, "Fail")
            }
            return
        }

        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                .setProductDetails(productDetail)
                // to get an offer token, call ProductDetails.subscriptionOfferDetails()
                // for a list of offers that are available to the user
                .setOfferToken(selectedOfferToken)
                .build()
        )

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        billingClient?.launchBillingFlow(activity, billingFlowParams)
    }

    override fun checkScribeStatus(finish: (success: Boolean, code: Int, message: String) -> Unit) {
        val params =
            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build()

        billingClient?.queryPurchasesAsync(params) { billingResult, purchaseList ->
            val code = billingResult.responseCode
            val message = billingResult.debugMessage
            App.mainHandler.post {
                if (code == BillingClient.BillingResponseCode.OK) {
                    log("查询订阅状态成功")
                    mPurchaseList.clear()
                    mPurchaseList.addAll(purchaseList)
                    if (purchaseList.isEmpty()) {
                        finish(false, code, "没有订阅")
                        log("没有订阅")
                    } else {
                        finish(true, code, "已订阅")
                        log("已订阅")
                    }
                } else {
                    logE("查询订阅状态失败: $code ->$message")
                    finish.invoke(false, code, message)
                }
            }
        }
    }

    override fun disConnect() {
        super.disConnect()
        billingClient?.endConnection()
        billingClient = null
    }

    /**
     * Use the Google Play Billing Library to make a purchase.
     *
     * @param productDetails ProductDetails object returned by the library.
     * @param currentPurchases List of current [Purchase] objects needed for upgrades or downgrades.
     * @param billingClient Instance of [BillingClientWrapper].
     * @param activity [Activity] instance.
     * @param tag String representing tags associated with offers and base plans.
     */
    private fun buy(
        productDetails: ProductDetails,
        currentPurchases: List<Purchase>?,
        activity: Activity,
        tag: String,
        finish: (success: Boolean, code: Int, message: String) -> Unit
    ) {
        val offers =
            productDetails.subscriptionOfferDetails?.let {
                retrieveEligibleOffers(
                    offerDetails = it,
                    tag = tag.lowercase()
                )
            }
        log("buy: offers = ${offers?.toJson()}")
        val offerToken = offers?.let { leastPricedOfferToken(it) }
        log("bug: offerToken = $offerToken")
        val oldPurchaseToken: String

        // Get current purchase. In this app, a user can only have one current purchase at
        // any given time.
        if (!currentPurchases.isNullOrEmpty() &&
            currentPurchases.size == 1
        ) {
            log("buy: This either an upgrade, downgrade, or conversion purchase.")
            // This either an upgrade, downgrade, or conversion purchase.
            val currentPurchase = currentPurchases.first()

            // Get the token from current purchase.
            oldPurchaseToken = currentPurchase.purchaseToken
            log("buy: oldPurchaseToken = $oldPurchaseToken")

            val billingParams = offerToken?.let {
                upDowngradeBillingFlowParamsBuilder(
                    productDetails = productDetails,
                    offerToken = it,
                    oldToken = oldPurchaseToken
                )
            }

            log("buy: billingParams = ${billingParams?.toJson()}")

            if (billingParams != null) {
                billingClient?.launchBillingFlow(
                    activity,
                    billingParams
                )
            } else {
                finish.invoke(false, -1, DEFAULT_ERROR_MSG)
            }
        } else if (currentPurchases == null || currentPurchases.isEmpty()) {
            // This is a normal purchase.
            log("buy: This is a normal purchase")
            val billingParams = offerToken?.let {
                billingFlowParamsBuilder(
                    productDetails = productDetails,
                    offerToken = it
                )
            }
            log("buy: billingParams = ${billingParams?.toJson()}")
            if (billingParams != null) {
                billingClient?.launchBillingFlow(
                    activity,
                    billingParams.build()
                )
            } else {
                finish.invoke(false, -1, DEFAULT_ERROR_MSG)
            }
        } else if (!currentPurchases.isNullOrEmpty() &&
            currentPurchases.size > 1
        ) {
            // The developer has allowed users  to have more than 1 purchase, so they need to
            /// implement a logic to find which one to use.
            log("User has more than 1 current purchase.")
            finish.invoke(false, -1, "User has more than 1 current purchase.")
        }
    }

    /**
     * Retrieves all eligible base plans and offers using tags from ProductDetails.
     *
     * @param offerDetails offerDetails from a ProductDetails returned by the library.
     * @param tag string representing tags associated with offers and base plans.
     *
     * @return the eligible offers and base plans in a list.
     *
     */
    private fun retrieveEligibleOffers(
        offerDetails: MutableList<ProductDetails.SubscriptionOfferDetails>,
        tag: String
    ): List<ProductDetails.SubscriptionOfferDetails> {
        val eligibleOffers = emptyList<ProductDetails.SubscriptionOfferDetails>().toMutableList()
        offerDetails.forEach { offerDetail ->
            if (offerDetail.offerTags.contains(tag)) {
                eligibleOffers.add(offerDetail)
            }
        }

        return eligibleOffers
    }

    /**
     * Calculates the lowest priced offer amongst all eligible offers.
     * In this implementation the lowest price of all offers' pricing phases is returned.
     * It's possible the logic can be implemented differently.
     * For example, the lowest average price in terms of month could be returned instead.
     *
     * @param offerDetails List of of eligible offers and base plans.
     *
     * @return the offer id token of the lowest priced offer.
     */
    private fun leastPricedOfferToken(
        offerDetails: List<ProductDetails.SubscriptionOfferDetails>
    ): String {
        var offerToken = String()
        var leastPricedOffer: ProductDetails.SubscriptionOfferDetails
        var lowestPrice = Int.MAX_VALUE

        if (!offerDetails.isNullOrEmpty()) {
            for (offer in offerDetails) {
                for (price in offer.pricingPhases.pricingPhaseList) {
                    if (price.priceAmountMicros < lowestPrice) {
                        lowestPrice = price.priceAmountMicros.toInt()
                        leastPricedOffer = offer
                        offerToken = leastPricedOffer.offerToken
                    }
                }
            }
        }
        return offerToken
    }

    /**
     * BillingFlowParams Builder for upgrades and downgrades.
     *
     * @param productDetails ProductDetails object returned by the library.
     * @param offerToken the least priced offer's offer id token returned by
     * [leastPricedOfferToken].
     * @param oldToken the purchase token of the subscription purchase being upgraded or downgraded.
     *
     * @return [BillingFlowParams] builder.
     */
    private fun upDowngradeBillingFlowParamsBuilder(
        productDetails: ProductDetails,
        offerToken: String,
        oldToken: String
    ): BillingFlowParams {
        return BillingFlowParams.newBuilder().setProductDetailsParamsList(
            listOf(
                BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(productDetails)
                    .setOfferToken(offerToken)
                    .build()
            )
        ).setSubscriptionUpdateParams(
            BillingFlowParams.SubscriptionUpdateParams.newBuilder()
                .setOldPurchaseToken(oldToken)
                .setReplaceProrationMode(
                    BillingFlowParams.ProrationMode.IMMEDIATE_AND_CHARGE_FULL_PRICE
                )
                .build()
        ).build()
    }

    /**
     * BillingFlowParams Builder for normal purchases.
     *
     * @param productDetails ProductDetails object returned by the library.
     * @param offerToken the least priced offer's offer id token returned by
     * [leastPricedOfferToken].
     *
     * @return [BillingFlowParams] builder.
     */
    private fun billingFlowParamsBuilder(
        productDetails: ProductDetails,
        offerToken: String
    ): BillingFlowParams.Builder {
        return BillingFlowParams.newBuilder().setProductDetailsParamsList(
            listOf(
                BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(productDetails)
                    .setOfferToken(offerToken)
                    .build()
            )
        )
    }
}