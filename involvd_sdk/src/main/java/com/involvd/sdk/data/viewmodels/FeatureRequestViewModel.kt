package com.involvd.sdk.data.viewmodels

import android.arch.persistence.room.Relation
import com.involvd.sdk.data.models.Attachment
import com.involvd.sdk.data.models.FeatureRequest

class FeatureRequestViewModel : FeatureRequest() {

    @Relation(parentColumn = FeatureRequest.FIELD_ID, entityColumn = Attachment.FIELD_APP_ID, entity = Attachment::class)
    var attachments: MutableList<Attachment> = java.util.ArrayList()

}
