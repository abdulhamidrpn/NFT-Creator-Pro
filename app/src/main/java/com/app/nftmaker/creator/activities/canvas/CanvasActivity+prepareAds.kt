package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import nft.nftcreator.pixel.pixelart.advertise.AdNetworkHelper
import nft.nftcreator.pixel.pixelart.data.AppConfig

fun CanvasActivity.prepareAds() {

    adNetworkHelper = AdNetworkHelper(this)
    adNetworkHelper.showGDPR()
    adNetworkHelper.loadBannerAd(AppConfig.ADS_CREATOR_BANNER)
    adNetworkHelper.loadInterstitialAd(AppConfig.ADS_MAIN_INTERS)
}