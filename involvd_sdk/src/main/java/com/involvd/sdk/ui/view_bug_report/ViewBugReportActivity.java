package com.involvd.sdk.ui.view_bug_report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.involvd.R;
import com.involvd.sdk.data.models.BugReport;
import com.involvd.sdk.ui.BaseDialogActivity;
import com.involvd.sdk.ui.create_bug_report.ViewBugReportFragment;

public class ViewBugReportActivity extends BaseDialogActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.details);
    }

    @Override
    protected String getFragmentName() {
        return ViewBugReportFragment.class.getName();
    }

    public static Intent getLaunchIntent(Context context, BugReport bugReport) {
        Intent i = new Intent(context, ViewBugReportActivity.class);
        try {
            String json = new ObjectMapper().writeValueAsString(bugReport);
            i.putExtra(ViewBugReportFragment.JSON, json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return i;
    }

}
