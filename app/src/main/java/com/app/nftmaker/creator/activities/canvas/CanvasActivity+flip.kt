package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import nft.nftcreator.pixel.pixelart.creator.utility.IntConstants

fun flip(flip: Int = IntConstants.DEGREES_ONE_EIGHTY) {
    outerCanvasInstance.flip(flip)
}