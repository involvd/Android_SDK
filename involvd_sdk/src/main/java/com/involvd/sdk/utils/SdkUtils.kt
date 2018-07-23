package com.involvd.sdk.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.text.TextUtils
import java.io.ByteArrayInputStream
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

open class SdkUtils {

    companion object {

        private var TAG = SdkUtils::class.java.simpleName
        private const val META_KEY = "com.involvd.apiKey"

        /**
         * @packageName being null retrieves all
         */
        @JvmStatic
        open fun getApiKeysForPackages(context: Context, packageName: String? = null): HashMap<String, String> {
            val i = Intent("android.intent.action.MAIN")
            i.addCategory("android.intent.category.LAUNCHER")
            if(!TextUtils.isEmpty(packageName))
                i.`package` = packageName
            val list = context.packageManager.queryIntentActivities(i, PackageManager.GET_META_DATA)
            val appMap = HashMap<String, String>()
            if (list?.isNotEmpty() == true) {
                for (packageInfo in list) {
                    if (packageInfo?.activityInfo?.applicationInfo?.metaData?.containsKey(META_KEY) == true)
                        appMap.put(packageInfo.activityInfo.packageName, packageInfo.activityInfo.applicationInfo.metaData.getString(META_KEY))
                }
            }
            return appMap;
        }

        @JvmStatic
        open fun getApiKeyForPackage(context: Context, packageName: String): String? {
            val map = getApiKeysForPackages(context, packageName)
            if(map.isEmpty())
                return null
            else
                return map.get(packageName)
        }

//                    Log.d(TAG, "SHA1: " + Base64.encode("3C:13:C4:E2:7E:BB:CF:56:6B:15:86:7E:C8:4D:A9:E0:91:97:D7:A8".toByteArray(), 0))

        @JvmStatic
        fun getCertificateSHA1Fingerprint(context: Context, packageName: String): String? {
            val pm = context.getPackageManager()
            val flags = PackageManager.GET_SIGNATURES
            var packageInfo: PackageInfo? = null
            try {
                packageInfo = pm.getPackageInfo(packageName, flags)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            val signatures = packageInfo!!.signatures
            val cert = signatures[0].toByteArray()
            val input = ByteArrayInputStream(cert)
            var cf: CertificateFactory? = null
            try {
                cf = CertificateFactory.getInstance("X509")
            } catch (e: CertificateException) {
                e.printStackTrace()
            }

            var c: X509Certificate? = null
            try {
                c = cf!!.generateCertificate(input) as X509Certificate
            } catch (e: CertificateException) {
                e.printStackTrace()
            }

            val md = MessageDigest.getInstance("SHA1")
            val publicKey = md.digest(c!!.getEncoded())
            val hexString = byte2HexFormatted(publicKey)

            return return hashString("MD5", hexString)
        }

        private fun byte2HexFormatted(arr: ByteArray): String {
            val str = StringBuilder(arr.size * 2)
            for (i in arr.indices) {
                var h = Integer.toHexString(arr[i].toInt())
                val l = h.length
                if (l == 1) h = "0$h"
                if (l > 2) h = h.substring(l - 2, l)
                str.append(h.toUpperCase())
                if (i < arr.size - 1) str.append(':')
            }
            return str.toString()
        }

        open fun hashString(type: String, input: String): String {
            val HEX_CHARS = "0123456789ABCDEF"
            val bytes = MessageDigest
                    .getInstance(type)
                    .digest(input.toByteArray())
            val result = StringBuilder(bytes.size * 2)

            bytes.forEach {
                val i = it.toInt()
                result.append(HEX_CHARS[i shr 4 and 0x0f])
                result.append(HEX_CHARS[i and 0x0f])
            }

            return result.toString()
        }

    }

}
