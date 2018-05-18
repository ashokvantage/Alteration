package com.tdevelopers.alteration.Payment.PaymentGateWay;

/**
 * Created by saitej dandge on 06-06-2017.
 */

public class InstaMojoPaymentUtils {


    public static InstaMojoPaymentService getInstaMojoService() {
        return MojoRetrofitClient.getClient("https://test.instamojo.com/").create(InstaMojoPaymentService.class);
    }

}