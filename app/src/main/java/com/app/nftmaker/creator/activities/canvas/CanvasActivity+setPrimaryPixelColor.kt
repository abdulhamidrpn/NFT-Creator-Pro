package nft.nftcreator.pixel.pixelart.creator.activities.canvas

fun setPrimaryPixelColor(color: Int) {
    primaryColor = color
    binding.activityCanvasColorPrimaryView.setBackgroundColor(color)
}