package com.involvd.sdk.data.models

import android.support.annotation.NonNull

/**
 * Created by jj on 05/02/18.
 */
abstract class BaseAttachment {

    @NonNull
    lateinit var id: String
    @NonNull
    lateinit var appId: String
    @NonNull
    lateinit var name: String
    @NonNull
    lateinit var url: String
    var isPublic: Boolean = false

    constructor()

    companion object {
        const val FIELD_ID = "id"
        const val FIELD_APP_ID = "appId"
    }

    enum class Type {
        IMAGE, VIDEO, TXT, UNKNOWN
    }
}
