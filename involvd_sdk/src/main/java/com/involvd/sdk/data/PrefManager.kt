package com.involvd.sdk.data

import android.content.Context
import android.text.TextUtils
import com.robj.radicallyreusable.base.utils.PrefUtils

class PrefManager {

    companion object {

        const val UNIQUE_ID = "UNIQUE_ID"

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

    }

}