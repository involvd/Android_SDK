package com.involvd.sdk.data.viewmodels

import android.arch.persistence.room.Relation
import com.involvd.sdk.data.models.Attachment
import com.involvd.sdk.data.models.BugReport

class BugReportViewModel : BugReport() {

    @Relation(parentColumn = BugReport.FIELD_ID, entityColumn = Attachment.FIELD_APP_ID, entity = Attachment::class)
    var attachments: MutableList<Attachment> = java.util.ArrayList()

}
