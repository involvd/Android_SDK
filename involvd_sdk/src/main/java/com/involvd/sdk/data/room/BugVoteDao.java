package com.involvd.sdk.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

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

    @Query("SELECT * FROM bugVote WHERE " + BugVote.FIELD_ID + " = :bugReportId LIMIT 1")
    BugVote getBugVote(String bugReportId);

    @Query("DELETE FROM bugVote WHERE " + BugVote.FIELD_ID + " = :bugReportId")
    int delete(String bugReportId);

}
