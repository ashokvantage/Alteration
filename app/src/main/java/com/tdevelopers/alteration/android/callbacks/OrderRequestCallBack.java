package com.tdevelopers.alteration.android.callbacks;

import com.tdevelopers.alteration.android.models.Errors;
import com.tdevelopers.alteration.android.models.Order;
import com.tdevelopers.alteration.android.network.Request;

/**
 * Callback listener that is passed along with {@link Order} to
 * {@link Request#Request(Order, OrderRequestCallBack)}.
 */

public interface OrderRequestCallBack {
    /**
     * Called on finishing the order creation with Instamojo server.
     * {@link Order} will be unchanged if the error is not null.
     *
     * @param order - original order if the error is not null else
     *              {@link Order#cardOptions} and {@link Order#netBankingOptions}
     *              will be updated from the response.
     * @param error - Appropriate {@link Exception} extended exception.
     *              {@link Errors.ConnectionError} related to all network exceptions
     *              and {@link Errors.ServerError} related to all
     *              form level validation errors from Instamojo server.
     */
    void onFinish(Order order, Exception error);

}
