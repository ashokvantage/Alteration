package com.tdevelopers.alteration.android.models;

import android.os.Parcel;
import android.os.Parcelable;


public class Wallet implements Parcelable {
    @SuppressWarnings("unused")
    public static final Creator<Wallet> CREATOR = new Creator<Wallet>() {
        @Override
        public Wallet createFromParcel(Parcel in) {
            return new Wallet(in);
        }

        @Override
        public Wallet[] newArray(int size) {
            return new Wallet[size];
        }
    };
    private String name;
    private String imageURL;
    private String walletID;

    public Wallet(String name, String imageURL, String walletID) {
        this.name = name;
        this.imageURL = imageURL;
        this.walletID = walletID;
    }

    protected Wallet(Parcel in) {
        name = in.readString();
        imageURL = in.readString();
        walletID = in.readString();
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getWalletID() {
        return walletID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageURL);
        dest.writeString(walletID);
    }
}
