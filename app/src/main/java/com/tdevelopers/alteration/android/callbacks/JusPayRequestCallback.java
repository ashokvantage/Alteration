package com.tdevelopers.alteration.android.callbacks;

import android.os.Bundle;

import com.tdevelopers.alteration.android.models.Card;
import com.tdevelopers.alteration.android.models.Errors;
import com.tdevelopers.alteration.android.models.Order;
import com.tdevelopers.alteration.android.activities.PaymentActivity;
import com.tdevelopers.alteration.android.helpers.Constants;
import com.tdevelopers.alteration.android.network.Request;

/**
 * Callback listener that is passed along with {@link Order}
 * and {@link Card} to
 * {@link Request#Request(Order, Card, JusPayRequestCallback)}.
 */

public interface JusPayRequestCallback {
    /**
     * Called on finish of the network call to Juspay with card and order details.
     * Pass on the bundle only after validating error to be null.
     *
     * @param bundle - Payment bundle that needs to be passed to {@link PaymentActivity}
     *               with Key {@link Constants#PAYMENT_BUNDLE}.
     * @param error  - Appropriate {@link Exception} extended exception.
     *               {@link Errors.ConnectionError} related to all network exceptions.
     */
    void onFinish(Bundle bundle, Exception error);
}
