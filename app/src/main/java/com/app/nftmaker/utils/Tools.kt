package nft.nftcreator.pixel.pixelart.utils

import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.URLUtil
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import nft.nftcreator.pixel.pixelart.BuildConfig
import nft.nftcreator.pixel.pixelart.R
import nft.nftcreator.pixel.pixelart.creator.utility.FileHelperUtilities
import java.io.File


class Tools {
    companion object {

        fun setSystemBarColor(act: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = act.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.statusBarColor = Color.WHITE
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val view = act.findViewById<View>(android.R.id.content)
                var flags = view.systemUiVisibility
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                view.systemUiVisibility = flags
            }
        }

        fun needRequestPermission(): Boolean {
            return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
        }

        fun isNumber(input: String): Boolean {
            val integerChars = '0'..'9'
            var dotOccurred = 0
            return input.all { it in integerChars || it == '.' && dotOccurred++ < 1 }
        }

        fun showDialog(
            act: Activity?,
            dialogTitle: String,
            dialogMessage: String,
            dialogPositiveButtonText: String,
            dialogPositiveButtonAction: DialogInterface.OnClickListener,
            dialogNegativeButtonText: String?,
            dialogNegativeButtonAction: DialogInterface.OnClickListener?,
            view: View?
        ) {
            if (act != null) {
                MaterialAlertDialogBuilder(act)
                    .setTitle(dialogTitle)
                    .setMessage(dialogMessage)
                    .setView(view)
                    .setPositiveButton(dialogPositiveButtonText, dialogPositiveButtonAction)
                    .setNegativeButton(dialogNegativeButtonText, dialogNegativeButtonAction)
                    .show()
            }
        }

        fun openImage(context: Context, path: String) {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            val photoURI = FileProvider.getUriForFile(
                context, BuildConfig.APPLICATION_ID + ".provider",
                File(path)
            )
            intent.setDataAndType(photoURI, "image/*")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(Intent.createChooser(intent, "View using"))
        }

        fun getVersionName(ctx: Context): String? {
            return try {
                val manager = ctx.packageManager
                val info = manager.getPackageInfo(ctx.packageName, 0)
                ctx.getString(R.string.app_version) + " " + info.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                ctx.getString(R.string.version_unknown)
            }
        }

        fun rateAction(activity: Activity) {
            val uri = Uri.parse("market://details?id=" + activity.packageName)
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            try {
                activity.startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + activity.packageName)
                    )
                )
            }
        }

        fun directLinkToBrowser(activity: Activity, url: String?) {
            if (!URLUtil.isValidUrl(url)) {
                Toast.makeText(activity, "Ops, Cannot open url", Toast.LENGTH_LONG).show()
                return
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            activity.startActivity(intent)
        }

        fun dpToPx(c: Context, dp: Int): Int {
            val r = c.resources
            return Math.round(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dp.toFloat(),
                    r.displayMetrics
                )
            )
        }

        fun openFolder(c: Context) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            val selectedUri = Uri.parse(FileHelperUtilities(c).getDirectory()?.absolutePath)
            intent.setDataAndType(selectedUri, "image/*")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            c.startActivity(Intent.createChooser(intent, "Open folder"))
        }
    }

    fun showDialogProject(activity: Activity) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
        dialog.setContentView(R.layout.dialog_about)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.findViewById<View>(R.id.bt_close).setOnClickListener { dialog.dismiss() }
        dialog.findViewById<TextView>(R.id.version).text = getVersionName(activity)
        dialog.show()
    }


}