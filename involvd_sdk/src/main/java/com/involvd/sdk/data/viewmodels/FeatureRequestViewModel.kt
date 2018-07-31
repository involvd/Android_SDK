package com.involvd.sdk.data.viewmodels

import android.arch.persistence.room.Relation
import com.involvd.sdk.data.models.*

class FeatureRequestViewModel : FeatureRequest() {

    @Relation(parentColumn = FeatureRequest.FIELD_ID, entityColumn = Attachment.FIELD_APP_ID, entity = Attachment::class)
    var attachments: MutableList<Attachment> = java.util.ArrayList()
    @Relation(parentColumn = BugReport.FIELD_ID, entityColumn = BaseVote.FIELD_REPORT_ID, entity = FeatureVote::class)
    var _votes: MutableList<FeatureVote> = java.util.ArrayList()

    var hasVotedUp: Boolean?
        get() {
            return if(!_votes.isEmpty()) {
                _votes.get(0).votedUp
            } else
                null
        }
        set(status) {  }

}
