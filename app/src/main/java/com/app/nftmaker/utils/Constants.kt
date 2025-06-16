package com.app.nftmaker.utils


class Constants {

    companion object {
        // Use the fake local server data or real remote server.
        @Volatile
        var USE_FAKE_SERVER = false

        const val BASIC_SKU = "basic_subscription"
        const val REMOVE_AD_SKU = "remove_ad_purchases"
        const val PREMIUM_SKU = "premium_subscription"
        const val PLAY_STORE_SUBSCRIPTION_URL
                = "https://play.google.com/store/account/subscriptions"
        const val PLAY_STORE_SUBSCRIPTION_DEEPLINK_URL
                = "https://play.google.com/store/account/subscriptions?sku=%s&package=%s"
    }

}