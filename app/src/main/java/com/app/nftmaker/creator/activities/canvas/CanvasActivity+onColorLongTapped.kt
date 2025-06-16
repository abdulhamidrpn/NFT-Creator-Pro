package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import android.util.Log
import nft.nftcreator.pixel.pixelart.creator.adapters.ColorPickerAdapter
import nft.nftcreator.pixel.pixelart.creator.converters.JsonConverter
import nft.nftcreator.pixel.pixelart.creator.database.AppData
import nft.nftcreator.pixel.pixelart.creator.models.ColorPalette

fun CanvasActivity.extendedOnColorLongTapped(objId: Int, color: Int) {
    var it:List<ColorPalette> = AppData.colorPalettesDB.colorPalettesDao().getListColorPalettes()

    for (data in it) {
        if (data.objId == objId) {
            fromDB = data
            break
        }
    }

    val newData = JsonConverter.convertJsonStringToListOfInt(fromDB!!.colorPaletteColorData).toMutableList()
    newData.remove(color)
    AppData.colorPalettesDB.colorPalettesDao().updateColorPaletteColorData(JsonConverter.convertListOfIntToJsonString(newData.toList()), fromDB!!.objId)
    AppData.colorPalettesDB.colorPalettesDao().getAllColorPalettes().observe(this) {
        binding.activityCanvasColorPickerRecyclerView.adapter = ColorPickerAdapter(fromDB!!, this)
        binding.activityCanvasColorPickerRecyclerView.scrollToPosition(
            JsonConverter.convertJsonStringToListOfInt(
                fromDB!!.colorPaletteColorData
            ).size
        )
    }

}