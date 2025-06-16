package nft.nftcreator.pixel.pixelart.creator.listeners

import nft.nftcreator.pixel.pixelart.creator.models.ColorPalette

interface ColorPalettesFragmentListener {
    fun onColorPaletteTapped(selectedColorPalette: ColorPalette)
}