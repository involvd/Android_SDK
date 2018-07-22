package com.involvd.sdk.data.room;

import com.involvd.sdk.data.models.Attachment;
import com.involvd.sdk.data.models.BaseReport;

import org.jetbrains.annotations.NotNull;

/**
 * Created by jj on 27/06/17.
 */

public class Converters {

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

}
