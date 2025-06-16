package nft.nftcreator.pixel.pixelart.creator.database

import android.graphics.Color

object ColorDatabase {
    private val database = mutableListOf<Int>()

    private fun addColor(color: Int) = database.add(color)

    fun toList() = database.toList()

    init {
        val colorData = listOf(
            Color.parseColor("#0000FF"),
            Color.parseColor("#FF0000"),
            Color.parseColor("#00FF00"),
            Color.parseColor("#808080"),
            Color.parseColor("#FFFF00"),
            Color.TRANSPARENT
        )

        for (color in colorData) addColor(color)
    }
}