package com.involvd.sdk.data.models

import android.support.annotation.NonNull
import com.google.firebase.firestore.Exclude

/**
 * Created by jj on 05/02/18.
 */
open abstract class BaseVote(
        @NonNull
        open var appId: String,
        @NonNull
        open var reportId: String,
        @NonNull
        open var userId: String,
        @NonNull
        open var votedUp: Boolean? = true
) {

        @Exclude
        fun isDelete(): Boolean {
                return votedUp == null
        }

        override fun equals(other: Any?): Boolean {
                if(other is String)
                        return reportId == other
                else if(other is BaseVote)
                        return reportId == other.reportId
                return super.equals(other)
        }

}