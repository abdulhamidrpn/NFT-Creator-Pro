@file:Suppress("DEPRECATION")

package nft.nftcreator.pixel.pixelart.creator.customviews.pixelgridview

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import nft.nftcreator.pixel.pixelart.creator.activities.canvas.projectTitle
import nft.nftcreator.pixel.pixelart.creator.activities.canvas.sharedPref
import nft.nftcreator.pixel.pixelart.creator.enums.OutputCode
import nft.nftcreator.pixel.pixelart.creator.extensions.SnackbarDuration
import nft.nftcreator.pixel.pixelart.creator.extensions.showDialog
import nft.nftcreator.pixel.pixelart.creator.extensions.showSnackbar
import nft.nftcreator.pixel.pixelart.creator.extensions.showSnackbarWithAction
import nft.nftcreator.pixel.pixelart.creator.utility.FileHelperUtilities
import nft.nftcreator.pixel.pixelart.data.StringConstants

lateinit var file: Uri

fun PixelGridView.extendedSaveAsImage(format: Bitmap.CompressFormat) {
    sharedPref?.setExported(true)
    val formatName = if (format == Bitmap.CompressFormat.PNG) "PNG" else "JPG"

    val fileHelperUtilitiesInstance = FileHelperUtilities.createInstance(this.context)

    fileHelperUtilitiesInstance.saveBitmapAsImage(90, format) { outputCode, _file, exceptionMessage_1 ->
        if (outputCode == OutputCode.SUCCESS) {
            file = _file!!
            showSnackbar("Successfully saved $projectTitle as $formatName", SnackbarDuration.MEDIUM)
//            showSnackbarWithAction("Successfully saved $projectTitle as $formatName", SnackbarDuration.MEDIUM, StringConstants.SNACKBAR_VIEW_EX_INFO_BUTTON_TEXT) {
//                fileHelperUtilitiesInstance.openImageFromUri(newUri) { outputCode, exceptionMessage_2 ->
//                    if (outputCode == OutputCode.FAILURE) {
//                        if (exceptionMessage_2 != null) {
//                            showSnackbarWithAction(StringConstants.DIALOG_VIEW_FILE_ERROR_TITLE, SnackbarDuration.DEFAULT, StringConstants.DIALOG_EXCEPTION_INFO_TITLE) {
//                                (context as Activity).showDialog(StringConstants.DIALOG_EXCEPTION_INFO_TITLE, exceptionMessage_2, StringConstants.DIALOG_POSITIVE_BUTTON_TEXT, { _, _ -> }, null, null, null)
//                            }
//                        } else {
//                            showSnackbar(StringConstants.DIALOG_VIEW_FILE_ERROR_TITLE, SnackbarDuration.DEFAULT)
//                        }
//                    }
//                }
//            }
        } else {
            if (exceptionMessage_1 != null) {
                showSnackbarWithAction("Error saving $projectTitle as $formatName", SnackbarDuration.DEFAULT, StringConstants.DIALOG_EXCEPTION_INFO_TITLE) {
                    (context as Activity)
                        .showDialog(StringConstants.DIALOG_EXCEPTION_INFO_TITLE, exceptionMessage_1, StringConstants.DIALOG_POSITIVE_BUTTON_TEXT, { _, _ -> }, null, null, null)
                }
            } else {
                showSnackbar("Error saving $projectTitle as $formatName", SnackbarDuration.DEFAULT)
            }
        }
    }
}