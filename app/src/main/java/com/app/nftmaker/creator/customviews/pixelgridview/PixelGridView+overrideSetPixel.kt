package nft.nftcreator.pixel.pixelart.creator.customviews.pixelgridview

import nft.nftcreator.pixel.pixelart.creator.activities.canvas.outerCanvasInstance
import nft.nftcreator.pixel.pixelart.creator.models.BitmapActionData
import nft.nftcreator.pixel.pixelart.creator.models.Coordinates

fun PixelGridView.extendedOverrideSetPixel(x: Int, y: Int, color: Int) {
    val coordinates = Coordinates(x, y)
    if (coordinatesInCanvasBounds(coordinates)) {
        pixelGridViewBitmap.setPixel(coordinates.x, coordinates.y, color)
        outerCanvasInstance.canvasFragment.myCanvasViewInstance.currentBitmapAction!!.actionData.add(BitmapActionData(coordinates, pixelGridViewBitmap.getPixel(coordinates.x, coordinates.y)))
    }
}