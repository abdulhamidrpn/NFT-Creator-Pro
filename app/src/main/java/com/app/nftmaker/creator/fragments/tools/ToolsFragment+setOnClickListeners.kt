package nft.nftcreator.pixel.pixelart.creator.fragments.tools

import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import nft.nftcreator.pixel.pixelart.R
import nft.nftcreator.pixel.pixelart.data.StringConstants
//TODO: SELECT COLOR PRIMARY IF ANY THING WRONG
fun ToolsFragment.getSelectedStateListPairData() = Pair(AppCompatResources.getColorStateList(context!!, R.color.dark_overlay), ContextCompat.getColorStateList(requireContext(), R.color.primary))
fun ToolsFragment.getUnselectedStateListPairData() = Pair(AppCompatResources.getColorStateList(context!!, android.R.color.transparent), ContextCompat.getColorStateList(requireContext(), R.color.bg_opposite))

fun ToolsFragment.setColorFor(it: View) {
    it.backgroundTintList = getSelectedStateListPairData().first
    (it as FloatingActionButton).supportImageTintList = getSelectedStateListPairData().second
}

fun ToolsFragment.unsetColorFor(it: View) {
    it.backgroundTintList = getUnselectedStateListPairData().first
    (it as FloatingActionButton).supportImageTintList =  getUnselectedStateListPairData().second
}

fun ToolsFragment.onOptionTapped(it: View) {
    currentlySelectedFAB?.let { it1 -> unsetColorFor(it1) }
    setColorFor(it)
    currentlySelectedFAB = it as FloatingActionButton
}

var currentlySelectedFAB: FloatingActionButton? = null

fun ToolsFragment.setOnClickListeners() {
    binding.apply {
        fragmentToolsPencilButton.setOnClickListener {
            onOptionTapped(it)
            caller.onToolTapped(StringConstants.PENCIL_TOOL_IDENTIFIER)
        }

        fragmentToolsFillButton.setOnClickListener {
            onOptionTapped(it)
            caller.onToolTapped(StringConstants.FILL_TOOL_IDENTIFIER)
        }

        fragmentToolsVerticalMirrorButton.setOnClickListener {
            onOptionTapped(it)
            caller.onToolTapped(StringConstants.VERTICAL_MIRROR_TOOL_IDENTIFIER)
        }

        fragmentToolsHorizontalMirrorButton.setOnClickListener {
            onOptionTapped(it)
            caller.onToolTapped(StringConstants.HORIZONTAL_MIRROR_TOOL_IDENTIFIER)
        }

        fragmentToolsLineButton.setOnClickListener {
            onOptionTapped(it)
            caller.onToolTapped(StringConstants.LINE_TOOL_IDENTIFIER)
        }

        fragmentToolsRectangleButton.setOnClickListener {
            onOptionTapped(it)
            caller.onToolTapped(StringConstants.RECTANGLE_TOOL_IDENTIFIER)
        }

        fragmentToolsResetCanvasButton.setOnClickListener {
            onOptionTapped(it)
            caller.onToolTapped(StringConstants.CLEAR_CANVAS_TOOL_IDENTIFIER)
        }

        fragmentToolsColorPickerButton.setOnClickListener {
            onOptionTapped(it)
            caller.onToolTapped(StringConstants.COLOR_PICKER_TOOL_IDENTIFIER)
        }

        fragmentToolsEraseButton.setOnClickListener {
            onOptionTapped(it)
            caller.onToolTapped(StringConstants.ERASE_TOOL_IDENTIFIER)
        }

        fragmentToolsColorPalette.setOnClickListener {
            onOptionTapped(it)
            caller.onToolTapped(StringConstants.PALETTE_TOOL_IDENTIFIER)
        }
    }
}