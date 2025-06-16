package nft.nftcreator.pixel.pixelart.utils

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import nft.nftcreator.pixel.pixelart.R
import java.util.ArrayList

class PermissionUtil {

    val STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE

    /* Permission required for application */
    val PERMISSION_ALL = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE // add more permission here
    )

    /* Permission required for application */
    val PERMISSION_STORAGE = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    fun showDialog(act: AppCompatActivity) {
        val builder = AlertDialog.Builder(act)
        builder.setTitle(act.getString(R.string.dialog_title_permission))
        builder.setMessage(act.getString(R.string.dialog_content_permission))
        builder.setPositiveButton(
            "SETTINGS"
        ) { dialogInterface, i -> goToPermissionSettingScreen(act) }
        builder.setNegativeButton("CANCEL", null)
        builder.show()
    }

    fun goToPermissionSettingScreen(activity: Activity) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", activity.packageName, null)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }

    fun isAllPermissionGranted(activity: Activity?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = PERMISSION_ALL
            if (permission.size == 0) return false
            for (s in permission) {
                if (ActivityCompat.checkSelfPermission(
                        activity!!,
                        s
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    fun getDeniedPermission(act: Activity): Array<String>? {
        val permissions: MutableList<String> = ArrayList()
        for (i in PERMISSION_ALL.indices) {
            val status = act.checkSelfPermission(PERMISSION_ALL[i])
            if (status != PackageManager.PERMISSION_GRANTED) {
                permissions.add(PERMISSION_ALL[i])
            }
        }
        return permissions.toTypedArray()
    }


    fun isGranted(act: Activity, permission: String?): Boolean {
        return if (!Tools.needRequestPermission()) true else act.checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
    }

    fun isStorageGranted(act: Activity): Boolean {
        return isGranted(act, STORAGE)
    }

    fun showSystemDialogPermission(fragment: Fragment, perm: String) {
        fragment.requestPermissions(arrayOf(perm), 200)
    }

    fun showSystemDialogPermission(act: Activity, perm: String) {
        act.requestPermissions(arrayOf(perm), 200)
    }

    fun showSystemDialogPermission(act: Activity, perm: String, code: Int) {
        act.requestPermissions(arrayOf(perm), code)
    }
}