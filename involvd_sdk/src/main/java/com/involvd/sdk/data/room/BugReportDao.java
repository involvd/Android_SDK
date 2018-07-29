package com.involvd.sdk.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.involvd.sdk.data.models.BugReport;
import com.involvd.sdk.data.viewmodels.BugReportViewModel;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by jj on 27/06/17.
 */

@Dao
public interface BugReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertOrUpdate(BugReport bugReport); //long being the row number

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long[] insertOrUpdateAll(List<BugReport> bugReports);

    @Delete
    public abstract int delete(BugReport bugReport);

    @Delete
    public abstract int delete(List<BugReport> bugReports);

    @Query("SELECT * FROM bugReport")
    @Transaction
    public abstract Flowable<List<BugReport>> getBugReports();

    @Query("SELECT * FROM bugReport WHERE " + BugReport.FIELD_STATUS + " = :status AND " + BugReport.FIELD_APP_ID + " = :appId")
    @Transaction
    public abstract Flowable<List<BugReportViewModel>> getBugReportsOfStatus(String appId, String status);

    @Query("SELECT * FROM bugReport WHERE " + BugReport.FIELD_ID + " = :bugReportId LIMIT 1")
    @Transaction
    public abstract BugReportViewModel getBugReport(String bugReportId);

}
