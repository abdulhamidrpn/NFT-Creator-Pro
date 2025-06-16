package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.ext.SdkExtensions.getExtensionVersion
import android.provider.MediaStore

private const val ANDROID_R_REQUIRED_EXTENSION_VERSION = 2
val PICK_IMAGE_CODE = 100
fun CanvasActivity.importImage(context: Context) {
//    val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

    val intent = if (isRunningOnAndroid13())
        Intent(MediaStore.ACTION_PICK_IMAGES)
    else
        Intent(Intent.ACTION_PICK)
    intent.apply {
        type = "image/*"
        putExtra("crop", "true") // NOTE: should be string
        putExtra("outputX", 300) // This is needed, editor can't close without these two
        putExtra("outputY", 300) // This is needed

        putExtra("scale", true)
        putExtra("aspectX", 1)
        putExtra("aspectY", 1)
        putExtra("return-data", true)
    }
    startActivityForResult(intent, PICK_IMAGE_CODE)
}

