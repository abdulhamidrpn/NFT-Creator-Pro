package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import android.view.Menu
import nft.nftcreator.pixel.pixelart.R

fun CanvasActivity.extendedOnCreateOptionsMenu(_menu: Menu?): Boolean {
    val inflater = menuInflater
    inflater.inflate(R.menu.app_menu, _menu)

    if (_menu != null) {
        menu = _menu


        transparentMenuItem = menu.findItem(R.id.transparent_on_off)
        if (sharedPref!!.getTransparentOn())   transparentMenuItem.title = "Transparent Off"
        else  transparentMenuItem.title = "Transparent On"

    }

    if (index != -1) setMenuItemIcon(_menu!!.getItem(4), R.drawable.ic_baseline_save_24, "Save")

    return true
}