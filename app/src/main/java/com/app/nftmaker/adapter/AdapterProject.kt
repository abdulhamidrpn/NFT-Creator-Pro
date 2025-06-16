package nft.nftcreator.pixel.pixelart.adapter

import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import nft.nftcreator.pixel.pixelart.R
import nft.nftcreator.pixel.pixelart.creator.*
import nft.nftcreator.pixel.pixelart.creator.converters.BitmapConverter
import nft.nftcreator.pixel.pixelart.creator.database.AppData
import nft.nftcreator.pixel.pixelart.creator.extensions.SnackbarDuration
import nft.nftcreator.pixel.pixelart.creator.extensions.showSnackbar
import nft.nftcreator.pixel.pixelart.creator.models.PixelArt
import nft.nftcreator.pixel.pixelart.databinding.ItemEmptyStateBinding
import nft.nftcreator.pixel.pixelart.databinding.ItemProjectBinding
import nft.nftcreator.pixel.pixelart.utils.TimeAgo
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class AdapterProject(private var data: List<PixelArt?>, private val listener: AdapterListener) : RecyclerView.Adapter<AdapterProject.ViewHolder>() {

    private val typeItem:Int = 100
    private val typeEmpty:Int = 200

    private lateinit var binding: ItemProjectBinding
    private lateinit var bindingEmpty: ItemEmptyStateBinding

    var userHasLongPressed = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == typeEmpty){
            bindingEmpty = ItemEmptyStateBinding.inflate(LayoutInflater.from(parent.context))
            return ViewHolder(bindingEmpty.root)
        } else {
            binding = ItemProjectBinding.inflate(LayoutInflater.from(parent.context))
            return ViewHolder(binding.root)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = data.forEach { _ ->
        if(getItemViewType(position) == typeEmpty) {
            bindingEmpty.layoutParent.setOnClickListener {
                if (!userHasLongPressed) listener.onNewClicked()
            }
        } else {
            binding.layoutParent.apply parent@{
                val item = data[position]

                binding.apply {

                    val executor: ExecutorService = Executors.newCachedThreadPool()
                    val handler = Handler(Looper.getMainLooper())
                    executor.execute {
                        //Background work here
                        handler.post {
                            image.setImageBitmap(BitmapConverter.convertStringToBitmap(item!!.coverBitmap))
                        }
                    }

                    subtitle.text = TimeAgo().get(context, item!!.dateCreated)

                    title.apply {
                        if (item.title.length > 15) {
                            ellipsize = TextUtils.TruncateAt.MARQUEE
                            isSelected = true
                            isSingleLine = true
                            text = (item.title + " ".repeat(10)).repeat(200)
                        } else {
                            text = item.title
                        }
                    }

                    this@parent.setOnClickListener {
                        if (!userHasLongPressed) listener.onClicked(item, position)
                    }

                    this@parent.setOnLongClickListener {
                        listener.onLongClicked(item, position)
                        true
                    }

                    changeStarredIndicator(buttonSaved, item)

                    buttonSaved.setOnClickListener {
                        if (item.starred) {
                            unFavouriteRecentCreation(this@parent, item)
                            AppData.pixelArtDB.pixelArtCreationsDao().updatePixelArtCreationStarred(false, item.objId)
                        } else {
                            favouriteRecentCreation(this@parent, item)
                            AppData.pixelArtDB.pixelArtCreationsDao().updatePixelArtCreationStarred(true, item.objId)
                        }
                        changeStarredIndicator((it as ImageView), item)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }

    private fun changeStarredIndicator(imageButton: ImageView, pixelArt: PixelArt) {
        imageButton.apply {
            if (pixelArt.starred) setImageResource(R.drawable.ic_bookmark)
            else setImageResource(R.drawable.ic_bookmark_border)
        }
    }
    private fun favouriteRecentCreation(contextView: View, pixelArt: PixelArt) {
        contextView.showSnackbar("Saved ${pixelArt.title} to saved items.", SnackbarDuration.DEFAULT)
        pixelArt.starred = true
    }

    private fun unFavouriteRecentCreation(contextView: View, pixelArt: PixelArt) {
        contextView.showSnackbar("You have removed ${pixelArt.title} from your saved items.", SnackbarDuration.DEFAULT)
        pixelArt.starred = false
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int): Int {
        return if(data[position] == null ) typeEmpty else typeItem
    }

    class ViewHolder(frame: View) : RecyclerView.ViewHolder(frame)

    interface AdapterListener {
        fun onClicked(obj: PixelArt, pos:Int)
        fun onLongClicked(obj: PixelArt, pos:Int)
        fun onNewClicked()
    }
}