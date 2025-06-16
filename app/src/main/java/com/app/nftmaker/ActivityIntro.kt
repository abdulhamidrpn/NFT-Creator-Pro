package nft.nftcreator.pixel.pixelart

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import nft.nftcreator.pixel.pixelart.R
import nft.nftcreator.pixel.pixelart.databinding.ActivityIntroBinding
import nft.nftcreator.pixel.pixelart.data.SharedPref
import nft.nftcreator.pixel.pixelart.utils.Tools

class ActivityIntro : AppCompatActivity() {

    lateinit var binding: ActivityIntroBinding

    // activity transition
    fun navigate(activity: Activity) {
        val i = Intent(activity, ActivityIntro::class.java)
        activity.startActivity(i)
    }

    lateinit var myViewPagerAdapter: MyViewPagerAdapter
    lateinit var dots: Array<ImageView?>
    lateinit var aboutTitleArray: Array<String>
    lateinit var aboutDescriptionArray: Array<String>
    lateinit var aboutImagesArray: TypedArray
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Tools.setSystemBarColor(this)

        aboutTitleArray = resources.getStringArray(R.array.about_title_array)
        aboutDescriptionArray = resources.getStringArray(R.array.about_description_array)
        aboutImagesArray = resources.obtainTypedArray(R.array.about_images_array)

        // adding bottom dots
        addBottomDots()
        myViewPagerAdapter = MyViewPagerAdapter(this, aboutTitleArray.size)
        binding.viewPager.adapter = myViewPagerAdapter
        binding.viewPager.addOnPageChangeListener(viewPagerPageChangeListener)
        binding.btnNext.setOnClickListener { finish() }

        SharedPref(this).setFirstLaunch(false)
    }


    private fun addBottomDots() {
        dots = arrayOfNulls(aboutTitleArray.size)
        val widthHeight: Int = Tools.dpToPx(this, 7)
        binding.layoutDots.removeAllViews()
        for (i in dots.indices) {
            var width = widthHeight
            dots[i] = ImageView(this)
            if (currentPosition == i) {
                dots[i]?.setImageResource(R.drawable.indicator_round)
                width = widthHeight * 3
            } else {
                dots[i]?.setImageResource(R.drawable.indicator_circle_off)
            }
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(width, widthHeight))
            params.setMargins(15, 10, 15, 10)
            dots[i]?.layoutParams = params
            binding.layoutDots.addView(dots[i])
        }
    }

    //  viewpager change listener
    private var viewPagerPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            currentPosition = position
            addBottomDots()
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
        override fun onPageScrollStateChanged(arg0: Int) {}
    }

    /**
     * View pager adapter
     */
    class MyViewPagerAdapter(val activity: ActivityIntro, val size: Int) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val inflater = activity.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(R.layout.item_intro_slider, container, false)

            (view.findViewById<View>(R.id.title) as TextView).text =
                activity.aboutTitleArray[position]
            (view.findViewById<View>(R.id.description) as TextView).text =
                activity.aboutDescriptionArray[position]
            (view.findViewById<View>(R.id.image) as ImageView).setImageResource(
                activity.aboutImagesArray.getResourceId(position, -1)
            )
            container.addView(view)

            return view
        }

        override fun getCount(): Int {
            return activity.aboutTitleArray.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        }
    }


}
