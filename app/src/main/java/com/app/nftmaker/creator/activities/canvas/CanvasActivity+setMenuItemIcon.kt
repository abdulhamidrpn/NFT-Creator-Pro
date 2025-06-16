package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import android.view.MenuItem
import androidx.core.content.ContextCompat

fun CanvasActivity.setMenuItemIcon(item: MenuItem, icon: Int, tooltipText: CharSequence? = item.tooltipText) {
    item.icon = ContextCompat.getDrawable(this, icon)
    item.tooltipText = tooltipText
}
