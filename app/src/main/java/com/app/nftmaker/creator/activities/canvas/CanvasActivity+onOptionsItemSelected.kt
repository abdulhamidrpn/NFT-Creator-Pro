package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import nft.nftcreator.pixel.pixelart.R
import nft.nftcreator.pixel.pixelart.inapp.enableAd
import nft.nftcreator.pixel.pixelart.utils.PermissionUtil


@RequiresApi(Build.VERSION_CODES.M)
fun CanvasActivity.extendedOnOptionsItemSelected(item: MenuItem): Boolean {
    val zoomIncrement = 0.2f
    var rotation = 0f

    when (item.itemId) {
        R.id.zoom_out -> {
            outerCanvasInstance.cardViewParent.apply {
                if (outerCanvasInstance.cardViewParent.scaleX - zoomIncrement > 0.2f) {
                    scaleX -= zoomIncrement
                    scaleY -= zoomIncrement
                }
            }
        }
        R.id.zoom_in -> {
            outerCanvasInstance.cardViewParent.apply {
                scaleX += zoomIncrement
                scaleY += zoomIncrement
            }
        }
        R.id.save_project -> {
//            After showing ad extendedSaveProject() this will be called.
            startAnotherActivity()
        }

        R.id.undo -> extendedUndo()

        R.id.redo -> extendedRedo()

        //TODO CHANGE PROJECT NAME AND ADD MENU BUTTON
//        R.id.change_project_name -> changeProjectName(context)

        R.id.import_image -> {

            if (isRunningOnAndroid13()) {

                importImage(context)
                return true
            }
            else if (!PermissionUtil().isStorageGranted(this)) {
                if (sharedPref!!.getNeverAskAgain(PermissionUtil().STORAGE)) {
                    PermissionUtil().showDialog(this)
                } else {
                    requestPermissions(PermissionUtil().PERMISSION_STORAGE, 500)
                }
                return true
            }

            importImage(context)
        }

        R.id.rotate -> {

            if (rotation >= 360f) {
                rotation = 0f
                rotate(rotation)
            } else {
                rotation = rotation + 90f
                rotate(rotation)
            }
        }
        R.id.flip -> {
            rotate(180f)
        }
//TODO::GRID ON OFF
/*
R.id.grid_on_off -> {
    if (sharedPref!!.getGridOn()){
        sharedPref?.setGridOn(false)
    }else{
        sharedPref?.setGridOn(true)
    }
    outerCanvasInstance.showGrid()
}*/
        R.id.transparent_on_off -> {
            if (sharedPref!!.getTransparentOn()) {
                sharedPref?.setTransparentOn(false)
                transparentMenuItem.title = "Transparent Off"
            } else {
                sharedPref?.setTransparentOn(true)
                transparentMenuItem.title = "Transparent On"
            }
            outerCanvasInstance.showTransparent()
        }

        R.id.export_to_png -> {

            if (isRunningOnAndroid13()) {

                Log.d(TAG, "extendedOnOptionsItemSelected: saved.")
                outerCanvasInstance.canvasFragment.myCanvasViewInstance.saveAsImage(Bitmap.CompressFormat.PNG)
                return true
            }
            else if (!PermissionUtil().isStorageGranted(this)) {
                if (sharedPref!!.getNeverAskAgain(PermissionUtil().STORAGE)) {
                    PermissionUtil().showDialog(this)
                } else {
                    requestPermissions(PermissionUtil().PERMISSION_STORAGE, 500)
                }
                return true
            }

            Log.d(TAG, "extendedOnOptionsItemSelected: saved.")
            outerCanvasInstance.canvasFragment.myCanvasViewInstance.saveAsImage(Bitmap.CompressFormat.PNG)
        }

        R.id.export_to_jpg -> {
            if (isRunningOnAndroid13()) {

                Log.d(TAG, "extendedOnOptionsItemSelected: saved.")
                outerCanvasInstance.canvasFragment.myCanvasViewInstance.saveAsImage(Bitmap.CompressFormat.JPEG)
                return true
            }
            else if (!PermissionUtil().isStorageGranted(this)) {
                if (sharedPref!!.getNeverAskAgain(PermissionUtil().STORAGE)) {
                    PermissionUtil().showDialog(this)
                } else {
                    requestPermissions(PermissionUtil().PERMISSION_STORAGE, 500)
                }
                return true
            }
            outerCanvasInstance.canvasFragment.myCanvasViewInstance.saveAsImage(Bitmap.CompressFormat.JPEG)
        }

        R.id.reset_zoom -> {
            resetZoom()
        }
    }
    return true
}




