package nft.nftcreator.pixel.pixelart.creator.listeners

import android.view.View
import nft.nftcreator.pixel.pixelart.creator.models.ColorPalette

interface ColorPickerListener {
    fun onColorTapped(colorTapped: Int, view: View)
    fun onColorLongTapped(paletteId: Int, colorTapped: Int, view: View)
    fun onColorAdded(colorPalette: ColorPalette)
}