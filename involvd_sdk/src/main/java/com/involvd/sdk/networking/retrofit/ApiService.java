package com.involvd.sdk.networking.retrofit;

import com.involvd.sdk.data.models.BaseReport;
import com.involvd.sdk.data.models.BaseVote;
import com.involvd.sdk.data.models.BugReport;
import com.involvd.sdk.data.models.BugVote;
import com.involvd.sdk.data.models.FeatureRequest;
import com.involvd.sdk.data.models.FeatureVote;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by jj on 11/02/17.
 */

public interface ApiService {

    /** Places **/

    @GET("/bugs")
    Flowable<List<BugReport>> getBugs(@Query("status") String status, @Query("page") String previousBugId, @Query("limit") int limit);

    @GET("/featureRequests")
    Flowable<List<FeatureRequest>> getFeatureRequests(@Query("status") String status, @Query("page") String previousFeatureReqId, @Query("limit") int limit);

    @POST("/createBugReport")
    Flowable<BugReport> createBugReport(@Body BaseReport baseReport);

    @POST("/createFeatureRequest")
    Flowable<FeatureRequest> createFeatureRequest(@Body BaseReport baseReport);

    @POST("/voteOnBug")
    Flowable<BugVote> voteOnBug(@Body BugVote vote);

    @POST("/voteOnFeatureRequest")
    Flowable<FeatureVote> voteOnFeatureRequest(@Body FeatureVote vote);

}
