package nft.nftcreator.pixel.pixelart.data

import android.app.Application
import android.content.Context
import com.app.nftmaker.advertise.AudienceNetworkInitializeHelper
import com.apps.mydairy.ad_manager.AppOpenManager
import com.google.android.gms.ads.MobileAds
import nft.nftcreator.pixel.pixelart.advertise.AdNetworkHelper
import nft.nftcreator.pixel.pixelart.creator.dao.PixelArtCreationsDao
import nft.nftcreator.pixel.pixelart.creator.database.AppData
import nft.nftcreator.pixel.pixelart.creator.database.ColorPalettesDatabase
import nft.nftcreator.pixel.pixelart.creator.database.PixelArtDatabase

class DataApp : Application() {

    private var appOpenAdManager: AppOpenManager? = null
    lateinit var sharedPref: SharedPref
    lateinit var dao: PixelArtCreationsDao

    override fun onCreate() {
        super.onCreate()
        sharedPref = SharedPref(this)

        AppData.pixelArtDB = PixelArtDatabase.getDatabase(this)
        AppData.colorPalettesDB = ColorPalettesDatabase.getDatabase(this)

        dao = PixelArtDatabase.getDatabase(this).pixelArtCreationsDao()
        setUpAd()
    }

    fun setUpAd() {
        MobileAds.initialize(this) { }
//        MobileAds.setAppMuted(true)
        appOpenAdManager = AppOpenManager(this)
    }
    private fun initAdNetwork(context: Context) {
        // Init ADS Admob
        AudienceNetworkInitializeHelper.initialize(this)
        AdNetworkHelper.init(this)
    }

}