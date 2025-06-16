package nft.nftcreator.pixel.pixelart.creator.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nft.nftcreator.pixel.pixelart.R
import nft.nftcreator.pixel.pixelart.creator.converters.JsonConverter
import nft.nftcreator.pixel.pixelart.creator.listeners.ColorPickerListener
import nft.nftcreator.pixel.pixelart.creator.models.ColorPalette
import nft.nftcreator.pixel.pixelart.creator.viewholders.ColorPickerViewHolder

class ColorPickerAdapter(private val data: ColorPalette, private val caller: ColorPickerListener?, private val isPaletteMode: Boolean = true, private val isPreviewMode: Boolean = false) : RecyclerView.Adapter<ColorPickerViewHolder>() {
    private var colorData = listOf<Int>()

    init {
        colorData = JsonConverter.convertJsonStringToListOfInt(data.colorPaletteColorData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorPickerViewHolder {
        return ColorPickerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_color_picker, parent, false) as View)
    }

    override fun onBindViewHolder(holder: ColorPickerViewHolder, position: Int) {
        holder.colorView.backgroundTintList = ColorStateList.valueOf(colorData[position])

        if (isPaletteMode && !isPreviewMode) {
            if (colorData[position] == Color.TRANSPARENT) {
                holder.colorView.setBackgroundResource(R.drawable.ic_baseline_add_24)
                holder.colorView.background.setColorFilter(
                    Color.GRAY,
                    PorterDuff.Mode.DST_OVER
                )
            }
        }

        holder.colorView.setOnClickListener {
            if (colorData[position] != Color.TRANSPARENT) {
                caller?.onColorTapped(colorData[position], it)
            } else {
                caller?.onColorAdded(data)
            }
        }

        holder.colorView.setOnLongClickListener {
            if (colorData[position] != Color.TRANSPARENT) {
                caller?.onColorLongTapped(data.objId, colorData[position], it)
            }
            true
        }


//        if (position == 0) {
//            caller?.onColorTapped(colorData[position], holder.colorView)
//        }
    }

    override fun getItemCount() = colorData.size
}