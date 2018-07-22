package com.involvd.sdk.data.room;

import android.net.Uri;
import android.text.TextUtils;

import com.google.firebase.Timestamp;
import com.robj.involvd.data.models.Attachment;
import com.robj.involvd.data.models.BaseReport;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by jj on 27/06/17.
 */

public class Converters {

    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static String uriToString(Uri uri) {
        return uri != null ? uri.toString() : null;
    }

    public static Uri stringToUri(String s) {
        return s != null ? Uri.parse(s) : null;
    }

    public static String stringListToString(List<String> stringList) {
        StringBuilder sb = new StringBuilder();
        for(String s : stringList) {
            if(sb.length() > 0)
                sb.append(",");
            sb.append(s);
        }
        return sb.toString();
    }

    public static List<String> stringToStringList(String s) {
        if(TextUtils.isEmpty(s))
            return new ArrayList<>();
        String[] array = s.split(",");
        return Arrays.asList(array);
    }

    public static String intListToString(List<Integer> integerList) {
        StringBuilder sb = new StringBuilder();
        for(Integer i : integerList) {
            if(sb.length() > 0)
                sb.append(",");
            sb.append(i);
        }
        return sb.toString();
    }

    public static List<Integer> stringToIntList(String s) {
        List<Integer> list = new ArrayList<>();
        if(TextUtils.isEmpty(s))
            return list;
        String[] array = s.split(",");
        for(String i : array)
            list.add(Integer.valueOf(i));
        return list;
    }

    public static HashMap<Float, Integer> stringToIntMap(String s) {
        String[] capacitySplit = s.split(",");
        HashMap<Float, Integer> map = new HashMap<>();
        for(String entry : capacitySplit) {
            String[] entrySplit = entry.split(":");
            if(entrySplit.length > 1)
                map.put(Float.valueOf(entrySplit[0]), Integer.valueOf(entrySplit[1]));
        }
        return map;
    }

    public static String intMapToString(HashMap<Float, Integer> map) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Float, Integer> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append(":");
            sb.append(entry.getValue());
            sb.append(",");
        }
        return sb.toString();
    }

    public static HashMap<String, String> stringToStringMap(String s) {
        String[] split = s.split(",");
        HashMap<String, String> map = new HashMap<>();
        for(String entry : split) {
            String[] entrySplit = entry.split(":");
            if(entrySplit.length > 1)
                map.put(entrySplit[0], entrySplit[1]);
        }
        return map;
    }

    public static String stringMapToString(HashMap<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append(":");
            sb.append(entry.getValue());
            sb.append(",");
        }
        return sb.toString();
    }

    public static String timezoneToString(TimeZone timezone) {
        return timezone != null ? timezone.getID() : null;
    }

    public static TimeZone stringToTimezone(String timezone) {
        return !TextUtils.isEmpty(timezone) ? TimeZone.getTimeZone(timezone) : null;
    }

    public static String reportStatusToString(BaseReport.Status status) {
        return status.name().toLowerCase();
    }

    public static BaseReport.Status stringToReportStatus(String status) {
        return BaseReport.Status.valueOf(status.toUpperCase());
    }

    public static String attachmentTypeToString(@NotNull Attachment.Type type) {
        return type.name().toLowerCase();
    }

    public static Attachment.Type stringToAttachmentType(@NotNull String type) {
        return Attachment.Type.valueOf(type.toUpperCase());
    }

    public static Date timestampToString(@NotNull Timestamp timestamp) {
        return timestamp.toDate();
    }

    public static Timestamp stringToTimestamp(@NotNull Date timestamp) {
        return new Timestamp(timestamp);
    }

}
