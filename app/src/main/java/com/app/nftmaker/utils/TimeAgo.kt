package nft.nftcreator.pixel.pixelart.utils

import android.content.Context
import nft.nftcreator.pixel.pixelart.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TimeAgo {

    private val SECOND_MILLIS = 1000
    private val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private val DAY_MILLIS = 24 * HOUR_MILLIS

    operator fun get(ctx: Context, datetime: String?): String? {
        return try {
            val date = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).parse(datetime)
            get(ctx, date.time)
        } catch (e: ParseException) {
            e.printStackTrace()
            datetime
        }
    }

    operator fun get(ctx: Context, time: Long): String? {
        var time = time
        if (time < 1000000000000L) {
            time *= 1000
        }
        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return null
        }
        val diff = now - time
        return if (diff < MINUTE_MILLIS) {
            ctx.getString(R.string.just_now)
        } else if (diff < 2 * MINUTE_MILLIS) {
            ctx.getString(R.string.a_minute_ago)
        } else if (diff < 50 * MINUTE_MILLIS) {
            (diff / MINUTE_MILLIS).toString() + " " + ctx.getString(R.string.minutes_ago)
        } else if (diff < 90 * MINUTE_MILLIS) {
            ctx.getString(R.string.an_hour_ago)
        } else if (diff < 24 * HOUR_MILLIS) {
            (diff / HOUR_MILLIS).toString() + " " + ctx.getString(R.string.hours_ago)
        } else if (diff < 48 * HOUR_MILLIS) {
            ctx.getString(R.string.yesterday)
        } else {
            if (diff / DAY_MILLIS >= 5) {
                val newFormat = SimpleDateFormat("dd MMM yy")
                newFormat.format(Date(time))
            } else {
                (diff / DAY_MILLIS).toString() + " " + ctx.getString(R.string.days_ago)
            }
        }
    }

}