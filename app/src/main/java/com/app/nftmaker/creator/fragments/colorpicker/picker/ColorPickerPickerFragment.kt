package nft.nftcreator.pixel.pixelart.creator.fragments.colorpicker.picker

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import nft.nftcreator.pixel.pixelart.R
import nft.nftcreator.pixel.pixelart.creator.activities.canvas.showMenuItems
import nft.nftcreator.pixel.pixelart.creator.customviews.colorpickerview.colorPickerColorSelected
import nft.nftcreator.pixel.pixelart.databinding.FragmentColorPickerPickerBinding
import nft.nftcreator.pixel.pixelart.creator.extensions.hideKeyboard
import nft.nftcreator.pixel.pixelart.creator.fragments.colorpicker.caller
import nft.nftcreator.pixel.pixelart.creator.fragments.colorpicker.colorPaletteMode_
import nft.nftcreator.pixel.pixelart.creator.fragments.colorpicker.oldColor_
import nft.nftcreator.pixel.pixelart.creator.fragments.colorpicker.picker.canvas.ColorPickerPickerCanvasFragment
import nft.nftcreator.pixel.pixelart.creator.utility.LongConstants


class ColorPickerPickerFragment : Fragment(), View.OnTouchListener {
    private fun setup() {
        binding.fragmentColorPickerPickerColorPreview.setBackgroundColor(oldColor_)
    }

    private fun updatePreview() {
        binding.fragmentColorPickerPickerColorPreview.setBackgroundColor(colorPickerColorSelected)
    }

    private fun setOnClickListeners() {
        binding.fragmentColorPickerPickerDoneButton.setOnClickListener {
            hideKeyboard()
            Handler(Looper.getMainLooper()).postDelayed({
                try {
                    caller.onDoneButtonPressed((binding.fragmentColorPickerPickerColorPreview.background as ColorDrawable).color, colorPaletteMode_)
                } catch (exception: Exception) {

                } finally {
                    showMenuItems()
                }
            },  LongConstants.DEF_HANDLER_DELAY)
        }
    }


    companion object {
        fun newInstance() = ColorPickerPickerFragment()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding_ = FragmentColorPickerPickerBinding.inflate(inflater, container, false)

        setup()
        setOnClickListeners()
        requireActivity().supportFragmentManager.beginTransaction().add(R.id.fragmentColorPickerPicker_canvasFragmentHost, ColorPickerPickerCanvasFragment.newInstance()).commit()

        binding.fragmentColorPickerPickerCanvasFragmentHost.setOnTouchListener(this)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding_ = null
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        val coordinateX = event.x.toInt()
        val coordinateY = event.y.toInt()

        val bitmap = v!!.drawToBitmap()

        return when (event.actionMasked) {
            MotionEvent.ACTION_MOVE -> {
                if (coordinateX in 0 until bitmap.width && coordinateY in 0 until bitmap.height) {
                    colorPickerColorSelected = bitmap.getPixel(coordinateX, coordinateY)
                    updatePreview()
                    true
                } else false
            }
            MotionEvent.ACTION_DOWN -> {
                if (coordinateX in 0 until bitmap.width && coordinateY in 0 until bitmap.height) {
                    colorPickerColorSelected = bitmap.getPixel(coordinateX, coordinateY)
                    updatePreview()
                    true
                } else false
            }
            else -> {
                false
            }
        }
    }
}