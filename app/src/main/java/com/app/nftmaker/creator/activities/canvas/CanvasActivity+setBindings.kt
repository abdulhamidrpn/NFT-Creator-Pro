package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import android.R
import android.os.Build
import android.view.View
import android.view.WindowInsets
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import nft.nftcreator.pixel.pixelart.databinding.ActivityCanvasBinding

fun CanvasActivity.setBindings() {
    enableEdgeToEdge()
    binding = ActivityCanvasBinding.inflate(layoutInflater)
    setContentView(binding.root)


    // Add padding to the root layout to account for the ActionBar
    /*ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
        val navBars = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
        val statusBars = insets.getInsets(WindowInsetsCompat.Type.statusBars())

        // Gesture nav detection: If bottom inset is small or invisible, treat it as gesture nav
        val isGestureNav = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            !insets.isVisible(WindowInsetsCompat.Type.navigationBars()) || navBars.bottom < 30
        } else {
            navBars.bottom < 30 // 30dp is a safe threshold to detect gestures
        }

        view.setPadding(
            navBars.left,
            statusBars.top,
            navBars.right,
            if (isGestureNav) 0 else navBars.bottom
        )
        insets
    }*/

}