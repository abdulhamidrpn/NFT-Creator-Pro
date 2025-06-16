package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import nft.nftcreator.pixel.pixelart.creator.converters.BitmapConverter
import nft.nftcreator.pixel.pixelart.creator.database.AppData
import nft.nftcreator.pixel.pixelart.creator.utility.IntConstants

fun CanvasActivity.replaceBitmapIfApplicable() {
    if (index != -1) {
        AppData.pixelArtDB.pixelArtCreationsDao().getAllPixelArtCreations("").observe(context) {
            currentPixelArtObj = it[index!!]
            outerCanvasInstance.canvasFragment.myCanvasViewInstance.replaceBitmap(BitmapConverter.convertStringToBitmap(currentPixelArtObj.bitmap)!!)
            outerCanvasInstance.rotate(it[index!!].rotation.toInt(), false)
            IntConstants.SPAN_COUNT = currentPixelArtObj.width
        }
    }
}