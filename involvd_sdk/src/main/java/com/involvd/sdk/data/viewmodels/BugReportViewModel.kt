package com.involvd.sdk.data.viewmodels

import android.arch.persistence.room.Relation
import com.involvd.sdk.data.models.Attachment
import com.involvd.sdk.data.models.BaseVote
import com.involvd.sdk.data.models.BugReport
import com.involvd.sdk.data.models.BugVote

class BugReportViewModel : BugReport() {

    @Relation(parentColumn = BugReport.FIELD_ID, entityColumn = Attachment.FIELD_APP_ID, entity = Attachment::class)
    var attachments: MutableList<Attachment> = java.util.ArrayList()
    @Relation(parentColumn = BugReport.FIELD_ID, entityColumn = BaseVote.FIELD_REPORT_ID, entity = BugVote::class)
    var _votes: MutableList<BugVote> = java.util.ArrayList()

    var hasVotedUp: Boolean?
        get() {
            return if(!_votes.isEmpty()) {
                _votes.get(0).votedUp
            } else
                null
        }
        set(status) {  }

}
