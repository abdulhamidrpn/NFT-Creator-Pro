package nft.nftcreator.pixel.pixelart.fragment

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import nft.nftcreator.pixel.pixelart.ActivityIntro
import nft.nftcreator.pixel.pixelart.creator.utility.FileHelperUtilities
import nft.nftcreator.pixel.pixelart.data.SharedPref
import nft.nftcreator.pixel.pixelart.inapp.InAppBilling
import nft.nftcreator.pixel.pixelart.utils.Tools
import nft.nftcreator.pixel.pixelart.R
import nft.nftcreator.pixel.pixelart.databinding.FragmentSettingBinding

class FragmentSetting : Fragment() {

    lateinit var binding: FragmentSettingBinding
    lateinit var sharedPref: SharedPref

    private var inAppBilling: InAppBilling? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        sharedPref = SharedPref(requireContext())

        if (sharedPref.getAdRemoved()) {
            binding.include.lytAdsRemover.visibility = View.GONE
        } else {
            binding.include.lytAdsRemover.visibility = View.VISIBLE
            removeAdPurchases()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun instance() = FragmentSetting()
    }

    override fun onResume() {
        super.onResume()
        initComponent()
    }

    fun removeAdPurchases() {

        Log.d("BuyTAG", "Load remove ad purchaese")
        inAppBilling = InAppBilling(requireActivity())
        inAppBilling?.setupBillingClient(requireContext())

    }

    fun billingLibrary() {
        Log.d("BuyTAG", "click billing library")
        inAppBilling?.loadBilling(requireActivity())
    }

    private fun initComponent() {
        binding.include.folderLocation.text =
            FileHelperUtilities(requireContext()).getDirectory()?.absolutePath
        binding.include.buildVersion.text = Tools.getVersionName(requireContext())

        binding.include.lytFolderLocation.setOnClickListener {
            Tools.openFolder(requireContext())
        }
        binding.include.lytAdsRemover.setOnClickListener {
            billingLibrary()
        }
        binding.include.lytIntroduction.setOnClickListener {
            ActivityIntro().navigate(requireActivity())
        }
        binding.include.lytAbout.setOnClickListener {
            Tools().showDialogProject(requireActivity())
        }
        binding.include.lytTnc.setOnClickListener {
            Tools.directLinkToBrowser(requireActivity(), getString(R.string.term_and_condition_url))
        }
        binding.include.lytRateApp.setOnClickListener {
            Tools.rateAction(requireActivity())
        }
        binding.include.lytMoreApps.setOnClickListener {
            Tools.directLinkToBrowser(requireActivity(), getString(R.string.more_app_url))
        }
        binding.include.lytContactUs.setOnClickListener {
            Tools.directLinkToBrowser(requireActivity(), getString(R.string.contact_us_url))
        }
    }
}