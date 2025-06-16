package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import nft.nftcreator.pixel.pixelart.creator.extensions.showDialog
import nft.nftcreator.pixel.pixelart.data.StringConstants

fun CanvasActivity.extendedOnToolTapped(toolName: String) {
    when (toolName) {
        StringConstants.PENCIL_TOOL_IDENTIFIER -> currentTool = Tools.PENCIL_TOOL

        StringConstants.FILL_TOOL_IDENTIFIER  -> currentTool = Tools.FILL_TOOL

        StringConstants.VERTICAL_MIRROR_TOOL_IDENTIFIER  -> currentTool = Tools.VERTICAL_MIRROR_TOOL

        StringConstants.HORIZONTAL_MIRROR_TOOL_IDENTIFIER -> currentTool = Tools.HORIZONTAL_MIRROR_TOOL

        StringConstants.LINE_TOOL_IDENTIFIER -> currentTool = Tools.LINE_TOOL

        StringConstants.RECTANGLE_TOOL_IDENTIFIER -> currentTool = Tools.RECTANGLE_TOOL

        StringConstants.CLEAR_CANVAS_TOOL_IDENTIFIER  -> {
            showDialog(
                StringConstants.DIALOG_CLEAR_CANVAS_TITLE,
                StringConstants.DIALOG_CLEAR_CANVAS_MESSAGE,
                StringConstants.DIALOG_POSITIVE_BUTTON_TEXT,
                { _, _ ->
                    clearCanvas()
                }, StringConstants.DIALOG_NEGATIVE_BUTTON_TEXT, { _, _ -> }, null)
        }

        StringConstants.COLOR_PICKER_TOOL_IDENTIFIER -> currentTool = Tools.COLOR_PICKER_TOOL

        StringConstants.ERASE_TOOL_IDENTIFIER -> currentTool = Tools.ERASE_TOOL

        StringConstants.PALETTE_TOOL_IDENTIFIER -> {
            currentTool = Tools.PALETTE_TOOL
            selectedColorPalette?.let { extendedOnAddColorTapped(it) }
        }
    }
}