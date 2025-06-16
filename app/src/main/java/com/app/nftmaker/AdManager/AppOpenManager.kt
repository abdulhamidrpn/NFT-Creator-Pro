package com.apps.mydairy.ad_manager


import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.annotation.Nullable
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import nft.nftcreator.pixel.pixelart.R
import nft.nftcreator.pixel.pixelart.data.SharedPref
import java.util.Date


class AppOpenManager(myApplication: Application) : Application.ActivityLifecycleCallbacks,
    LifecycleObserver {
    private var isLoadingAd = false
    private var appOpenAd: AppOpenAd? = null
    private var isShowingAd = false
    private var currentActivity: Activity? = null
    private val myApplication: Application
    private var loadTime: Long = 0

    /**
     * Request an ad
     */

    fun fetchAd() {
        // Have unused ad, no need to fetch another.
        if (isLoadingAd || isAdAvailable) {
            return
        }

        isLoadingAd = true
        val request: AdRequest = adRequest //getAdRequest()
        AppOpenAd.load(
            myApplication,
            currentActivity?.getString(R.string.app_open_ad_unit_id)!!,
            request,
            object : AppOpenAdLoadCallback() {
                /**
                 * Called when an app open ad has loaded.
                 *
                 * @param ad the loaded app open ad.
                 */
                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad
                    isLoadingAd = false
                    loadTime = Date().time
                }

                /**
                 * Called when an app open ad has failed to load.
                 *
                 * @param loadAdError the error.
                 */
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    isLoadingAd = false
                    Log.e("APPOPENAD", "onAdFailedToLoad: " + loadAdError.message)
                }
            })
    }

    /**
     * Shows the ad if one isn't already showing.
     */
    fun showAdIfAvailable() {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.


        // If the app open ad is already showing, do not show the ad again.
        if (isShowingAd) {
            return
        }

        // If the app open ad is not available yet, invoke the callback then load the ad.
        if (!isAdAvailable) {
            fetchAd()
            return
        }

        appOpenAd!!.setFullScreenContentCallback(
            object : FullScreenContentCallback() {
                /** Called when full screen content is dismissed. */
                override fun onAdDismissedFullScreenContent() {
                    // Set the reference to null so isAdAvailable() returns false.
                    appOpenAd = null
                    isShowingAd = false
                    fetchAd()
                }

                /** Called when fullscreen content failed to show. */
                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    appOpenAd = null
                    isShowingAd = false
                    Log.e("APPOPENAD", "onAdFailedToShowFullScreenContent: " + adError.message)
                    fetchAd()
                }

                /** Called when fullscreen content is shown. */
                override fun onAdShowedFullScreenContent() {}
            })
        isShowingAd = true
        currentActivity?.let { appOpenAd!!.show(it) }
    }

    /**
     * Creates and returns ad request.
     */
    private val adRequest: AdRequest
        get() = AdRequest.Builder().build()

    /**
     * Utility method that checks if ad exists and can be shown.
     */


    /** Utility method to check if ad was loaded more than n hours ago.  */
    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
        val dateDifference = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * numHours
    }

    /** Utility method that checks if ad exists and can be shown.  */
//    fun isAdAvailable(): Boolean {
//        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4)
//    }

    val isAdAvailable: Boolean
        get() = appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4)

    override fun onActivityCreated(activity: Activity, @Nullable bundle: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {
        currentActivity = null
    }

    /** LifecycleObserver methods  */
    @OnLifecycleEvent(ON_START)
    fun onStart() {

        if (!SharedPref(myApplication.applicationContext).getAdRemoved()) {
            showAdIfAvailable()
        }

    }

    init {
        this.myApplication = myApplication
        this.myApplication.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }
}