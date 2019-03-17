package com.involvd.sdk.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.involvd.sdk.data.models.FeatureVote;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by jj on 27/06/17.
 */

@Dao
public interface FeatureVoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOrUpdate(FeatureVote featureVote); //long being the row number

    @Query("SELECT * FROM featureVote")
    Flowable<List<FeatureVote>> getAll();

    @Query("SELECT * FROM featureVote WHERE " + FeatureVote.FIELD_REPORT_ID + " = :featureRequestId LIMIT 1")
    FeatureVote getFeatureVote(String featureRequestId);

    @Query("DELETE FROM featureVote WHERE " + FeatureVote.FIELD_REPORT_ID + " = :featureRequestId")
    int delete(String featureRequestId);

    @Query("SELECT * FROM featureVote WHERE " + FeatureVote.FIELD_REPORT_ID + " in (:reportIds)")
    Flowable<List<FeatureVote>> getFeatureVotes(List<String> reportIds);

}
