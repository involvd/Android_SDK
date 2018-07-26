package com.involvd.sdk.ui.view_feature_request;

import android.content.Context;
import android.content.Intent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.involvd.sdk.data.models.FeatureRequest;
import com.involvd.sdk.ui.create_bug_report.ViewFeatureRequestFragment;
import com.involvd.sdk.ui.create_feature_request.CreateFeatureRequestActivity;

public class ViewFeatureRequestActivity extends CreateFeatureRequestActivity {

    @Override
    protected String getFragmentName() {
        return ViewFeatureRequestFragment.class.getName();
    }

    public static Intent getLaunchIntent(Context context, FeatureRequest featureRequest) {
        Intent i = new Intent(context, ViewFeatureRequestActivity.class);
        try {
            String json = new ObjectMapper().writeValueAsString(featureRequest);
            i.putExtra(ViewFeatureRequestFragment.JSON, json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return i;
    }

}
