package com.involvd.sdk.data.models

import androidx.room.Entity
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.involvd.sdk.data.Converters

@Entity(primaryKeys = [(FeatureRequest.FIELD_ID)])
open class FeatureRequest : BaseReport {

    var _status: String
        @JsonProperty("status")
        get() = Converters.reportStatusToString(getStatus())
        @JsonProperty("status")
        set(status) { this.status = Converters.stringToReportStatus(status) }

    @JsonIgnore
    private var status: Status = Status.PENDING_APPROVAL

    @JsonIgnore
    fun getStatus(): Status {
        return status
    }

    @JsonIgnore
    fun setStatus(status: Status) {
        this.status = status
    }

    @JsonIgnore
    override fun getName(): String {
        return title
    }

    constructor()

    constructor(packageName: String, title: String, description: String) : super(packageName, title, description)

    companion object {
        const val FIELD_ID = BaseReport.FIELD_ID
        const val FIELD_APP_ID = BaseReport.FIELD_APP_ID
        const val FIELD_STATUS = "status"
    }

}