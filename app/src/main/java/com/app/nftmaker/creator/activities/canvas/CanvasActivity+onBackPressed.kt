package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import android.util.Log
import android.view.View
import nft.nftcreator.pixel.pixelart.ActivityHome
import nft.nftcreator.pixel.pixelart.advertise.AdNetworkHelper
import nft.nftcreator.pixel.pixelart.creator.extensions.navigateHome
import nft.nftcreator.pixel.pixelart.creator.extensions.showDialog
import nft.nftcreator.pixel.pixelart.data.AppConfig
import nft.nftcreator.pixel.pixelart.data.StringConstants

private fun showInterstitialAd(): Boolean {
    return adNetworkHelper.showInterstitialAd(AppConfig.ADS_MAIN_INTERS)
}

fun CanvasActivity.extendedOnBackPressed() {

    try {
        Log.d("AdNetworkHelper", "Clicked back button show ad")
        if (showInterstitialAd()) return
    } catch (e: Exception) {
    }

    if (!saved && currentFragmentInstance == null) {

        showDialog(
            StringConstants.DIALOG_UNSAVED_CHANGES_TITLE,
            StringConstants.DIALOG_UNSAVED_CHANGES_MESSAGE,
            StringConstants.DIALOG_POSITIVE_BUTTON_TEXT,
            { _, _ ->
                finish()
            }, StringConstants.DIALOG_NEGATIVE_BUTTON_TEXT, { _, _ -> }, null
        )
    } else if (currentFragmentInstance != null) {

        navigateHome(
            supportFragmentManager,
            currentFragmentInstance!!,
            binding.activityCanvasRootLayout,
            binding.activityCanvasPrimaryFragmentHost,
            intent.getStringExtra("PROJECT_TITLE")!!
        )
        currentFragmentInstance = null
        showMenuItems()

        if (isPrimaryColorSelected) {
            binding.activityCanvasColorPrimaryViewIndicator.visibility = View.VISIBLE
        } else {
            binding.activityCanvasColorPrimaryViewIndicator.visibility = View.INVISIBLE
        }
    } else {
        finish()
    }
}