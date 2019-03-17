package com.involvd.sdk.data;

import androidx.room.TypeConverter;
import android.text.TextUtils;

import com.involvd.sdk.data.models.AppVersion;
import com.involvd.sdk.data.models.BaseAttachment;
import com.involvd.sdk.data.models.Device;
import com.involvd.sdk.data.models.Status;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jj on 27/06/17.
 */

public class Converters {

    @TypeConverter
    public static String stringListToString(List<String> stringList) {
        StringBuilder sb = new StringBuilder();
        for(String s : stringList) {
            if(sb.length() > 0)
                sb.append(",");
            sb.append(s);
        }
        return sb.toString();
    }

    @TypeConverter
    public static List<String> stringToStringList(String s) {
        if(TextUtils.isEmpty(s))
            return new ArrayList<>();
        String[] array = s.split(",");
        return Arrays.asList(array);
    }

    @TypeConverter
    public static String reportStatusToString(Status status) {
        return status.name().toLowerCase();
    }

    @TypeConverter
    public static Status stringToReportStatus(String status) {
        return Status.valueOf(status.toUpperCase());
    }

    @TypeConverter
    public static String attachmentTypeToString(@NotNull BaseAttachment.Type type) {
        return type.name().toLowerCase();
    }

    @TypeConverter
    public static BaseAttachment.Type stringToAttachmentType(@NotNull String type) {
        return BaseAttachment.Type.valueOf(type.toUpperCase());
    }

    @TypeConverter
    public static String appVersionToString(@NotNull AppVersion appVersion) {
        return appVersion.getName() + ":" + appVersion.getCode();
    }

    @TypeConverter
    public static AppVersion stringToAppVersion(@NotNull String appVersion) {
        if(TextUtils.isEmpty(appVersion))
            return null;
        String[] s = appVersion.split(":");
        return new AppVersion(s[0], Integer.valueOf(s[1]));
    }

    @TypeConverter
    public static String appVersionListToString(List<AppVersion> appVersionList) {
        StringBuilder sb = new StringBuilder();
        for(AppVersion appVersion : appVersionList) {
            String s = appVersionToString(appVersion);
            if(!TextUtils.isEmpty(s)) {
                sb.append(s);
                sb.append("|");
            }
        }
        return sb.toString();
    }

    @TypeConverter
    public static List<AppVersion> appVersionListToString(String appVersionListString) {
        List<AppVersion> list = new ArrayList<>();
        if(!TextUtils.isEmpty(appVersionListString)) {
            String[] arr = appVersionListString.split("\\|");
            for(String a : arr)
                if(!TextUtils.isEmpty(a)) {
                    AppVersion appVersion = stringToAppVersion(a);
                    if(appVersion != null)
                        list.add(appVersion);
                }
        }
        return list;
    }

    @TypeConverter
    public static String deviceToString(@NotNull Device device) {
        return device.getDeviceName() + ":" + device.getSdkVersion();
    }

    @TypeConverter
    public static Device stringToDevice(@NotNull String device) {
        if(TextUtils.isEmpty(device))
            return null;
        String[] s = device.split(":");
        return new Device(s[0], Integer.valueOf(s[1]));
    }

    @TypeConverter
    public static String deviceListToString(List<Device> deviceList) {
        StringBuilder sb = new StringBuilder();
        for(Device device : deviceList) {
            String s = deviceToString(device);
            if(!TextUtils.isEmpty(s)) {
                sb.append(s);
                sb.append("|");
            }
        }
        return sb.toString();
    }

    @TypeConverter
    public static List<Device> deviceListToString(String deviceListString) {
        List<Device> list = new ArrayList<>();
        if(!TextUtils.isEmpty(deviceListString)) {
            String[] arr = deviceListString.split("\\|");
            for(String a : arr)
                if(!TextUtils.isEmpty(a)) {
                    Device device = stringToDevice(a);
                    if(device != null)
                        list.add(device);
                }
        }
        return list;
    }

}
