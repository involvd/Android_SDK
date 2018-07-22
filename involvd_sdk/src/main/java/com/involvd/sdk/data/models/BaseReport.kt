package com.involvd.sdk.data.models

import android.support.annotation.NonNull
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import com.involvd.R
import com.involvd.sdk.data.room.Converters
import com.robj.firestore_utils.FirebaseDbUtils
import java.util.*

/**
 * Created by jj on 24/01/18.
 */
open class BaseReport : FirebaseDbUtils.DatabaseValueProvider {

    @NonNull
    private lateinit var id: String
    lateinit var packageName: String
    lateinit var title: String
    var description: String? = null
    var upvotes: Int = 0
    var downvotes: Int = 0
    var deployedInBuild: String? = null
    var submittedTimestamp: Timestamp = Timestamp(Date())
    lateinit var submittedBy: String
    @Exclude @get:Exclude @set:Exclude
    var status: Status = Status.PENDING_APPROVAL
    @Exclude @get:Exclude
    var followerList: MutableList<String> = ArrayList()
    var appVersionsAffected: MutableList<Int> = ArrayList()

    @JsonProperty("status")
    open fun get_Status() : String {
        return Converters.reportStatusToString(status)
    }
    @JsonProperty("status")
    open fun set_Status(status: String) {
        this.status = Converters.stringToReportStatus(status)
    }

    constructor()

    constructor(packageName: String, title: String, description: String) {
        this.packageName = packageName
        this.title = title
        this.description = description
    }


    override fun getId(): String {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    override fun equals(other: Any?): Boolean {
        if (other is BaseReport)
            return other.getId() == getId()
        return super.equals(other)
    }

    enum class Status(val labelResId: Int) {
        PENDING_APPROVAL(R.string.status_pending), ACCEPTED(R.string.status_accepted), IN_PROGRESS(R.string.status_in_progress),
        TESTING(R.string.status_testing), IN_BETA(R.string.status_beta), COMPLETE(R.string.status_complete)
    }
}
