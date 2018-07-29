package com.involvd.sdk.data

import android.content.Context
import android.text.TextUtils
import com.robj.radicallyreusable.base.utils.PrefUtils

object PrefManager {

    const val UNIQUE_ID = "UNIQUE_ID"
    const val LAST_BUG_LIST_TIME = "LAST_BUG_LIST_TIME"
    const val LAST_FEATURE_LIST_TIME = "LAST_FEATURE_LIST_TIME"

    @JvmStatic
    fun getUniqueId(context: Context): String? {
        return PrefUtils.readStringPref(context, UNIQUE_ID)
    }

    @JvmStatic
    fun setUniqueId(context: Context, submitteeId: String?) {
        if(TextUtils.isEmpty(submitteeId)) {
            val prefs = Array(1) { submitteeId }
            PrefUtils.removePrefs(context, prefs)
            return
        }
        PrefUtils.writeStringPref(context, UNIQUE_ID, submitteeId)
    }

    fun setBugListTime(context: Context) {
        PrefUtils.writeLongPref(context, LAST_BUG_LIST_TIME, System.currentTimeMillis())
    }

    fun getBugListTime(context: Context) : Long {
        return PrefUtils.readLongPref(context, LAST_BUG_LIST_TIME)
    }

    fun setFeatureListTime(context: Context) {
        PrefUtils.writeLongPref(context, LAST_FEATURE_LIST_TIME, System.currentTimeMillis())
    }

    fun getFeatureListTime(context: Context) : Long {
        return PrefUtils.readLongPref(context, LAST_FEATURE_LIST_TIME)
    }

}