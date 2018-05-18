package com.tdevelopers.alteration.android.callbacks;


import android.os.Bundle;

import com.tdevelopers.alteration.android.models.UPISubmissionResponse;

/**
 * Callback for UPISubmission Submission Method.
 */

public interface UPICallback {
    void onSubmission(UPISubmissionResponse upiSubmissionResponse, Exception e);

    void onStatusCheckComplete(Bundle bundle, boolean paymentComplete, Exception e);
}
