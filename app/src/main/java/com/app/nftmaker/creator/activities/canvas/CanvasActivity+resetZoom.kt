package nft.nftcreator.pixel.pixelart.creator.activities.canvas

fun resetZoom() {
    outerCanvasInstance.cardViewParent.apply {
        scaleX = 1f
        scaleY = 1f
    }
}