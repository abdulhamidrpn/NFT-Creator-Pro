package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nft.nftcreator.pixel.pixelart.creator.converters.BitmapConverter
import nft.nftcreator.pixel.pixelart.creator.database.AppData
import nft.nftcreator.pixel.pixelart.creator.models.PixelArt

fun CanvasActivity.extendedSaveProject() {
    saved = true

    val bmp = getCoverImageBitmap()

    if (index == -1) {
        CoroutineScope(Dispatchers.IO).launch {
            AppData.pixelArtDB.pixelArtCreationsDao().insertPixelArt(
                PixelArt(
                    BitmapConverter.convertBitmapToString(bmp),
                    BitmapConverter.convertBitmapToString(outerCanvasInstance.canvasFragment.myCanvasViewInstance.pixelGridViewBitmap),
                    spanCount,
                    spanCount,
                    outerCanvasInstance.getCurrentRotation(),
                    title.toString(),
                    false
                )
            )
        }
        onBackPressed()
    } else {
        outerCanvasInstance.canvasFragment.myCanvasViewInstance.invalidate()

        AppData.pixelArtDB.pixelArtCreationsDao().apply {
            updatePixelArtCreationTitle(title.toString(), currentPixelArtObj.objId)
            updatePixelArtCreationCoverBitmap(
                BitmapConverter.convertBitmapToString(bmp),
                currentPixelArtObj.objId
            )
            updatePixelArtCreationBitmap(
                BitmapConverter.convertBitmapToString(outerCanvasInstance.canvasFragment.myCanvasViewInstance.pixelGridViewBitmap),
                currentPixelArtObj.objId
            )
            updatePixelArtCreationRotation(
                outerCanvasInstance.getCurrentRotation(),
                currentPixelArtObj.objId
            )
        }
        //Todo: uncomment
//        outerCanvasInstance.rotate(0)
        onBackPressed()
    }

    Toast.makeText(this, "Successfully saved $projectTitle", Toast.LENGTH_SHORT).show()
}