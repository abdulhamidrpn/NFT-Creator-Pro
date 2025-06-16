package nft.nftcreator.pixel.pixelart.creator.fragments.canvas

import nft.nftcreator.pixel.pixelart.creator.customviews.pixelgridview.PixelGridView
import nft.nftcreator.pixel.pixelart.databinding.FragmentCanvasBinding
import nft.nftcreator.pixel.pixelart.creator.listeners.CanvasFragmentListener

var binding_: FragmentCanvasBinding? = null

val binding get() = binding_!!

lateinit var caller: CanvasFragmentListener

lateinit var myCanvasViewInstance: PixelGridView