package nft.nftcreator.pixel.pixelart.creator.listeners

interface ColorPickerFragmentListener {
    fun onDoneButtonPressed(selectedColor: Int, isColorPaletteMode: Boolean = false)
}