package com.involvd.sdk.data.networking.retrofit;

import com.robj.involvd.data.models.BaseReport;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jj on 11/02/17.
 */

public interface ApiService {

    /** Places **/

    @GET
    Flowable<List<BaseReport>> getBugs(@Query("id") String appId, @Query("status") String status, @Query("page") String previousBugId);

}
