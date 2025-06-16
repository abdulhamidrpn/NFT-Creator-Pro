package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import nft.nftcreator.pixel.pixelart.creator.adapters.ColorPickerAdapter
import nft.nftcreator.pixel.pixelart.creator.models.ColorPalette

fun CanvasActivity.extendedOnColorPaletteTapped(selectedColorPalette: ColorPalette) {
    binding.activityCanvasColorPickerRecyclerView.adapter = ColorPickerAdapter(selectedColorPalette, this)
}