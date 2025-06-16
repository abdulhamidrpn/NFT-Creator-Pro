package nft.nftcreator.pixel.pixelart.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import nft.nftcreator.pixel.pixelart.R
import com.google.android.material.imageview.ShapeableImageView

interface ItemClickListener<T> {
    fun onItemClick(view: View, position: Int, item: T)
}

class ColorsAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val colors = mutableListOf<String>()
    var itemClickListener: ItemClickListener<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_color, null)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val colorString = if (!colors.isNullOrEmpty()) getItem(position) else "#f84c44"
        (holder as ViewHolder).setColor(colorString)

    }


    override fun getItemCount() = colors.size

    fun getItem(position: Int): String {
        return colors[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataSet(newList: List<String>) {
        colors.clear()
        colors.addAll(newList.toMutableList())
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        lateinit var container: ConstraintLayout
        lateinit var circularImage: ShapeableImageView

        @SuppressLint("ResourceType")
        fun setColor(colorString: String) {

            container = view.findViewById(R.id.container)
            circularImage = view.findViewById(R.id.circle_image)
            container.setOnClickListener(this@ViewHolder)
//            val c = Color.parseColor(colorString)
            circularImage.setBackgroundColor(Color.parseColor(colorString))
        }

        override fun onClick(view: View) {
            if (itemClickListener != null)
                when (view.id) {
                    R.id.container -> itemClickListener!!.onItemClick(
                        view,
                        adapterPosition,
                        getItem(adapterPosition)
                    )
                }
        }
    }


}