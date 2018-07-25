package com.involvd.sdk.data.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.involvd.sdk.data.Converters

class FeatureRequest : BaseReport {

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

}