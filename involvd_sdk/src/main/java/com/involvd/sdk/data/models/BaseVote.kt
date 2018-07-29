package com.involvd.sdk.data.models

import android.support.annotation.NonNull
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by jj on 05/02/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
open abstract class BaseVote (
        @NonNull
        private var id: String,
        @NonNull
        open var appId: String,
        @NonNull
        open var reportId: String,
        @NonNull
        open var userId: String,
        @NonNull
        open var votedUp: Boolean? = true
) {

        constructor() : this("", "", "", "")

        @JsonProperty("id")
        fun getId(): String {
                return id
        }

        @JsonProperty("id")
        fun setId(id: String) {
                this.id = id
        }

        override fun equals(other: Any?): Boolean {
                if(other is String)
                        return reportId == other
                else if(other is BaseVote)
                        return reportId == other.reportId
                return super.equals(other)
        }

        companion object {
                const val FIELD_ID = "reportId"
                const val FIELD_APP_ID = "appId"
                const val FIELD_USER_ID = "userId"
        }

}