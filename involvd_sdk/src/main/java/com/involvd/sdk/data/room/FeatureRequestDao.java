package com.involvd.sdk.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.involvd.sdk.data.models.FeatureRequest;
import com.involvd.sdk.data.viewmodels.FeatureRequestViewModel;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by jj on 27/06/17.
 */

@Dao
public interface FeatureRequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertOrUpdate(FeatureRequest featureRequest); //long being the row number

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long[] insertOrUpdateAll(List<FeatureRequest> featureRequests);

    @Delete
    public abstract int delete(FeatureRequest featureRequest);

    @Delete
    public abstract int delete(List<FeatureRequest> featureRequests);

    @Query("SELECT * FROM featureRequest")
    @Transaction
    public abstract Flowable<List<FeatureRequestViewModel>> getFeatureRequests();

    @Query("SELECT * FROM featureRequest WHERE " + FeatureRequest.FIELD_STATUS + " = :status AND " + FeatureRequest.FIELD_APP_ID + " = :appId")
    @Transaction
    public abstract Flowable<List<FeatureRequestViewModel>> getFeatureRequestsOfStatus(String appId, String status);

    @Query("SELECT * FROM featureRequest WHERE " + FeatureRequest.FIELD_ID + " = :featureRequestId LIMIT 1")
    @Transaction
    public abstract FeatureRequestViewModel getFeatureRequest(String featureRequestId);

}
