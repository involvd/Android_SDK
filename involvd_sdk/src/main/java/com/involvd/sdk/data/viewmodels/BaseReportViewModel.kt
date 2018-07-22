package com.involvd.sdk.data.viewmodels

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.firebase.firestore.Exclude
import com.involvd.sdk.data.models.Attachment
import com.involvd.sdk.data.models.BaseReport
import com.robj.radicallyreusable.base.Searchable

@JsonIgnoreProperties(ignoreUnknown = true)
class BaseReportViewModel : BaseReport(), Searchable {

    @Exclude
    var isFollowing: Boolean = false
    @Exclude
    var isVotedOn: Boolean = false

    override fun getName(): String {
        return title
    }

    @Exclude
    var attachments: MutableList<Attachment> = java.util.ArrayList() //TODO: Can firestore parse this??

    fun getFollowerCount() : Int {
        return followerList.size
    }

    fun getVoteCount() : Int {
        return upvotes - downvotes
    }

}
