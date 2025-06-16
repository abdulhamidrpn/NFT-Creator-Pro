package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import nft.nftcreator.pixel.pixelart.creator.algorithms.AlgorithmInfoParameter
import nft.nftcreator.pixel.pixelart.creator.algorithms.FloodFillAlgorithm
import nft.nftcreator.pixel.pixelart.creator.models.Coordinates

fun CanvasActivity.fillToolOnPixelTapped(coordinatesTapped: Coordinates) {
    val floodFillAlgorithmInstance = FloodFillAlgorithm(AlgorithmInfoParameter(outerCanvasInstance.canvasFragment.myCanvasViewInstance.pixelGridViewBitmap, outerCanvasInstance.canvasFragment.myCanvasViewInstance.currentBitmapAction!!, getSelectedColor()))
    floodFillAlgorithmInstance.compute(Coordinates(coordinatesTapped.x, coordinatesTapped.y))
}