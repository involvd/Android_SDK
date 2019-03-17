package com.involvd.sdk.ui.view_feature_request;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.involvd.R;
import com.involvd.sdk.data.models.FeatureRequest;
import com.involvd.sdk.ui.BaseDialogActivity;
import com.involvd.sdk.ui.create_bug_report.ViewFeatureRequestFragment;

public class ViewFeatureRequestActivity extends BaseDialogActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.details);
    }

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
