package com.involvd.sdk.data.models

import android.os.Build
import androidx.annotation.NonNull
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.involvd.BuildConfig
import com.robj.radicallyreusable.base.Searchable
import java.util.*

/**
 * Created by jj on 24/01/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class BaseReport : Searchable {

    @NonNull
    private lateinit var id: String
    lateinit var packageName: String
    lateinit var title: String
    var description: String? = null
    var upvotes: Int = 0
    var downvotes: Int = 0
    var deployedInBuild: String? = null
    var submittedBy: String? = null
    var followerList: MutableList<String> = ArrayList()
    var appVersionsAffected: MutableList<AppVersion> = ArrayList()
    var appDevicesAffected: MutableList<Device> = ArrayList()

    constructor()

    constructor(packageName: String, title: String, description: String) {
        this.packageName = packageName
        this.title = title
        this.description = description
        this.upvotes = 1
        this.appDevicesAffected.add(Device(Build.MODEL?:"Unknown", Build.VERSION.SDK_INT))
    }

    @JsonProperty("id")
    fun getId(): String {
        return id
    }

    @JsonProperty("id")
    fun setId(id: String) {
        this.id = id
    }

    override fun equals(other: Any?): Boolean {
        if (other is BaseReport)
            return other.getId() == getId()
        return super.equals(other)
    }

    @JsonIgnore
    abstract override fun getName(): String

    companion object {
        const val FIELD_ID = "id"
        const val FIELD_APP_ID = "packageName"
    }
}
