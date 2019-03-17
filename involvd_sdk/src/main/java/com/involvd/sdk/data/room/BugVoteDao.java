package com.involvd.sdk.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.involvd.sdk.data.models.BugVote;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by jj on 27/06/17.
 */

@Dao
public interface BugVoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOrUpdate(BugVote bugVote); //long being the row number

    @Query("SELECT * FROM bugVote")
    Flowable<List<BugVote>> getAll();

    @Query("SELECT * FROM bugVote WHERE " + BugVote.FIELD_REPORT_ID + " = :bugReportId LIMIT 1")
    BugVote getBugVote(String bugReportId);

    @Query("DELETE FROM bugVote WHERE " + BugVote.FIELD_REPORT_ID + " = :bugReportId")
    int delete(String bugReportId);

    @Query("SELECT * FROM bugVote WHERE " + BugVote.FIELD_REPORT_ID + " in (:reportIds)")
    Flowable<List<BugVote>> getBugVotes(List<String> reportIds);
}
