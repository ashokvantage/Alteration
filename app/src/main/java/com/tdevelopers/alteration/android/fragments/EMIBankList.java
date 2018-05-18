package com.tdevelopers.alteration.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.android.activities.PaymentDetailsActivity;
import com.tdevelopers.alteration.android.helpers.Logger;
import com.tdevelopers.alteration.android.models.EMIBank;

public class EMIBankList extends BaseFragment {

    private PaymentDetailsActivity parentActivity;
    private LinearLayout emiBanksContainer;

    public EMIBankList() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emi_instamojo, container, false);
        parentActivity = (PaymentDetailsActivity) getActivity();
        inflateXML(view);
        loadBanks();
        return view;
    }

    @Override
    public void inflateXML(View view) {
        emiBanksContainer = (LinearLayout) view.findViewById(R.id.emi_view_container);
    }

    @Override
    public void onResume() {
        super.onResume();
        parentActivity.updateActionBarTitle(R.string.choose_your_credit_card);
    }

    private void loadBanks() {
        emiBanksContainer.removeAllViews();
        for (final EMIBank bank : parentActivity.getOrder().getEmiOptions().getEmiBanks()) {
            View bankView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_instamojo, emiBanksContainer, false);
            ((TextView) bankView.findViewById(R.id.item_name)).setText(bank.getBankName());
            bankView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EMIBankOptionsView optionsView = EMIBankOptionsView.getInstance(bank);
                    parentActivity.loadFragment(optionsView, true);
                }
            });

            emiBanksContainer.addView(bankView);
        }
        Logger.logDebug(this.getClass().getSimpleName(), "Loaded EMI Banks");
    }
}
