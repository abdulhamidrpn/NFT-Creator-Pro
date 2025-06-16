package  nft.nftcreator.pixel.pixelart.inapp

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.billingclient.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nft.nftcreator.pixel.pixelart.data.SharedPref

var enableAd: MutableLiveData<Boolean> = MutableLiveData(false)

class InAppBilling(val context: Activity) : PurchasesUpdatedListener {
    private lateinit var billingClient: BillingClient
    private var skuDetailLst: List<SkuDetails>? = null

    //    private val skuList = listOf("test_product_one", "test_icanhear", "test_two", "test_two2")
    //TODO: UPLOAD THIS LIST IN PLAY CONSOLE
    private val skuList = listOf("remove_ad_purchases")

    lateinit var sharedPref: SharedPref

    private fun allowMultiplePurchases(purchases: MutableList<Purchase>?) {
        val purchase = purchases?.first()
        if (purchase != null) {

            val consumeParams =
                ConsumeParams.newBuilder()
                    .setPurchaseToken(purchase.getPurchaseToken())
                    .build()
            billingClient.consumeAsync(
                consumeParams
            ) { billingResult, s ->
                //do something..
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && s != null) {
                    println("AllowMultiplePurchases success, responseCode: ${billingResult.responseCode}")
                } else {
                    println("Can't allowMultiplePurchases, responseCode: ${billingResult.responseCode}")
                }
            }

        }
    }


    fun setupBillingClient(context: Context) {

        billingClient = BillingClient.newBuilder(context)
            .enablePendingPurchases()
            .setListener(this)
            .build()

        connectToGooglePlayBilling()

    }

    private fun connectToGooglePlayBilling() {

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    Log.d("BuyTAG", "$billingResult}")
//                    loadAllSKUs()
                    getProductDetails()
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.d("BuyTAG", "billing service disconnected}")
                connectToGooglePlayBilling()
            }
        })
    }

    fun getProductDetails() {
        var productIds = listOf<String>("remove_ad_purchases")
        val params = SkuDetailsParams
            .newBuilder()
            .setSkusList(productIds)
            .setType(BillingClient.SkuType.INAPP)
            .build()

        billingClient.querySkuDetailsAsync(params) { responseCode, skuDetailsList ->

            // Process the result.
            if (responseCode.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                Log.d("BuyTAG", "$skuDetailsList title ${skuDetailsList.get(0).title}")
                skuDetailLst = skuDetailsList
            }
        }
    }

    fun loadBilling(context: Activity) {
        if (skuDetailLst != null) {
            Log.d("BuyTAG", "$skuDetailLst title ${skuDetailLst?.get(0)?.title}")
            for (skuDetails in skuDetailLst!!) {
                if (skuDetails.sku == "remove_ad_purchases") {
                    val billingFlowParams = BillingFlowParams
                        .newBuilder()
                        .setSkuDetails(skuDetails)
                        .build()
                    billingClient.launchBillingFlow(context, billingFlowParams)
                }

            }
        } else {

        }
    }

    override fun onPurchasesUpdated(p0: BillingResult, purchases: MutableList<Purchase>?) {
        if (p0.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            Log.d("BuyTAG", "Purchases Updated")
            for (purchase in purchases) {
                if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged) {
//                        (purchase.purchaseToken)

                    sharedPref = SharedPref(context)
                    sharedPref.setAdRemoved(true)
                    enableAd.postValue(true)
                    allowMultiplePurchases(purchases)

                }

            }
        } else if (p0.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
            Log.d("BuyTAG", "Purchases USER_CANCELED")


        } else {
            // Handle any other error codes.
            Log.d("BuyTAG", "Purchases Other Errors")
        }
    }


}

