package com.involvd.sdk.data.models

/**
 * Created by jj on 05/02/18.
 */
class BugVote(appId: String, bugReportId: String, userId: String, votedUp: Boolean? = true) : BaseVote("", appId, bugReportId, userId, votedUp) {

    constructor() : this("", "", "")

}
