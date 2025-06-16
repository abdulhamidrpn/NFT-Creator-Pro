package nft.nftcreator.pixel.pixelart.creator.listeners

import nft.nftcreator.pixel.pixelart.creator.models.ColorPalette

interface ColorPalettesListener {
    fun onColorPaletteTapped(selectedColorPalette: ColorPalette)
    fun onColorPaletteLongTapped(selectedColorPalette: ColorPalette)
}