package nft.nftcreator.pixel.pixelart.creator.listeners

import nft.nftcreator.pixel.pixelart.creator.models.Coordinates

interface CanvasFragmentListener {
    fun onPixelTapped(coordinatesTapped: Coordinates)
    fun onActionUp()
}