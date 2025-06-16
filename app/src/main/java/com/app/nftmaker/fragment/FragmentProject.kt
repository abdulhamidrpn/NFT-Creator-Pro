package nft.nftcreator.pixel.pixelart.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import nft.nftcreator.pixel.pixelart.ActivityHome
import nft.nftcreator.pixel.pixelart.adapter.AdapterProject
import nft.nftcreator.pixel.pixelart.creator.activities.canvas.CanvasActivity
import nft.nftcreator.pixel.pixelart.creator.database.AppData
import nft.nftcreator.pixel.pixelart.creator.models.PixelArt
import nft.nftcreator.pixel.pixelart.data.StringConstants
import nft.nftcreator.pixel.pixelart.databinding.FragmentProjectBinding
import nft.nftcreator.pixel.pixelart.utils.Tools.Companion.showDialog

class FragmentProject : Fragment() {

    lateinit var binding: FragmentProjectBinding
    var query: String = ""

    companion object {
        @JvmStatic
        fun instance() = FragmentProject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProjectBinding.inflate(inflater, container, false)

        initComponent("")
        return binding.root
    }
    fun initComponent(q:String) {
        showProgress(true)
        query = q
        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(context, 2)
        }
        var data: MutableList<PixelArt?> = ArrayList()
        data.add(null)
        AppData.pixelArtDB.pixelArtCreationsDao().getAllPixelArtCreations(q).observe(viewLifecycleOwner) {
            var items = data
            if (it.isNotEmpty()) {
                items = it.toMutableList()
            }
            binding.recyclerView.adapter =
                AdapterProject(items, object : AdapterProject.AdapterListener {
                    override fun onClicked(creationTapped: PixelArt, pos: Int) {
                        onCreationTappedOverride(pos, creationTapped)
                    }

                    override fun onLongClicked(creationTapped: PixelArt, pos: Int) {
                        onCreationLongTappedOverride(pos, creationTapped)
                    }

                    override fun onNewClicked() {
                        (requireActivity() as ActivityHome).showDialogCreate()
                    }

                })

            showProgress(false)
        }
    }


    private fun onCreationTappedOverride(index:Int, creationTapped: PixelArt) {
        try {
            Log.d("AdNetworkHelper", "Clicked TappedOverride show ad")
            if((activity as ActivityHome).showInterstitialAd()) return
        } catch (e: Exception) { }

        startActivity(
            Intent(context, CanvasActivity::class.java)
                .putExtra(StringConstants.INDEX_EXTRA, index)
                .putExtra(StringConstants.PROJECT_TITLE_EXTRA, creationTapped.title))

    }


    private fun onCreationLongTappedOverride(index:Int, creationTapped: PixelArt) {
        (binding.recyclerView.adapter as AdapterProject).userHasLongPressed = true
        showDialog(
            activity,
            StringConstants.DIALOG_DELETE_PROJECT_TITLE,
            StringConstants.DIALOG_DELETE_PROJECT_MESSAGE + creationTapped.title + "?",
            StringConstants.DIALOG_POSITIVE_BUTTON_TEXT,
            { _, _ ->
                AppData.pixelArtDB.pixelArtCreationsDao().getAllPixelArtCreations(query).observe(this) {
                    AppData.pixelArtDB.pixelArtCreationsDao()
                        .deletePixelArtCreation(creationTapped.objId)
                    binding.recyclerView.adapter!!.notifyItemRemoved(it.indexOf(creationTapped))
                }
            }, StringConstants.DIALOG_NEGATIVE_BUTTON_TEXT, { _, _ -> }, null)

    }

    private fun showProgress(show: Boolean) {
        binding.progressLoading.visibility = if (show) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (show) View.GONE else View.VISIBLE
    }
}