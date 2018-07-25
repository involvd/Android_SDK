package com.involvd.sdk.data.models

import com.involvd.R

enum class Status(val labelResId: Int) {
    PENDING_APPROVAL(R.string.status_pending), ACCEPTED(R.string.status_accepted),
    INVESTIGATING(R.string.status_investigating), IN_PROGRESS(R.string.status_in_progress),
    FIXING(R.string.status_fixing), WILL_FIX(R.string.status_will_fix), WONT_FIX(R.string.status_wont_fix),
    TESTING(R.string.status_testing), IN_BETA(R.string.status_beta), RELEASED(R.string.status_released), FIXED(R.string.status_fixed)
}