package nft.nftcreator.pixel.pixelart.creator.algorithms

import nft.nftcreator.pixel.pixelart.creator.activities.canvas.extendedUndo
import nft.nftcreator.pixel.pixelart.creator.models.BitmapAction
import nft.nftcreator.pixel.pixelart.creator.models.BitmapActionData

class PixelPerfectAlgorithm(private val algorithmInfoParameter: AlgorithmInfoParameter) {
    fun compute() {
        var distinct = algorithmInfoParameter.currentBitmapAction.actionData.distinctBy { it.xyPosition }
        val data = mutableListOf<BitmapActionData>()

        var index = 0

        while (index < distinct.size) {
            if (index > 0 && index + 1 < distinct.size
                && (distinct[index - 1].xyPosition.x == distinct[index].xyPosition.x || distinct[index - 1].xyPosition.y == distinct[index].xyPosition.y)
                && (distinct[index + 1].xyPosition.x == distinct[index].xyPosition.x || distinct[index + 1].xyPosition.y == distinct[index].xyPosition.y)
                && distinct[index - 1].xyPosition.x != distinct[index + 1].xyPosition.x
                && distinct[index - 1].xyPosition.y != distinct[index + 1].xyPosition.y
            ) {
                index += 1
            }

            data.add(distinct[index])

            index += 1
        }

        extendedUndo()

        for (value in data) {
            distinct = distinct.filter { it == value }
        }

        for (value in data) {
            algorithmInfoParameter.bitmap.setPixel(
                value.xyPosition.x,
                value.xyPosition.y,
                algorithmInfoParameter.color
            )
        }

        algorithmInfoParameter.currentBitmapActionData!!.toMutableList().add(BitmapAction(data))
    }
}