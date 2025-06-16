package nft.nftcreator.pixel.pixelart.data

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.google.ads.consent.ConsentForm
import com.google.ads.consent.ConsentFormListener
import com.google.ads.consent.ConsentInfoUpdateListener
import com.google.ads.consent.ConsentInformation
import com.google.ads.consent.ConsentStatus
import com.google.ads.consent.DebugGeography
import nft.nftcreator.pixel.pixelart.BuildConfig
import nft.nftcreator.pixel.pixelart.R
import java.net.MalformedURLException
import java.net.URL

object GDPR {

    fun getBundleAd(act: Context): Bundle {
        val extras = Bundle()
        val consentInformation: ConsentInformation = ConsentInformation.getInstance(act)
        if (consentInformation.consentStatus.equals(ConsentStatus.NON_PERSONALIZED)) {
            extras.putString("npa", "1")
        }
        return extras
    }

    fun updateConsentStatus(act: Activity) {
        val consentInformation: ConsentInformation = ConsentInformation.getInstance(act)
        if (BuildConfig.DEBUG) {
            // How to get device ID : https://goo.gl/2ompNn, https://goo.gl/jrCqfY
            consentInformation.addTestDevice("5417CD40436EA9EEDB1801BAF429F990")
            consentInformation.debugGeography = DebugGeography.DEBUG_GEOGRAPHY_EEA
        }
        val publisherId: String = act.getString(R.string.admob_app_id)
        if (TextUtils.isEmpty(publisherId)) return
        consentInformation.requestConsentInfoUpdate(
            arrayOf(publisherId),
            object : ConsentInfoUpdateListener {
                override fun onConsentInfoUpdated(consentStatus: ConsentStatus?) {
                    if (consentStatus === ConsentStatus.UNKNOWN) {
                        GDPRForm(act).displayConsentForm()
                    }
                }

                override fun onFailedToUpdateConsentInfo(reason: String?) {
                    Log.e("GDPR", reason!!)
                }

            }
        )
    }

    private class GDPRForm(act: Activity) {
        private lateinit var form: ConsentForm
        private val activity: Activity = act

        private var sharedPref: SharedPref = SharedPref(act)

        fun displayConsentForm() {
            val url = urlPrivacyPolicy ?: return
            val builder: ConsentForm.Builder = ConsentForm.Builder(activity, url)
            builder.withPersonalizedAdsOption()
            builder.withNonPersonalizedAdsOption()
            builder.withListener(object : ConsentFormListener() {
                override fun onConsentFormLoaded() {
                    super.onConsentFormLoaded()
                    // Consent form loaded successfully.
                    try {
                        form.show()
                    } catch (e: Exception) {
                        Log.e("GDPR", e.message!!)
                    }
                }

            })
            form = builder.build()
            form.load()
        }

        private val urlPrivacyPolicy: URL?
            get() {
                try {
                    val policyPrivacy: String = activity.getString(R.string.term_and_condition_url)
                    URL(policyPrivacy)
                } catch (e: MalformedURLException) {
                    Log.e("GDPR", e.message!!)
                }
                return null
            }

    }
}