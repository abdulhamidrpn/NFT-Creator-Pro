package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import android.content.Context
import nft.nftcreator.pixel.pixelart.data.SharedPref

fun CanvasActivity.initSharedPreferenceObject() {
    sharedPreferenceObject = this.getPreferences(Context.MODE_PRIVATE)
    sharedPref = SharedPref(this)
}