package com.involvd.sdk.data

import android.content.Context
import android.text.TextUtils
import com.robj.radicallyreusable.base.utils.PrefUtils

class PrefManager {

    companion object {

        const val SUBMITTEE_ID = "SUBMITTEE_ID"

        @JvmStatic
        fun getSubmitteeId(context: Context): String? {
            return PrefUtils.readStringPref(context, SUBMITTEE_ID)
        }

        @JvmStatic
        fun setSubmitteeId(context: Context, submitteeId: String?) {
            if(TextUtils.isEmpty(submitteeId)) {
                val prefs = Array(1) { submitteeId }
                PrefUtils.removePrefs(context, prefs)
                return
            }
            PrefUtils.writeStringPref(context, SUBMITTEE_ID, submitteeId)
        }

    }

}