package com.tdevelopers.alteration.Models.Remote;

/**
 * Created by saitej dandge on 06-06-2017.
 */


import com.tdevelopers.alteration.Cart.Checkout.CheckoutResponse;
import com.tdevelopers.alteration.Cart.Checkout.DeleteCartResponce;
import com.tdevelopers.alteration.Cart.ClearCartResponse;
import com.tdevelopers.alteration.Cart.EditCart.EditCartResponse;
import com.tdevelopers.alteration.Cart.getCart.GetCartItemsResponse;
import com.tdevelopers.alteration.Models.Responses.AlterListResponse;
import com.tdevelopers.alteration.NewLogin.OTPAuthenticate.OTPAuthenticateResponse;
import com.tdevelopers.alteration.NewLogin.OTPRequest.OTPRequestResponse;
import com.tdevelopers.alteration.Renew.Button.ButtonResponses.ButtonResponse;
import com.tdevelopers.alteration.Renew.Collar.CollarResponses.CollarResponse;
import com.tdevelopers.alteration.Renew.Cuff.CuffsResponses.CuffsResponse;
import com.tdevelopers.alteration.Renew.Placket.PlacketResponses.PlacketResponse;
import com.tdevelopers.alteration.Renew.Pocket.PocketResponses.PocketResponse;
import com.tdevelopers.alteration.Renew.Sleeve.SleeveResponses.SleeveResponse;
import com.tdevelopers.alteration.Renew.home.ColorResponce;
import com.tdevelopers.alteration.Renew.home.ShirtResponce;
import com.tdevelopers.alteration.UserPackage.AddressPackage.AddAddressResponse;
import com.tdevelopers.alteration.UserPackage.AddressPackage.GetAddressResponse;
import com.tdevelopers.alteration.UserPackage.GetUserResponse;
import com.tdevelopers.alteration.UserPackage.MeasurementsPackage.AddMeasurementResponse;
import com.tdevelopers.alteration.UserPackage.MeasurementsPackage.GetMeasurementsResponse;
import com.tdevelopers.alteration.UserPackage.RenewPackage.GetRenewResponce;
import com.tdevelopers.alteration.UserPackage.TransactionPackage.SaveTransactionResponse;
import com.tdevelopers.alteration.UserPackage.UpdateUserResponse;
import com.tdevelopers.alteration.UserPackage.userOrder.PreviousOrderResponce;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;


public interface AlterationService {


    @GET
    Call<AlterListResponse> getAlterList(@Url String url);


    @GET
    Call<ButtonResponse> getRepairButtonsList(@Url String url);

    @GET
    Call<CollarResponse> getRepairCollarsList(@Url String url);


    @GET
    Call<CuffsResponse> getRepairCuffsList(@Url String url);

    @GET
    Call<SleeveResponse> getRepairSleevesList(@Url String url);
    @GET
    Call<PocketResponse> getRepairPocketsList(@Url String url);
    @GET
    Call<PlacketResponse> getRepairPlacketsList(@Url String url);

    @FormUrlEncoded
    @POST
    Call<EditCartResponse> editCart(@Url String url, @FieldMap HashMap<String, Object> map);


    @FormUrlEncoded
    @POST
    Call<OTPRequestResponse> requestOTP(@Url String url, @Field("mobile") String mobile);


    @FormUrlEncoded
    @POST
    Call<OTPAuthenticateResponse> authenticateOTP(@Url String url, @Field("mobile") String mobile, @Field("otp") String otp);

    @GET
    Call<GetCartItemsResponse> getCartItems(@Url String url);


    @FormUrlEncoded
    @POST
    Call<CheckoutResponse> checkOut(@Url String url, @Field("user_id") String userId);


    @FormUrlEncoded
    @POST
    Call<CheckoutResponse> get(@Url String url, @Field("user_id") String userId);

    @FormUrlEncoded
    @POST("user/transaction")
    Call<SaveTransactionResponse> saveTransaction(@FieldMap HashMap<String, Object> map);

    @GET
    Call<GetUserResponse> getUserProfile(@Url String url);

    @FormUrlEncoded
    @POST("user/clear-cart")
    Call<ClearCartResponse> clearCart(@Field("user_id") String userId);


    @FormUrlEncoded
    @POST("user/update-profile")
    Call<UpdateUserResponse> updateProfile(@FieldMap HashMap<String, Object> map);

    @GET
    Call<PreviousOrderResponce> getPreviousOrders(@Url String url);
    @FormUrlEncoded
    @POST("address/add-address")
    Call<AddAddressResponse> addAddress(@FieldMap HashMap<String, Object> map);


    @FormUrlEncoded
    @PUT("address/edit-address")
    Call<AddAddressResponse> editAddress(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "address/delete-address", hasBody = true)
    Call<AddMeasurementResponse> deleteAddress(@FieldMap HashMap<String, Object> map);


    @GET
    Call<GetAddressResponse> getAddresses(@Url String url);

    @GET
    Call<GetMeasurementsResponse> getMeasurements(@Url String url);

    @FormUrlEncoded
    @POST("measurement/add-measurement")
    Call<AddMeasurementResponse> addMeasurement(@FieldMap HashMap<String, Object> map);


    @FormUrlEncoded
    @PUT("measurement/edit-measurement")
    Call<AddMeasurementResponse> editMeasurement(@FieldMap HashMap<String, Object> map);


    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "measurement/delete-measurement", hasBody = true)
    Call<AddMeasurementResponse> deleteMeasurement(@FieldMap HashMap<String, Object> map);

    @GET
    Call<ColorResponce> getRepairColor(@Url String url);
    @GET
    Call<ShirtResponce> getGarments(@Url String url);
    @GET
    Call<ShirtResponce> getShirtBody(@Url String url);
    @GET
    Call<ColorResponce> getRepairFebric(@Url String url);

    @FormUrlEncoded
    @POST
    Call<EditCartResponse> createRenw(@Url String url, @FieldMap HashMap<String, Object> map);
    @GET
    Call<GetRenewResponce> getRenew(@Url String url);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "user/delete-from-cart", hasBody = true)
    Call<DeleteCartResponce> deleteImage(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "user/delete-from-cart", hasBody = true)
    Call<CheckoutResponse> deletecartItem(@FieldMap HashMap<String, Object> map);
}