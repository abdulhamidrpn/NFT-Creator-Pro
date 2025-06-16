package nft.nftcreator.pixel.pixelart.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.FrameLayout
import nft.nftcreator.pixel.pixelart.creator.*
import nft.nftcreator.pixel.pixelart.databinding.ItemExportedBinding

class AdapterExported(private val data: List<String>, private val listener: AdapterListener) : RecyclerView.Adapter<AdapterExported.ViewHolder>() {

    private lateinit var binding: ItemExportedBinding

    var userHasLongPressed = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemExportedBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = data.forEach { _ ->
        binding.layoutParent.apply parent@{
            val item = data[position]

            binding.apply {
                image.setImageURI(Uri.parse(item))

                this@parent.setOnClickListener {
                    if (!userHasLongPressed) listener.onClicked(item, position)
                }

                this@parent.setOnLongClickListener {
                    listener.onLongClicked(item, position)
                    true
                }
            }
        }
    }

    override fun getItemCount() = data.size

    class ViewHolder(frame: FrameLayout) : RecyclerView.ViewHolder(frame)

    interface AdapterListener {
        fun onClicked(obj: String, pos:Int)
        fun onLongClicked(obj: String, pos:Int)
    }
}