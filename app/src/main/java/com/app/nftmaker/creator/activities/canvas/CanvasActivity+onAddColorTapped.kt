package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import nft.nftcreator.pixel.pixelart.creator.database.AppData
import nft.nftcreator.pixel.pixelart.creator.models.ColorPalette

var fromDB: ColorPalette? = null

fun CanvasActivity.extendedOnAddColorTapped(colorPalette: ColorPalette) {
    hideMenuItems()

    AppData.colorPalettesDB.colorPalettesDao().getAllColorPalettes().observe(this) {
        for (data in it) {
            if (data.objId == colorPalette.objId) {
                fromDB = data
                break
            }
        }
    }
    openColorPickerDialog(true)
}