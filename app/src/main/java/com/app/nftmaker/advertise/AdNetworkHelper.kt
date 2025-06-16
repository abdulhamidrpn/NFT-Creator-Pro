package nft.nftcreator.pixel.pixelart.advertise

//import com.facebook.ads.*
//import com.google.android.gms.ads.interstitial.InterstitialAd

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.app.nftmaker.advertise.AudienceNetworkInitializeHelper
import com.facebook.ads.Ad
import com.facebook.ads.AudienceNetworkAds
import com.facebook.ads.InterstitialAdListener
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import nft.nftcreator.pixel.pixelart.BuildConfig
import nft.nftcreator.pixel.pixelart.R
import nft.nftcreator.pixel.pixelart.data.AppConfig
import nft.nftcreator.pixel.pixelart.data.GDPR
import nft.nftcreator.pixel.pixelart.data.SharedPref


class AdNetworkHelper(private val activity: Activity) {

    private val TAG = AdNetworkHelper::class.java.simpleName

    private val sharedPref = SharedPref(activity)

    //Interstitial
    private var adMobInterstitialAd: com.google.android.gms.ads.interstitial.InterstitialAd? = null
    private var fbInterstitialAd: com.facebook.ads.InterstitialAd? = null


    companion object {
        fun init(context: Context) {
            MobileAds.initialize(context)
            if (BuildConfig.DEBUG) {
                val configuration = RequestConfiguration.Builder()
                    .setTestDeviceIds(listOf("5417CD40436EA9EEDB1801BAF429F990")).build()
                MobileAds.setRequestConfiguration(configuration)
            }
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

    fun showGDPR() {
        GDPR.updateConsentStatus(activity)
    }

    fun loadBannerAd(enable: Boolean, facboodAdEnable: Boolean = false) {
        if (!enable) {
            val adContainer = activity.findViewById<LinearLayout>(R.id.ad_container)
            adContainer.removeAllViews()
            adContainer.visibility = View.GONE
            return
        }

        val adContainer = activity.findViewById<LinearLayout>(R.id.ad_container)
        adContainer.removeAllViews()
        //facebook ad
        if (facboodAdEnable) {
            val fbAdView = com.facebook.ads.AdView(
                activity,
                activity.getString(R.string.facebook_banner_unit_id),
                com.facebook.ads.AdSize.BANNER_HEIGHT_50
            )
            adContainer.addView(fbAdView)
//        fbAdView.loadAd()


            val adListener: com.facebook.ads.AdListener = object : com.facebook.ads.AdListener {
                override fun onError(ad: Ad?, adError: com.facebook.ads.AdError) {
                    adContainer.visibility = View.GONE
                }

                override fun onAdLoaded(ad: com.facebook.ads.Ad?) {
                    adContainer.visibility = View.VISIBLE
                }

                override fun onAdClicked(ad: com.facebook.ads.Ad?) {

                }

                override fun onLoggingImpression(ad: com.facebook.ads.Ad?) {

                }
            }

            fbAdView.loadAd(fbAdView.buildLoadAdConfig().withAdListener(adListener).build())

        } else {
            //Admob
            val adRequest = AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter::class.java, GDPR.getBundleAd(activity))
                .build()
            adContainer.visibility = View.GONE
            val adView = com.google.android.gms.ads.AdView(activity)
            adView.adUnitId = activity.getString(R.string.banner_app_id)
            adContainer.addView(adView)
            adView?.setAdSize(getAdmobBannerSize())
            adView.loadAd(adRequest)
            adView.adListener = object : com.google.android.gms.ads.AdListener() {
                override fun onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    adContainer.visibility = View.VISIBLE
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Code to be executed when an ad request fails.
                    adContainer.visibility = View.GONE
                }
            }
        }
    }

    //TODO : TO ENABLE GOOGLE ADS PUT FALSE = facboodAdEnable
    fun loadInterstitialAd(enable: Boolean, facboodAdEnable: Boolean = false) {
        if (!enable) {
            adMobInterstitialAd = null
            fbInterstitialAd?.destroy()
            fbInterstitialAd = null
            return
        }
        //facebook ad

        if (facboodAdEnable) {

            fbInterstitialAd = com.facebook.ads.InterstitialAd(
                activity,
                activity.getString(R.string.facebook_interstitial_unit_id)
            )
            // Create listeners for the Interstitial Ad

            val interstitialAdListener: InterstitialAdListener = object : InterstitialAdListener {
                override fun onInterstitialDisplayed(ad: Ad) {
                    // Interstitial ad displayed callback
                    Log.e(TAG, "Interstitial ad displayed.")
                    sharedPref.setIntersCounter(0)
                }

                override fun onInterstitialDismissed(ad: Ad) {
                    // Interstitial dismissed callback
                    Log.e(TAG, "Interstitial ad dismissed.")

                    fbInterstitialAd = null
                    loadInterstitialAd(!sharedPref.getAdRemoved())
                }

                override fun onError(ad: Ad?, adError: com.facebook.ads.AdError?) {
                    Log.e(TAG, "Interstitial ad failed to load: " + adError?.getErrorMessage())
                    fbInterstitialAd = null
                    Handler(Looper.getMainLooper()).postDelayed({
                        loadInterstitialAd(!sharedPref.getAdRemoved())
                    }, 30000)
                }

                override fun onAdLoaded(ad: Ad) {
                    // Interstitial ad is loaded and ready to be displayed
                    Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!")

                }

                override fun onAdClicked(ad: Ad) {
                    // Ad clicked callback
                    Log.d(TAG, "Interstitial ad clicked!")
                    fbInterstitialAd = null
                    loadInterstitialAd(enable)
                }

                override fun onLoggingImpression(ad: Ad) {
                    // Ad impression logged callback
                    Log.d(TAG, "Interstitial ad impression logged!")
                }
            }

            // For auto play video ads, it's recommended to load the ad
            // at least 30 seconds before it is shown

            // For auto play video ads, it's recommended to load the ad
            // at least 30 seconds before it is shown
            fbInterstitialAd?.loadAd(
                fbInterstitialAd?.buildLoadAdConfig()
                    ?.withAdListener(interstitialAdListener)
                    ?.build()
            )

        } else {

            //Admob
            val adRequest = AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter::class.java, GDPR.getBundleAd(activity))
                .build()
            com.google.android.gms.ads.interstitial.InterstitialAd.load(
                activity,
                activity.getString(R.string.interstitial_app_id),
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(interstitialAd: com.google.android.gms.ads.interstitial.InterstitialAd) {
                        adMobInterstitialAd = interstitialAd
                        adMobInterstitialAd!!.fullScreenContentCallback =
                            object : FullScreenContentCallback() {
                                override fun onAdDismissedFullScreenContent() {
                                    adMobInterstitialAd = null
                                    loadInterstitialAd(enable)
                                }

                                override fun onAdShowedFullScreenContent() {
                                    Log.d(TAG, "The ad was shown.")
                                    sharedPref.setIntersCounter(0)
                                }
                            }
                        Log.i(TAG, "onAdLoaded")
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        Log.i(TAG, loadAdError.message)
                        adMobInterstitialAd = null
                        Log.d(TAG, "Failed load AdMob Interstitial Ad")
                        Handler(Looper.getMainLooper()).postDelayed({
                            loadInterstitialAd(enable)
                        }, 10000)
                    }
                })
        }
    }

    fun showInterstitialAd(enable: Boolean, facbookAdEnable: Boolean = false): Boolean {
        if (!enable) return false
        val counter: Int = sharedPref.getIntersCounter()
        if (counter > AppConfig.ADS_INTERS_INTERVAL) {
            if (facbookAdEnable) {
                if (fbInterstitialAd == null) return false
                fbInterstitialAd?.show()
                Log.d(TAG, "facbookAdEnable $facbookAdEnable Interstitial Ad $fbInterstitialAd")
            } else {
                if (adMobInterstitialAd == null) return false
                adMobInterstitialAd?.show(activity)
            }
            return true
        } else {
            sharedPref.setIntersCounter(sharedPref.getIntersCounter() + 1)
        }
        return false
    }

    private fun getAdmobBannerSize(): com.google.android.gms.ads.AdSize {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        val display = activity.windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels.toFloat()
        val density = outMetrics.density
        val adWidth = (widthPixels / density).toInt()
        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return com.google.android.gms.ads.AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
            activity,
            adWidth
        )
    }
}