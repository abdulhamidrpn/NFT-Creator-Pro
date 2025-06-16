package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import nft.nftcreator.pixel.pixelart.data.StringConstants

fun rotate(rotationAngle: Float) {
    outerCanvasInstance.rotate(rotationAngle.toInt())
}