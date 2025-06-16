package nft.nftcreator.pixel.pixelart.creator.fragments.colorpicker

import android.graphics.Color
import nft.nftcreator.pixel.pixelart.databinding.FragmentColorPickerBinding
import nft.nftcreator.pixel.pixelart.creator.fragments.colorpicker.picker.ColorPickerPickerFragment
import nft.nftcreator.pixel.pixelart.creator.fragments.colorpicker.rgb.RGBColorPickerFragment
import nft.nftcreator.pixel.pixelart.creator.listeners.ColorPickerFragmentListener

var binding_: FragmentColorPickerBinding? = null

var oldColor_ = Color.BLACK
var colorPaletteMode_: Boolean = false

val binding get() = binding_!!

lateinit var caller: ColorPickerFragmentListener

var rgbFragmentInstance: RGBColorPickerFragment? = null
var pickerFragmentInstance: ColorPickerPickerFragment? = null
