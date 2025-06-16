package nft.nftcreator.pixel.pixelart.creator.fragments.colorpicker.rgb

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import nft.nftcreator.pixel.pixelart.R
import nft.nftcreator.pixel.pixelart.adapter.ColorsAdapter
import nft.nftcreator.pixel.pixelart.adapter.ItemClickListener
import nft.nftcreator.pixel.pixelart.creator.activities.canvas.showMenuItems
import nft.nftcreator.pixel.pixelart.creator.extensions.hideKeyboard
import nft.nftcreator.pixel.pixelart.creator.fragments.colorpicker.caller
import nft.nftcreator.pixel.pixelart.creator.fragments.colorpicker.colorPaletteMode_
import nft.nftcreator.pixel.pixelart.creator.utility.ColorConstants
import nft.nftcreator.pixel.pixelart.creator.utility.ColorConstants.colors
import nft.nftcreator.pixel.pixelart.creator.utility.LongConstants
import nft.nftcreator.pixel.pixelart.databinding.FragmentRgbColorPickerBinding

class RGBColorPickerFragment : Fragment(), ItemClickListener<String> {
    private fun setup() {
        binding.apply {
            picker.addOpacityBar(opacitybar)
            picker.addValueBar(valuebar)
            picker.setOnColorChangedListener {
                picker.setOldCenterColor(picker.color)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setOnClickListeners() {
        binding.btnColorReset.setOnClickListener {
            binding.opacitybar.opacity = 0
            binding.valuebar.setValue(0f)
        }

        binding.fragmentRGBColorPickerDoneButton.setOnClickListener {
            hideKeyboard()
            Handler(Looper.getMainLooper()).postDelayed({
                try {
                    caller.onDoneButtonPressed(binding.picker.color, colorPaletteMode_)
                } catch (exception: Exception) {

                } finally {
                    showMenuItems()
                }
            }, LongConstants.DEF_HANDLER_DELAY)
        }
        binding.fragmentRGBColorPickerButtonPresets.setOnClickListener {
            buttonSet(false)
            binding.rvColorPresets.adapter = colorsAdapter
            binding.rvColorPresets.layoutManager = GridLayoutManager(requireContext(), 5)
            colorsAdapter.updateDataSet(ColorConstants.colors)
            colorsAdapter.itemClickListener = this@RGBColorPickerFragment

        }
        binding.fragmentRGBColorPickerButtonPicker.setOnClickListener {
            buttonSet(true)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun buttonSet(picker: Boolean = true) {

        val backgroundTintListSelected =
            ColorStateList.valueOf(resources.getColor(R.color.bg_opposite))
        val backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.bg_hard))
        if (picker) {
            binding.cardPicker.visibility = View.VISIBLE
            binding.cardPresets.visibility = View.GONE
            binding.fragmentRGBColorPickerButtonPicker.backgroundTintList =
                backgroundTintListSelected
            binding.fragmentRGBColorPickerButtonPresets.backgroundTintList = backgroundTintList
            binding.fragmentRGBColorPickerButtonPicker.setTextColor(resources.getColor(R.color.tx_opposite))
            binding.fragmentRGBColorPickerButtonPresets.setTextColor(resources.getColor(R.color.tx_dark))
        } else {
            binding.cardPicker.visibility = View.GONE
            binding.cardPresets.visibility = View.VISIBLE
            binding.fragmentRGBColorPickerButtonPicker.backgroundTintList = backgroundTintList
            binding.fragmentRGBColorPickerButtonPresets.backgroundTintList =
                backgroundTintListSelected
            binding.fragmentRGBColorPickerButtonPicker.setTextColor(resources.getColor(R.color.tx_dark))
            binding.fragmentRGBColorPickerButtonPresets.setTextColor(resources.getColor(R.color.tx_opposite))
        }
    }


    val colorsAdapter = ColorsAdapter()

    companion object {
        fun newInstance() = RGBColorPickerFragment()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding_ = FragmentRgbColorPickerBinding.inflate(inflater, container, false)

        setOnClickListeners()
        setup()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding_ = null
    }

    override fun onItemClick(view: View, position: Int, item: String) {
        val colorSelected = Color.parseColor(item)
        hideKeyboard()
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                caller.onDoneButtonPressed(colorSelected, colorPaletteMode_)
            } catch (exception: Exception) {

            } finally {
                showMenuItems()
            }
        }, LongConstants.DEF_HANDLER_DELAY)
    }

}


/*
class RGBColorPickerFragment : Fragment() {
    private fun setup() {
        binding.apply {
            fragmentRGBColorPickerColorPreview.setBackgroundColor(oldColor_)

            val red = Color.red(oldColor_).toFloat()
            val green = Color.green(oldColor_).toFloat()
            val blue = Color.blue(oldColor_).toFloat()

            fragmentRGBColorPickerRedProgressBar.value = red
            fragmentRGBColorPickerGreenProgressBar.value = green
            fragmentRGBColorPickerBlueProgressBar.value = blue

            fragmentRGBColorPickerValueR.text = red.toInt().toString()
            fragmentRGBColorPickerValueG.text = green.toInt().toString()
            fragmentRGBColorPickerValueB.text = blue.toInt().toString()
        }
    }

    private fun updateColorPickerColorPreview() {
        binding.fragmentRGBColorPickerColorPreview.setBackgroundColor(Color.argb(255, valueR.toInt(), valueG.toInt(), valueB.toInt()))
    }

    private fun setOnChangeListeners() {
        binding.apply {
            fragmentRGBColorPickerRedProgressBar.addOnChangeListener { _, value, _ ->
                valueR = value
                updateColorPickerColorPreview()
                fragmentRGBColorPickerValueR.text = valueR.toInt().toString()
            }

            fragmentRGBColorPickerGreenProgressBar.addOnChangeListener { _, value, _ ->
                valueG = value
                updateColorPickerColorPreview()
                fragmentRGBColorPickerValueG.text = valueG.toInt().toString()
            }

            fragmentRGBColorPickerBlueProgressBar.addOnChangeListener { _, value, _ ->
                valueB = value
                updateColorPickerColorPreview()
                fragmentRGBColorPickerValueB.text = valueB.toInt().toString()
            }
        }
    }

    private fun setOnClickListeners() {
        binding.fragmentRGBColorPickerDoneButton.setOnClickListener {
            hideKeyboard()
            Handler(Looper.getMainLooper()).postDelayed({
                try {
                    caller.onDoneButtonPressed((binding.fragmentRGBColorPickerColorPreview.background as ColorDrawable).color, colorPaletteMode_)
                } catch (exception: Exception) {

                } finally {
                    showMenuItems()
                }
            }, LongConstants.DEF_HANDLER_DELAY)
        }
    }

    companion object {
        fun newInstance() = RGBColorPickerFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding_ = FragmentRgbColorPickerBinding.inflate(inflater, container, false)

        setOnClickListeners()
        setup()
        setOnChangeListeners()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding_ = null
    }
}*/