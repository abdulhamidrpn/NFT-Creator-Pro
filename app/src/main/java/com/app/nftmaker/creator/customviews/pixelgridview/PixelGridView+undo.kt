package nft.nftcreator.pixel.pixelart.creator.customviews.pixelgridview

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.graphics.set

fun PixelGridView.extendedUndo() {
    if (bitmapActionData.isNotEmpty()) {

        if (!bitmapActionData.last().isFilterBased) {
            for ((key, value) in bitmapActionData.last().actionData.distinctBy { it.xyPosition }) {
                pixelGridViewBitmap[key.x, key.y] = value
            }
        } else {
            for ((key, value) in bitmapActionData.last().actionData) {
                pixelGridViewBitmap[key.x, key.y] = value
            }
        }

        invalidate()
        bitmapActionData.removeAt(bitmapActionData.lastIndex)
    }
}