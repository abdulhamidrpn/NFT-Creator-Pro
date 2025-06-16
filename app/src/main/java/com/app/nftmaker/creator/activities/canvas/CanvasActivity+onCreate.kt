package nft.nftcreator.pixel.pixelart.creator.activities.canvas

fun CanvasActivity.onCreate() {
    getExtras()
    setUpFragment()
    setBindings()
    initSharedPreferenceObject()
}