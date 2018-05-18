package com.tdevelopers.alteration.android.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * UPIOptions Class to hold the details of a UPISubmission options.
 */

public class UPIOptions implements Parcelable {

    @SuppressWarnings("unused")
    public static final Creator<UPIOptions> CREATOR = new Creator<UPIOptions>() {
        @Override
        public UPIOptions createFromParcel(Parcel in) {
            return new UPIOptions(in);
        }

        @Override
        public UPIOptions[] newArray(int size) {
            return new UPIOptions[size];
        }
    };
    private String url;

    public UPIOptions(String url) {
        this.url = url;
    }

    protected UPIOptions(Parcel in) {
        url = in.readString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
    }
}