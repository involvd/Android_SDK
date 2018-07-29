package com.involvd.sdk.data.models

import android.arch.persistence.room.Entity
import android.support.annotation.NonNull
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.involvd.sdk.data.Converters

/**
 * Created by jj on 05/02/18.
 */
@Entity(primaryKeys = [(Attachment.FIELD_ID)])
class Attachment : BaseAttachment {

    @NonNull
    @JsonIgnore
    var type: Type = Type.UNKNOWN

    constructor() : super()

    var _type: String
        @JsonProperty("type")
        get() = Converters.attachmentTypeToString(type)
        @JsonProperty("type")
        set(type) { this.type = Converters.stringToAttachmentType(type) }

    companion object {
        const val FIELD_ID = BaseAttachment.FIELD_ID
        const val FIELD_APP_ID = BaseAttachment.FIELD_APP_ID
    }

}
