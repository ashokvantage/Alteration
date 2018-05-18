package com.tdevelopers.alteration.Payment.PaymentGateWay;

/**
 * Created by saitej dandge on 06-06-2017.
 */


import com.tdevelopers.alteration.Payment.PaymentGateWay.Responses.GetAccessTokenResponse;
import com.tdevelopers.alteration.Payment.PaymentGateWay.Responses.GetOrderIdResponse;
import com.tdevelopers.alteration.Payment.PaymentGateWay.Responses.GetPaymentIdResponse;
import com.tdevelopers.alteration.Payment.PaymentGateWay.Responses.GetPaymentStatus;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface InstaMojoPaymentService {


    @FormUrlEncoded
    @POST("oauth2/token/")
    Call<GetAccessTokenResponse> getAccessToken(@FieldMap HashMap<String, String> map);


    @FormUrlEncoded
    @POST("v2/payment_requests/")
    Call<GetPaymentIdResponse> getPaymentId(@Header("Authorization") String header, @FieldMap HashMap<String, String> map);


    @FormUrlEncoded
    @POST("v2/gateway/orders/payment-request/")
    Call<GetOrderIdResponse> getOrder(@Header("Authorization") String header, @FieldMap HashMap<String, String> map);

    @GET
    Call<GetPaymentStatus> getStatus(@Header("Authorization") String header, @Url String url);

//    @GET
//    Call<GetAddressResponse> getAddresses(@Url String url);
}