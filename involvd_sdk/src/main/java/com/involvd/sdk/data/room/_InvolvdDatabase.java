package com.involvd.sdk.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.involvd.sdk.data.Converters;
import com.involvd.sdk.data.models.Attachment;
import com.involvd.sdk.data.models.BugReport;
import com.involvd.sdk.data.models.BugVote;
import com.involvd.sdk.data.models.FeatureRequest;
import com.involvd.sdk.data.models.FeatureVote;

/**
 * Created by jj on 27/06/17.
 */

@TypeConverters({Converters.class})
@Database(entities = { BugReport.class, FeatureRequest.class, Attachment.class, FeatureVote.class, BugVote.class }, version = 1, exportSchema = false)
public abstract class _InvolvdDatabase extends RoomDatabase {

    public static final int VALUE_FALSE = 0;
    public static final int VALUE_TRUE = 1;

    public abstract BugReportDao bugReportDao();
    public abstract AttachmentDao attachmentDao();
    public abstract FeatureRequestDao featureRequestDao();
    public abstract BugVoteDao bugVoteDao();
    public abstract FeatureVoteDao featureVoteDao();

}
