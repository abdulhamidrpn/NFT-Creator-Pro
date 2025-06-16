package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import nft.nftcreator.pixel.pixelart.R

fun setColors(context:Context) {
    setPrimaryPixelColor(ContextCompat.getColor(context, R.color.primary))
    setSecondaryPixelColor(Color.BLUE)
}