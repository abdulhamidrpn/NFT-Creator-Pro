package nft.nftcreator.pixel.pixelart.creator.fragments.outercanvas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import nft.nftcreator.pixel.pixelart.R
import nft.nftcreator.pixel.pixelart.creator.activities.canvas.sharedPref
import nft.nftcreator.pixel.pixelart.creator.customviews.transparentbackgroundview.GridLines
import nft.nftcreator.pixel.pixelart.creator.customviews.transparentbackgroundview.PixelGridView
import nft.nftcreator.pixel.pixelart.creator.customviews.transparentbackgroundview.PixelGridViewx
import nft.nftcreator.pixel.pixelart.creator.customviews.transparentbackgroundview.TransparentBackgroundView
import nft.nftcreator.pixel.pixelart.creator.fragments.canvas.CanvasFragment
import nft.nftcreator.pixel.pixelart.creator.utility.IntConstants
import nft.nftcreator.pixel.pixelart.databinding.FragmentOuterCanvasBinding


class OuterCanvasFragment(val spanCount: Int, private val isEmpty: Boolean = false) : Fragment() {
    lateinit var canvasFragment: CanvasFragment
    lateinit var cardViewParent: View
    lateinit var fragmentHost: View

    private fun instantiateVariables() {
        cardViewParent = binding.fragmentOuterCanvasCanvasFragmentHostCardViewParent
        fragmentHost = binding.fragmentOuterCanvasCanvasFragmentHost
        canvasFragment = CanvasFragment.newInstance(spanCount, isEmpty)
    }

    private fun showCanvas() {
        val transparent = TransparentBackgroundView(requireContext(), spanCount)
        sharedPref?.setSpanCount(spanCount)
        binding.defsq2.addView(transparent)
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.fragmentOuterCanvas_canvasFragmentHost, canvasFragment).commit()
    }

    fun showGrid() {
        val pixelGrid = PixelGridView(requireContext())
        pixelGrid.setNumColumns(spanCount)
        pixelGrid.setNumRows(spanCount)
        binding.defsq3.addView(pixelGrid)
        val isGridOn = sharedPref?.getGridOn() ?: true
        if (isGridOn)
            binding.defsq3.visibility = View.VISIBLE
        else
            binding.defsq3.visibility = View.GONE

    }

    fun showTransparent() {
        val transparent =
            sharedPref?.getSpanCount()?.let { TransparentBackgroundView(requireContext(), it) }
        binding.defsq2.addView(transparent)
    }

    fun getCurrentRotation(): Float {
        return binding.fragmentOuterCanvasCanvasFragmentHostCardViewParent.rotation
    }

    fun rotate(
        by: Int = IntConstants.DEGREES_NINETY,
        animate: Boolean = true,
        clockwise: Boolean = true
    ) {
        val rotationAmount = if (clockwise) {
            (getCurrentRotation() + by)
        } else {
            (getCurrentRotation() - by)
        }

        if (animate) {
            binding.fragmentOuterCanvasCanvasFragmentHostCardViewParent
                .animate()
                .rotation(rotationAmount)
        } else {
            binding.fragmentOuterCanvasCanvasFragmentHostCardViewParent.rotation = rotationAmount
        }
    }

    //    @RequiresApi(Build.VERSION_CODES.Q)
    fun flip(
        by: Int = IntConstants.DEGREES_ONE_EIGHTY,
        animate: Boolean = true,
        clockwise: Boolean = true
    ) {
        val rotationAmount = if (clockwise) {
            (getCurrentRotation() + by)
        } else {
            (getCurrentRotation() - by)
        }

        if (animate) {
            binding.fragmentOuterCanvasCanvasFragmentHostCardViewParent
                .animate()
                .rotationX(rotationAmount)
        } else {
            binding.fragmentOuterCanvasCanvasFragmentHostCardViewParent.rotationX = rotationAmount
        }
    }

    companion object {
        fun newInstance(spanCount: Int, isEmpty: Boolean = false) =
            OuterCanvasFragment(spanCount, isEmpty)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding_ = FragmentOuterCanvasBinding.inflate(inflater, container, false)

        instantiateVariables()
        showCanvas()

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding_ = null
    }
}