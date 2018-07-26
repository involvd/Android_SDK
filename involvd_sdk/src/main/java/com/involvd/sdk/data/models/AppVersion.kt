package com.involvd.sdk.data.models

import android.content.Context

/**
 * Created by jj on 05/02/18.
 */
class AppVersion(var name: String, var code: Int) {

    constructor() : this("", 0)

    constructor(context: Context, packageName: String) : this() {
        val packageInfo = context.packageManager.getPackageInfo(packageName, 0)
        this.name = packageInfo.versionName
        this.code = packageInfo.versionCode
    }

}
