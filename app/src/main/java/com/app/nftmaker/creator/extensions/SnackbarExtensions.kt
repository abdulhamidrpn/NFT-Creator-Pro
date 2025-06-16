package nft.nftcreator.pixel.pixelart.creator.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

enum class SnackbarDuration(val timeValue: Int) {
    DEFAULT(1500),
    MEDIUM(3000)
}

fun View.showSnackbar(snackbarText: String, duration: SnackbarDuration) {
    Snackbar.make(this, snackbarText, duration.timeValue)
            .show()
}

fun View.showSnackbarWithAction(snackbarText: String, duration: SnackbarDuration, actionText: String, actionOnClickListener: View.OnClickListener) {
    Snackbar.make(this, snackbarText, duration.timeValue)
        .setAction(actionText, actionOnClickListener)
        .show()
}