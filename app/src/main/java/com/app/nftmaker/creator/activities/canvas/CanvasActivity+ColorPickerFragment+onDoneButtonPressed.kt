package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import nft.nftcreator.pixel.pixelart.creator.adapters.ColorPickerAdapter
import nft.nftcreator.pixel.pixelart.creator.converters.JsonConverter
import nft.nftcreator.pixel.pixelart.creator.database.AppData
import nft.nftcreator.pixel.pixelart.creator.extensions.navigateHome
import nft.nftcreator.pixel.pixelart.data.StringConstants

@RequiresApi(Build.VERSION_CODES.N)
fun CanvasActivity.extendedOnDoneButtonPressed(selectedColor: Int, colorPaletteMode: Boolean) {
    showMenuItems()
    setPixelColor(selectedColor)
    val newData = JsonConverter.convertJsonStringToListOfInt(fromDB!!.colorPaletteColorData).toMutableList()
    if(!newData.contains(selectedColor)){
        newData.add(selectedColor)
        newData.removeIf { it == Color.TRANSPARENT }
        newData.add(Color.TRANSPARENT)

        AppData.colorPalettesDB.colorPalettesDao().updateColorPaletteColorData(JsonConverter.convertListOfIntToJsonString(newData.toList()), fromDB!!.objId)
        AppData.colorPalettesDB.colorPalettesDao().getAllColorPalettes().observe(this) {
            binding.activityCanvasColorPickerRecyclerView.adapter =
                ColorPickerAdapter(fromDB!!, this)
            binding.activityCanvasColorPickerRecyclerView.scrollToPosition(
                JsonConverter.convertJsonStringToListOfInt(
                    fromDB!!.colorPaletteColorData
                ).size
            )
        }
    }

    currentFragmentInstance = null
    navigateHome(supportFragmentManager, colorPickerFragmentInstance, binding.activityCanvasRootLayout, binding.activityCanvasPrimaryFragmentHost, intent.getStringExtra(
        StringConstants.PROJECT_TITLE_EXTRA)!!)

    if (isPrimaryColorSelected) {
        binding.activityCanvasColorPrimaryViewIndicator.visibility = View.VISIBLE
    } else {
        binding.activityCanvasColorPrimaryViewIndicator.visibility = View.INVISIBLE
    }
}