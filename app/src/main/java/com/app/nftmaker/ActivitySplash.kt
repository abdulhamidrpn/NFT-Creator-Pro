package nft.nftcreator.pixel.pixelart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import nft.nftcreator.pixel.pixelart.databinding.ActivitySplashBinding
import nft.nftcreator.pixel.pixelart.utils.Tools

class ActivitySplash : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Tools.setSystemBarColor(this)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ActivityHome::class.java)
            startActivity(intent)
            finish()
        }, 800)
    }
}