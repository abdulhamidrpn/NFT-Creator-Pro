package nft.nftcreator.pixel.pixelart.data

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {

    var prefs: SharedPreferences = context.getSharedPreferences("MAIN_PREF", Context.MODE_PRIVATE)

    /**
     * To save dialog permission state
     */
    fun setSpanCount(value: Int) {
        prefs.edit().putInt("SPAN_COUNT", value).apply()
    }

    fun getSpanCount(): Int {
        return prefs.getInt("SPAN_COUNT", 5)
    }

    fun setAdRemoved(value: Boolean) {
        prefs.edit().putBoolean("AD_REMOVED", value).apply()
    }

    fun getAdRemoved(): Boolean {
        return prefs.getBoolean("AD_REMOVED", false)
    }


    fun setExported(value: Boolean) {
        prefs.edit().putBoolean("EXPORTED", value).apply()
    }

    fun getExported(): Boolean {
        return prefs.getBoolean("EXPORTED", false)
    }

    fun setGridOn(value: Boolean) {
        prefs.edit().putBoolean("GRID_ON_OFF", value).apply()
    }

    fun getGridOn(): Boolean {
        return prefs.getBoolean("GRID_ON_OFF", true)
    }
    fun setTransparentOn(value: Boolean) {
        prefs.edit().putBoolean("TRANSPARENT_ON_OFF", value).apply()
    }

    fun getTransparentOn(): Boolean {
        return prefs.getBoolean("TRANSPARENT_ON_OFF", true)
    }

    /**
     * To save first launch
     */
    fun setFirstLaunch(value: Boolean) {
        prefs.edit().putBoolean("FIRST", value).apply()
    }

    fun getFirstLaunch(): Boolean {
        return prefs.getBoolean("FIRST", true)
    }

    /**
     * To save dialog permission state
     */
    fun setNeverAskAgain(key: String?, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getNeverAskAgain(key: String?): Boolean {
        return prefs.getBoolean(key, false)
    }

    // Preference for settings
    fun setPushNotification(value: Boolean) {
        prefs.edit().putBoolean("SETTINGS_PUSH_NOTIF", value).apply()
    }

    fun getPushNotification(): Boolean {
        return prefs.getBoolean("SETTINGS_PUSH_NOTIF", true)
    }

    fun setVibration(value: Boolean) {
        prefs.edit().putBoolean("SETTINGS_VIBRATION", value).apply()
    }

    fun getVibration(): Boolean {
        return prefs.getBoolean("SETTINGS_VIBRATION", true)
    }


    // Preference for first launch
    fun setIntersCounter(counter: Int) {
        prefs.edit().putInt("INTERS_COUNT", counter).apply()
    }

    fun getIntersCounter(): Int {
        return prefs.getInt("INTERS_COUNT", 0)
    }

    fun clearIntersCounter() {
        prefs.edit().putInt("INTERS_COUNT", 0).apply()
    }
}