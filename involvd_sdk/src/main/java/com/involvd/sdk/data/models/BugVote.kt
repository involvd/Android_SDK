package com.involvd.sdk.data.models

import android.arch.persistence.room.Entity

/**
 * Created by jj on 05/02/18.
 */
@Entity(primaryKeys = [ BaseVote.FIELD_APP_ID, BaseVote.FIELD_ID ])
class BugVote(appId: String, bugReportId: String, userId: String, votedUp: Boolean? = true) : BaseVote("", appId, bugReportId, userId, votedUp) {

    constructor() : this("", "", "")

}
