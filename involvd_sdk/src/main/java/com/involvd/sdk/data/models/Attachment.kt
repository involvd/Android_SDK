package com.involvd.sdk.data.models

import android.support.annotation.NonNull
import com.google.firebase.firestore.PropertyName
import com.involvd.sdk.data.room.Converters

/**
 * Created by jj on 05/02/18.
 */
class Attachment {

    @NonNull
    lateinit var id: String
    @NonNull
    lateinit var appId: String
    @NonNull
    lateinit var name: String
    @NonNull
    lateinit var url: String
    @NonNull
    var type: Type = Type.UNKNOWN
    var isPublic: Boolean = false

    constructor()

    var _type: String
        @PropertyName("type")
        get() = Converters.attachmentTypeToString(type)
        @PropertyName("type")
        set(type) { this.type = Converters.stringToAttachmentType(type) }

    companion object {
        const val FIELD_ID = "id"
        const val FIELD_APP_ID = "appId"
    }

    enum class Type {
        IMAGE, VIDEO, TXT, UNKNOWN
    }
}
