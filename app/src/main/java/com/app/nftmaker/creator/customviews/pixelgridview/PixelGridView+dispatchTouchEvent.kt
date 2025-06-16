package nft.nftcreator.pixel.pixelart.creator.customviews.pixelgridview

import android.view.MotionEvent
import nft.nftcreator.pixel.pixelart.creator.models.BitmapAction
import nft.nftcreator.pixel.pixelart.creator.models.Coordinates
import android.graphics.PointF
import android.util.Log
import nft.nftcreator.pixel.pixelart.creator.activities.canvas.spanCount

fun PixelGridView.extendedDispatchTouchEvent(event: MotionEvent): Boolean {
    val coordinateX = (event.x / scaleWidth).toInt()
    val coordinateY = (event.y / scaleWidth).toInt()

    if (currentBitmapAction == null) {
        currentBitmapAction = BitmapAction(mutableListOf())
    }

    when (event.actionMasked) {
        MotionEvent.ACTION_MOVE -> {
            if (coordinateX in 0 until canvasSize && coordinateY in 0 until canvasSize) {
                caller.onPixelTapped(Coordinates(coordinateX, coordinateY))
            } else {
                prevX = null
                prevY = null
            }
        }
        MotionEvent.ACTION_DOWN -> {
            if (coordinateX in 0 until canvasSize && coordinateY in 0 until canvasSize) {
                caller.onPixelTapped(Coordinates(coordinateX, coordinateY))
            } else {
                prevX = null
                prevY = null
            }
        }
        MotionEvent.ACTION_UP -> {
            caller.onActionUp()
        }
    }

    invalidate()

    return true
}