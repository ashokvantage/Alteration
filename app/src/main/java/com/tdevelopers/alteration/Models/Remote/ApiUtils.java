package com.tdevelopers.alteration.Models.Remote;

/**
 * Created by saitej dandge on 06-06-2017.
 */

public class ApiUtils {

//    private static final String BASE_URL = "http://35.186.158.253/api/v1/";
    private static final String BASE_URL = "http://35.199.31.212/api/v1/";

    public static AlterationService getAlterationService() {
        return RetrofitClient.getClient(BASE_URL).create(AlterationService.class);
    }


    public static String getAPIEndPoint(String input) {
        return BASE_URL + input;
    }

}