package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import android.R.attr
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream


fun CanvasActivity.extendedOnRequestActivityResult(
    requestCode: Int, resultCode: Int, data: Intent?
) {
    if (resultCode == AppCompatActivity.RESULT_OK && requestCode == PICK_IMAGE_CODE) {

        val extras = data?.extras
        Log.d(TAG, "extendedOnRequestActivityResult: data $data extras $extras")
        if (extras != null) {
            val photo = extras.getParcelable<Bitmap>("data")
            val stream = ByteArrayOutputStream()
            photo!!.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            Log.d(TAG, "extendedOnRequestActivityResult: photo is  $photo")
            val resized = getResizedBitmap(photo, spanCount, spanCount)
            Log.d(TAG, "extendedOnRequestActivityResult: resized $resized")

            resized?.let {
                outerCanvasInstance.canvasFragment.myCanvasViewInstance
                    .replaceBitmap(it)
            }
        }else if(data?.data !=null){

            val imageUri = data.data

            val photo: Bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(imageUri.toString()))
            val stream = ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            Log.d(TAG, "extendedOnRequestActivityResult: photo is  $photo")
            val resized = getResizedBitmap(photo, spanCount, spanCount)
            Log.d(TAG, "extendedOnRequestActivityResult: resized $resized")

            resized?.let {
                outerCanvasInstance.canvasFragment.myCanvasViewInstance
                    .replaceBitmap(it)
            }
        }

    }
}
fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
    val width = bm.width
    val height = bm.height
    val scaleWidth = newWidth.toFloat() / width
    val scaleHeight = newHeight.toFloat() / height
    // CREATE A MATRIX FOR THE MANIPULATION
    val matrix = Matrix()
    // RESIZE THE BIT MAP
    matrix.postScale(scaleWidth, scaleHeight)

    // "RECREATE" THE NEW BITMAP
    val resizedBitmap = Bitmap.createBitmap(
        bm, 0, 0, width, height, matrix, false
    )
//        bm.recycle()
    return resizedBitmap
}