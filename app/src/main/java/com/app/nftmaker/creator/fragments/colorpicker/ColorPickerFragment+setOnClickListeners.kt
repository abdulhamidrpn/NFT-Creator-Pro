package nft.nftcreator.pixel.pixelart.creator.fragments.colorpicker

import nft.nftcreator.pixel.pixelart.R
import nft.nftcreator.pixel.pixelart.creator.fragments.colorpicker.rgb.RGBColorPickerFragment

fun ColorPickerFragment.setOnClickListeners() {
    rgbFragmentInstance = RGBColorPickerFragment.newInstance()
    activity!!.supportFragmentManager.beginTransaction().add(R.id.fragmentColorPicker_tabLayoutFragmentHost, rgbFragmentInstance!!).commit()
}