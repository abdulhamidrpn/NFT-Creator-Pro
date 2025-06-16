package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.interstitial.InterstitialAd
import nft.nftcreator.pixel.pixelart.R
import nft.nftcreator.pixel.pixelart.advertise.AdNetworkHelper
import nft.nftcreator.pixel.pixelart.creator.adapters.ColorPickerAdapter
import nft.nftcreator.pixel.pixelart.databinding.ActivityCanvasBinding
import nft.nftcreator.pixel.pixelart.creator.fragments.colorpicker.ColorPickerFragment
import nft.nftcreator.pixel.pixelart.creator.fragments.outercanvas.OuterCanvasFragment
import nft.nftcreator.pixel.pixelart.creator.fragments.tools.ToolsFragment
import nft.nftcreator.pixel.pixelart.creator.models.ColorPalette
import nft.nftcreator.pixel.pixelart.creator.models.PixelArt
import nft.nftcreator.pixel.pixelart.data.SharedPref

lateinit var binding: ActivityCanvasBinding
var index: Int? = null

var primaryColor: Int = Color.BLACK
var secondaryColor: Int = Color.BLUE
var spanCount = 5
var isPrimaryColorSelected = true

var isSelected = false
var background: Drawable? = null

var currentFragmentInstance: Fragment? = null

var sharedPref: SharedPref? = null
var TAG = "TAG"

lateinit var currentPixelArtObj: PixelArt

lateinit var adNetworkHelper: AdNetworkHelper

var mInterstitialAd: InterstitialAd? = null

enum class Tools {
    PENCIL_TOOL,
    FILL_TOOL,
    HORIZONTAL_MIRROR_TOOL,
    VERTICAL_MIRROR_TOOL,
    LINE_TOOL,
    RECTANGLE_TOOL,
    OUTLINED_RECTANGLE_TOOL,
    COLOR_PICKER_TOOL,
    ERASE_TOOL,
    PALETTE_TOOL
}

var currentTool: Tools = Tools.PENCIL_TOOL

var saved = true

lateinit var colorPickerFragmentInstance: ColorPickerFragment
lateinit var outerCanvasInstance: OuterCanvasFragment

var colorPickerAdapter : ColorPickerAdapter? = null
var selectedColorPalette: ColorPalette? = null

lateinit var menu: Menu

lateinit var transparentMenuItem:MenuItem
var toolsFragmentInstance: ToolsFragment? = null

var lineMode_hasLetGo = false
var rectangleMode_hasLetGo = false

var projectTitle: String? = null

lateinit var sharedPreferenceObject: SharedPreferences


