package nft.nftcreator.pixel.pixelart.creator.listeners

import nft.nftcreator.pixel.pixelart.creator.models.PixelArt

interface RecentCreationsListener {
    fun onCreationTapped(creationTapped: PixelArt)
    fun onCreationLongTapped(creationTapped: PixelArt)
}