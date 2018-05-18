package com.tdevelopers.alteration.android.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.tdevelopers.alteration.android.helpers.CardValidator;

/**
 * Card object to hold the User card details.
 * Can be passed between activities through Bundle since
 * the it implements {@link Parcelable}.
 */
public class Card implements Parcelable {

    @SuppressWarnings("unused")
    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };
    private String cardHolderName;
    private String cardNumber;
    private String date;
    private String cvv;
    private boolean saveCard = false;

    /**
     * Constructor for Card.
     */
    public Card() {
    }

    protected Card(Parcel in) {
        cardHolderName = in.readString();
        cardNumber = in.readString();
        date = in.readString();
        cvv = in.readString();
        saveCard = in.readByte() != 0;
    }

    /**
     * @return Card holder's Name if available else Null.
     */
    public String getCardHolderName() {
        return cardHolderName;
    }

    /**
     * @param cardHolderName Cardholder's name. Must be NonNull
     *                       else {@link NullPointerException} will be thrown while making the JuspaySafe
     *                       call if Null.
     */
    public void setCardHolderName(@NonNull String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    /**
     * @return Card number else Null.
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * @param cardNumber Card Number. Must be NonNull
     *                   else {@link NullPointerException} will be thrown while making the JuspaySafe
     *                   call if Null.
     */
    public void setCardNumber(@NonNull String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * @return Card expiry date of available else Null.
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date Must be NonNull and should be in MM/yy format.
     *             Add 12/49 as default date for Maestro Card.
     *             Else {@link NullPointerException} will be thrown while making Juspay Safe browser.
     */
    public void setDate(@NonNull String date) {
        this.date = date;
    }

    /**
     * @return cvv of the card if available else Null.
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * @param cvv Must be NonNull.
     *            Add 111 as default cvv for Maestro Card.
     *            Else {@link NullPointerException} will be thrown while making Juspay Safe browser.
     */
    public void setCvv(@NonNull String cvv) {
        this.cvv = cvv;
    }

    /**
     * @return Month of the Expiry date provided. Else Null.
     */
    public String getMonth() {
        return this.date.split("/")[0];
    }

    /**
     * @return Year of the Expiry date provided. Else Null.
     */
    public String getYear() {
        return this.date.split("/")[1];
    }

    /**
     * @return True if user selects card to be saved for future use
     */
    public boolean canSaveCard() {
        return saveCard;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cardHolderName);
        dest.writeString(cardNumber);
        dest.writeString(date);
        dest.writeString(cvv);
        dest.writeByte((byte) (saveCard ? 1 : 0));
    }

    /**
     * Check if the card Name is Valid.
     *
     * @return True if not Null and not Empty. Else False.
     */
    public boolean isCardNameValid() {
        return this.cardHolderName != null && !this.cardHolderName.isEmpty();
    }

    /**
     * Check if the Expiry date is valid. {@link Card#cardNumber} must not be null.
     * Takes care of all the Edge cases involved.
     *
     * @return True if the date is valid Else False.
     */
    public boolean isDateValid() {
        if (this.date != null) {
            if (!this.date.isEmpty()) {
                return !CardValidator.isDateExpired(this.date);
            } else {
                return !(isCardNumberValid() && !CardValidator.maestroCard(this.cardNumber));
            }
        } else {
            return !(isCardNumberValid() && !CardValidator.maestroCard(this.cardNumber));
        }


    }

    /**
     * Check if the Cvv Exists. {@link Card#cvv} must not be null.
     * Takes care of all the edge cases involved.
     *
     * @return True if valid. Else False.
     */
    public boolean isCVVValid() {
        if (this.cvv != null) {
            return !this.cvv.isEmpty() || !(isCardNumberValid() && !CardValidator.maestroCard(this.cardNumber));
        } else {
            return !(isCardNumberValid() && !CardValidator.maestroCard(this.cardNumber));
        }
    }

    /**
     * Check if the Card Number is Valid using Luhn's algorithm.
     * Takes care of all the Edge cases. Requires atleast first four digits of the card.
     *
     * @return True if Valid. Else False.
     */
    public boolean isCardNumberValid() {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return false;
        }

        int result;
        if (CardValidator.masterCardWithoutLength(cardNumber)
                || CardValidator.dinnersClubIntWithoutLength(cardNumber)
                || CardValidator.visaCardWithoutLength(cardNumber)
                || CardValidator.amexCardWithoutLength(cardNumber)
                || CardValidator.discoverCardWithoutLength(cardNumber)) {
            result = CardValidator.isValid(cardNumber, false);
        } else {
            result = CardValidator.isValid(cardNumber, true);
        }
        return result != 0;
    }

    /**
     * Check if the all the card details are valid.
     * if False, use
     * {@link Card#isCardNameValid()},
     * {@link Card#isCardNumberValid()},
     * {@link Card#isCVVValid()},
     * {@link Card#isDateValid()}
     * to pinpoint the which field failed.
     *
     * @return True if Valid. Else False.
     */
    public boolean isCardValid() {
        return isCardNameValid() && isDateValid() && isCVVValid() && isCardNameValid();
    }
}