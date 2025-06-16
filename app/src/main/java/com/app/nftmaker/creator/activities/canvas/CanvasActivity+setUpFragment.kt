package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import nft.nftcreator.pixel.pixelart.R
import nft.nftcreator.pixel.pixelart.creator.fragments.outercanvas.OuterCanvasFragment

fun CanvasActivity.setUpFragment() {
    outerCanvasInstance = if (index == -1) {
        OuterCanvasFragment.newInstance(spanCount)
    } else {
        OuterCanvasFragment.newInstance(spanCount, true)
    }
    supportFragmentManager.beginTransaction().add(R.id.activityCanvas_outerCanvasFragmentHost, outerCanvasInstance).commit()
}