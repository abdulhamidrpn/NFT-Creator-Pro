package nft.nftcreator.pixel.pixelart

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import nft.nftcreator.pixel.pixelart.advertise.AdNetworkHelper
import nft.nftcreator.pixel.pixelart.creator.activities.canvas.CanvasActivity
import nft.nftcreator.pixel.pixelart.creator.database.AppData
import nft.nftcreator.pixel.pixelart.creator.utility.FileHelperUtilities
import nft.nftcreator.pixel.pixelart.data.AppConfig
import nft.nftcreator.pixel.pixelart.data.SharedPref
import nft.nftcreator.pixel.pixelart.data.StringConstants
import nft.nftcreator.pixel.pixelart.databinding.ActivityHomeBinding
import nft.nftcreator.pixel.pixelart.databinding.DialogCreateProjectBinding
import nft.nftcreator.pixel.pixelart.fragment.FragmentExported
import nft.nftcreator.pixel.pixelart.fragment.FragmentProject
import nft.nftcreator.pixel.pixelart.fragment.FragmentSaved
import nft.nftcreator.pixel.pixelart.fragment.FragmentSetting
import nft.nftcreator.pixel.pixelart.inapp.InAppBilling
import nft.nftcreator.pixel.pixelart.inapp.enableAd
import nft.nftcreator.pixel.pixelart.model.MenuView
import nft.nftcreator.pixel.pixelart.utils.Tools
import kotlin.math.abs


class ActivityHome : AppCompatActivity() {

    private var nextIntent: Intent? = null
    private var menuViews = hashMapOf<Int, MenuView>()
    private var menuSelected: MenuView? = null

    lateinit var binding: ActivityHomeBinding
    lateinit var sharedPref: SharedPref
    var myActionMenuItem: MenuItem? = null

    private var inAppBilling: InAppBilling? = null

    private var mInterstitialAd: InterstitialAd? = null
    val TAG = "TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPref = SharedPref(this)

        initComponent()
        initToolbar()
        initDrawerMenu()
        onNavigationMenuClick(findViewById(R.id.menu_project))

        SetupInterstitialAd()

        if (sharedPref.getFirstLaunch()) {
            ActivityIntro().navigate(this)
        }
        //TODO: uncomment to prepare ads
        prepareAds()

        enableAd.observe(this, Observer {
            Log.d("AdNetworkHelper", "Main Enable observer Ad $it remove ads - sharedPref.getAdRemoved() ${sharedPref.getAdRemoved()}")
            if (it || sharedPref.getAdRemoved()) {
                binding.include.lytAdsRemover.visibility = View.GONE
                removeAds()
            } else {
                prepareAds()
                binding.include.lytAdsRemover.visibility = View.VISIBLE
                removeAdPurchases()
            }
        })

        if (sharedPref.getAdRemoved()) {
            binding.include.lytAdsRemover.visibility = View.GONE
            Log.d("AdNetworkHelper", "Main Enable sharedPref.getAdRemoved() ${sharedPref.getAdRemoved()} remove ads")
            removeAds()
        } else {
            Log.d("BuyTAG", "getAdRemoved Ad false")
            prepareAds()
            binding.include.lytAdsRemover.visibility = View.VISIBLE
            removeAdPurchases()
        }
    }

    fun removeAdPurchases() {

        Log.d("BuyTAG", "Load remove ad purchaese")
        inAppBilling = InAppBilling(this)
        inAppBilling?.setupBillingClient(applicationContext)


    }

    fun billingLibrary() {
        Log.d("BuyTAG", "click billing library")
        inAppBilling?.loadBilling(this)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun initComponent() {

        val menuHome = MenuView(
            R.string.menu_home,
            R.id.menu_project,
            R.id.menu_project_img,
            R.id.frame_container_home,
            FragmentProject.instance()
        )
        val menuExported = MenuView(
            R.string.menu_exported,
            R.id.menu_exported,
            R.id.menu_exported_img,
            R.id.frame_container_exported,
            FragmentExported.instance()
        )
        val menuAdd = MenuView(R.string.menu_add, R.id.menu_add, R.id.menu_add_strip, -1, null)
        val menuSaved = MenuView(
            R.string.menu_saved,
            R.id.menu_saved,
            R.id.menu_saved_img,
            R.id.frame_container_saved,
            FragmentSaved.instance()
        )
        val menuSetting = MenuView(
            R.string.menu_setting,
            R.id.menu_setting,
            R.id.menu_setting_img,
            R.id.frame_container_setting,
            FragmentSetting.instance()
        )

        menuViews[R.id.menu_project] = menuHome
        menuViews[R.id.menu_exported] = menuExported
        menuViews[R.id.menu_add] = menuAdd
        menuViews[R.id.menu_saved] = menuSaved
        menuViews[R.id.menu_setting] = menuSetting

        binding.appbarLayout.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout: AppBarLayout, verticalOffset: Int ->
            val offset = abs(verticalOffset)
            val range = appBarLayout.totalScrollRange
            val heightMax = binding.lytBarContent.height
            if (range <= 0) return@OnOffsetChangedListener;
            val translationY = offset * heightMax / range
            binding.lytBar.translationY = translationY.toFloat()
        })
    }

    fun onNavigationMenuClick(view: View) {
        val menu = menuViews[view.id]
        if (menuSelected?.parent == menu?.parent) return

        if (menu?.parent == R.id.menu_add) {
            showDialogCreate()
            return
        }

        val image = findViewById<ImageView>(menu?.image!!)
        image.colorFilter = PorterDuffColorFilter(
            ContextCompat.getColor(this, R.color.primary),
            PorterDuff.Mode.SRC_IN
        )
        val frameLayout = findViewById<FrameLayout>(menu.frame)
        frameLayout.visibility = View.VISIBLE

        binding.toolbar.title = getString(menu.title)

        when {
            supportFragmentManager.findFragmentById(menu.frame) == null -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(menu.frame, menu.fragment!!)
                transaction.commit()
            }
            menu.parent == R.id.menu_exported -> {
                (menu.fragment as FragmentExported?)!!.onResume()
            }
            menu.parent == R.id.menu_setting -> {
                (menu.fragment as FragmentSetting?)!!.onResume()
            }
        }
        if (menuSelected != null) {
            (findViewById<View>(menuSelected!!.image) as ImageView).setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.ic_soft
                ), PorterDuff.Mode.SRC_IN
            )
            findViewById<View>(menuSelected!!.frame).visibility = View.GONE
        }
        menuSelected = menu
        myActionMenuItem?.collapseActionView()
    }

    private fun initDrawerMenu() {
        binding.include.folderLocation.text = FileHelperUtilities(this).getDirectory()?.absolutePath
        binding.include.buildVersion.text = Tools.getVersionName(this)

        binding.include.lytFolderLocation.setOnClickListener {
            Tools.openFolder(this)
        }

        binding.include.lytIntroduction.setOnClickListener {
            ActivityIntro().navigate(this)
        }
        binding.include.lytAdsRemover.setOnClickListener {
            billingLibrary()
        }
        binding.include.lytAbout.setOnClickListener {
            Tools().showDialogProject(this)
        }
        binding.include.lytTnc.setOnClickListener {
            Tools.directLinkToBrowser(this, getString(R.string.term_and_condition_url))
        }
        binding.include.lytRateApp.setOnClickListener {
            Tools.rateAction(this)
        }
        binding.include.lytMoreApps.setOnClickListener {
            Tools.directLinkToBrowser(this, getString(R.string.more_app_url))
        }
        binding.include.lytContactUs.setOnClickListener {
            Tools.directLinkToBrowser(this, getString(R.string.contact_us_url))
        }
    }

    fun showDialogCreate() {
        var bindingView = DialogCreateProjectBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        var count: Int = AppData.pixelArtDB.pixelArtCreationsDao().countPixelArt()
        count++
        bindingView.name.setText("Untitled-" + count)
        bindingView.dimen.setText("12")
        dialog.setCancelable(true)
        dialog.setContentView(bindingView.root)
        bindingView.dimen.visibility = View.GONE
        val radios = arrayOf(
            bindingView.radio12x,
            bindingView.radio16x,
            bindingView.radio24x,
            bindingView.radio32x,
            bindingView.radio48x,
            bindingView.radio64x,
            bindingView.radioCustom
        )
        bindingView.radio12x.isChecked = true;
        var spanCount: Int = 12
        var selectedRadio = bindingView.radio12x
        for (r in radios) {
            r.setOnClickListener {
                selectedRadio.isChecked = false
                selectedRadio = r
                spanCount = Integer.parseInt(r.tag.toString())
                if (r == bindingView.radioCustom) {
                    bindingView.dimen.visibility = View.VISIBLE
                } else {
                    bindingView.dimen.visibility = View.GONE
                }
            }

        }
        bindingView.btnCreate.setOnClickListener {
            if (selectedRadio == bindingView.radioCustom) {
                var countStr: String = bindingView.dimen.text.toString()
                if (!Tools.isNumber(countStr)) return@setOnClickListener
                spanCount = Integer.parseInt(countStr)
            }
            dialog.dismiss()
            nextIntent = Intent(this, CanvasActivity::class.java)
                    .putExtra(StringConstants.SPAN_COUNT_EXTRA, spanCount)
                    .putExtra(StringConstants.PROJECT_TITLE_EXTRA, bindingView.name.text.toString())
            startAnotherActivity()

        }
        dialog.show()
    }




    private fun SetupInterstitialAd() {

        requestNewInterstitial()
    }

    private fun startAnotherActivity(showAd: Boolean = true) {
        if (enableAd.value == false && sharedPref.getAdRemoved() == false && mInterstitialAd != null && showAd == true) {
            mInterstitialAd?.show(this@ActivityHome)
        } else {
            startActivity(nextIntent)
        }
    }

    private fun requestNewInterstitial() {
        MobileAds.initialize(this) { }
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, getString(R.string.interstitial_app_id), adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd
                    Log.i(TAG, "onAdLoaded")


                    mInterstitialAd?.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdClicked() {
                                // Called when a click is recorded for an ad.
                                Log.d(TAG, "Ad was clicked.")
                            }

                            override fun onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad dismissed fullscreen content.")
                                mInterstitialAd = null
                                startAnotherActivity(false)
                                requestNewInterstitial()
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                // Called when ad fails to show.
                                Log.e(TAG, "Ad failed to show fullscreen content.")
                                mInterstitialAd = null
                            }

                            override fun onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Log.d(TAG, "Ad recorded an impression.")
                            }

                            override fun onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad showed fullscreen content.")
                            }
                        }


                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.d(TAG, "onAdFailedToLoad " + loadAdError.toString())
                    mInterstitialAd = null
                }
            })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)

        myActionMenuItem = menu?.findItem(R.id.action_search)
        var searchView = myActionMenuItem?.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                (menuSelected?.fragment as FragmentProject).initComponent(query)
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
        myActionMenuItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                if (menuSelected?.parent != R.id.menu_project) onNavigationMenuClick(findViewById(R.id.menu_project))
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                if (menuSelected?.parent == R.id.menu_project && myActionMenuItem!!.isActionViewExpanded) {
                    (menuSelected?.fragment as FragmentProject).initComponent("")
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    private lateinit var adNetworkHelper: AdNetworkHelper

    private fun prepareAds() {
        adNetworkHelper = AdNetworkHelper(this)
        adNetworkHelper.showGDPR()
        adNetworkHelper.loadBannerAd(AppConfig.ADS_MAIN_BANNER)
        adNetworkHelper.loadInterstitialAd(AppConfig.ADS_MAIN_INTERS)
    }

    private fun removeAds() {
        adNetworkHelper = AdNetworkHelper(this)
        adNetworkHelper.loadBannerAd(false)
        adNetworkHelper.loadInterstitialAd(false)
    }

    fun showInterstitialAd(): Boolean {
        return adNetworkHelper.showInterstitialAd(AppConfig.ADS_MAIN_INTERS)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        doExitApp()
    }

    private var exitTime: Long = 0

    fun doExitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, R.string.press_again_exit_app, Toast.LENGTH_SHORT).show()
            exitTime = System.currentTimeMillis()
        } else {
            finish()
        }
    }
}