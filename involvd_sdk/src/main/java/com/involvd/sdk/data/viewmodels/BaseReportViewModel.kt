package com.robj.involvd.data.viewmodels

import com.google.firebase.firestore.Exclude
import com.robj.involvd.data.models.Attachment
import com.robj.involvd.data.models.BaseReport
import com.robj.radicallyreusable.base.Searchable

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
