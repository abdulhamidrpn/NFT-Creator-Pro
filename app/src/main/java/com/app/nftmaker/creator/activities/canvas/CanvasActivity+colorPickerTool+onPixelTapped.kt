package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import android.graphics.Color
import nft.nftcreator.pixel.pixelart.creator.models.Coordinates

fun CanvasActivity.colorPickerToolOnPixelTapped(coordinatesTapped: Coordinates) {
    val color = outerCanvasInstance.canvasFragment.myCanvasViewInstance.pixelGridViewBitmap.getPixel(coordinatesTapped.x, coordinatesTapped.y)

    if (color == Color.TRANSPARENT) setPixelColor(Color.WHITE)
    else setPixelColor(color)
}