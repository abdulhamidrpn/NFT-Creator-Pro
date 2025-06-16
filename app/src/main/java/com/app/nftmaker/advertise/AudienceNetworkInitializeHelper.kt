package com.app.nftmaker.advertise

import android.content.Context
import android.util.Log
import com.facebook.ads.AudienceNetworkAds
import com.facebook.ads.AudienceNetworkAds.InitListener
import com.facebook.ads.AudienceNetworkAds.InitResult


/**
 * Sample class that shows how to call initialize() method of Audience Network SDK.
 */
class AudienceNetworkInitializeHelper : InitListener {
    override fun onInitialized(result: InitResult) {
        Log.d(AudienceNetworkAds.TAG, result.message)
    }

    companion object {
        /**
         * It's recommended to call this method from Application.onCreate().
         * Otherwise you can call it from all Activity.onCreate()
         * methods for Activities that contain ads.
         *
         * @param context Application or Activity.
         */
        fun initialize(context: Context?) {
            if (!AudienceNetworkAds.isInitialized(context)) {
//                if (DEBUG) {
//                    AdSettings.turnOnSDKDebugger(context)
//                    AdSettings.setTestMode(true)// for get test ad in your device
//                }
                AudienceNetworkAds
                    .buildInitSettings(context)
                    .withInitListener(AudienceNetworkInitializeHelper())
                    .initialize()
            }
        }
    }
}