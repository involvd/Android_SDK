package com.involvd.sdk.data.models

import com.involvd.R

enum class Status(val labelResId: Int) {
    PENDING_APPROVAL(R.string.status_pending), ACCEPTED(R.string.status_accepted), IN_PROGRESS(R.string.status_in_progress),
    TESTING(R.string.status_testing), IN_BETA(R.string.status_beta), COMPLETE(R.string.status_complete)
}