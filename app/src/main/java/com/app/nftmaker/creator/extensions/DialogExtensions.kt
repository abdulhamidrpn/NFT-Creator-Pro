package nft.nftcreator.pixel.pixelart.creator.extensions

import android.app.Activity
import android.content.DialogInterface
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Activity.showDialog(
    dialogTitle: String,
    dialogMessage: String,
    dialogPositiveButtonText: String,
    dialogPositiveButtonAction: DialogInterface.OnClickListener,
    dialogNegativeButtonText: String?,
    dialogNegativeButtonAction: DialogInterface.OnClickListener?,
    view: View?) {

    MaterialAlertDialogBuilder(this)
        .setTitle(dialogTitle)
        .setMessage(dialogMessage)
        .setView(view)
        .setPositiveButton(dialogPositiveButtonText, dialogPositiveButtonAction)
        .apply {
            if (dialogNegativeButtonText != null && dialogNegativeButtonAction != null) {
                setNegativeButton(dialogNegativeButtonText, dialogNegativeButtonAction)
            }
        }
        .show()
    /*MaterialAlertDialogBuilder(this)
        .setTitle(dialogTitle)
        .setMessage(dialogMessage)
        .setView(view)
        .setPositiveButton(dialogPositiveButtonText, dialogPositiveButtonAction)
        .setNegativeButton(dialogNegativeButtonText, dialogNegativeButtonAction)
        .show()*/
}
