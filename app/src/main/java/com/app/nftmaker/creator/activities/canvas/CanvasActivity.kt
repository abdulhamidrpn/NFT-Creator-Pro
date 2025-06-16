package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import nft.nftcreator.pixel.pixelart.R
import nft.nftcreator.pixel.pixelart.creator.fragments.colorpicker.ColorPickerFragment
import nft.nftcreator.pixel.pixelart.creator.listeners.CanvasFragmentListener
import nft.nftcreator.pixel.pixelart.creator.listeners.ColorPalettesFragmentListener
import nft.nftcreator.pixel.pixelart.creator.listeners.ColorPickerFragmentListener
import nft.nftcreator.pixel.pixelart.creator.listeners.ColorPickerListener
import nft.nftcreator.pixel.pixelart.creator.listeners.ToolsFragmentListener
import nft.nftcreator.pixel.pixelart.creator.models.ColorPalette
import nft.nftcreator.pixel.pixelart.creator.models.Coordinates
import nft.nftcreator.pixel.pixelart.inapp.enableAd


class CanvasActivity :
    AppCompatActivity(),
    CanvasFragmentListener,
    ColorPickerListener,
    ColorPickerFragmentListener,
    ToolsFragmentListener,
    ColorPalettesFragmentListener {

    var previousView: View? = null

    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreate()
        SetupInterstitialAd()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setUpRecyclerView()
        setOnClickListeners()
        setColors(this)
        replaceBitmapIfApplicable()
        //TODO/ UNCOMMENT ADS
        prepareAds()
        if (!sharedPref!!.getAdRemoved()) {
            prepareAds()
        }


    }

    fun initColorPickerFragmentInstance(colorPaletteMode: Boolean) =
        ColorPickerFragment.newInstance(getSelectedColor(), colorPaletteMode)

    override fun onCreateOptionsMenu(menu: Menu?) = extendedOnCreateOptionsMenu(menu)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onOptionsItemSelected(item: MenuItem) = extendedOnOptionsItemSelected(item)

    fun getSelectedColor() = extendedGetSelectedColor()

    fun setPixelColor(color: Int) =
        if (isPrimaryColorSelected) setPrimaryPixelColor(color) else setSecondaryPixelColor(color)

    override fun onPause() {
        extendedOnPause()
        super.onPause()
    }

    override fun onPixelTapped(coordinatesTapped: Coordinates) =
        extendedOnPixelTapped(coordinatesTapped)

    override fun onActionUp() = extendedOnActionUp()

    override fun onColorTapped(colorTapped: Int, view: View) =
        extendedOnColorTapped(colorTapped, view)

    override fun onColorLongTapped(objId: Int, colorTapped: Int, view: View) =
        extendedOnColorLongTapped(objId, colorTapped)

    override fun onColorAdded(colorPalette: ColorPalette) = extendedOnAddColorTapped(colorPalette)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onDoneButtonPressed(selectedColor: Int, isColorPaletteMode: Boolean) =
        extendedOnDoneButtonPressed(selectedColor, isColorPaletteMode)

    override fun onBackPressed() {
        super.onBackPressed()
        extendedOnBackPressed()
    }

    override fun onToolTapped(toolName: String) = extendedOnToolTapped(toolName)

    override fun onColorPaletteTapped(selectedColorPalette: ColorPalette) =
        extendedOnColorPaletteTapped(selectedColorPalette)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        extendedOnRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        extendedOnRequestActivityResult(requestCode, resultCode, data)
    }


    fun SetupInterstitialAd() {

        requestNewInterstitial()
    }

    fun startAnotherActivity(showAd: Boolean = true) {
        if (enableAd.value == false && sharedPref?.getAdRemoved() == false && mInterstitialAd != null && showAd == true) {
            mInterstitialAd?.show(this@CanvasActivity)
        } else {
            extendedSaveProject()
        }
    }

    fun requestNewInterstitial() {
        MobileAds.initialize(this) { }
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this, getString(R.string.interstitial_app_id), adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd
                    Log.i(TAG, "onAdLoaded")


                    mInterstitialAd?.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdClicked() {
                                // Called when a click is recorded for an ad.
                                Log.d(TAG, "Ad was clicked.")
                            }

                            override fun onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad dismissed fullscreen content.")
                                mInterstitialAd = null
                                startAnotherActivity(false)
                                requestNewInterstitial()
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                // Called when ad fails to show.
                                Log.e(TAG, "Ad failed to show fullscreen content.")
                                mInterstitialAd = null
                            }

                            override fun onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Log.d(TAG, "Ad recorded an impression.")
                            }

                            override fun onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad showed fullscreen content.")
                            }
                        }


                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.d(TAG, "onAdFailedToLoad " + loadAdError.toString())
                    mInterstitialAd = null
                }
            })
    }

    fun isRunningOnAndroid13(): Boolean {
        // Get the current Android version.
        val currentVersion = Build.VERSION.SDK_INT

        // Return true if the current version is 13 or greater.
        return currentVersion >= Build.VERSION_CODES.R
    }

}


