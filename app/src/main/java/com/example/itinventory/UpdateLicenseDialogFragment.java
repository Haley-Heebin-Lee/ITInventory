package com.example.itinventory;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class UpdateLicenseDialogFragment extends DialogFragment{
    private Spinner dropdown;
    private DatePicker expiry;
    private String selectedName;
    private ArrayList<String> names = new ArrayList<>(0);

    public UpdateLicenseDialogFragment() {
    }

    public interface UpdateLicenseDialogListener {
        void onLicenseUpdateDialog(String name, DatePicker expiry);
    }

    private UpdateLicenseDialogFragment.UpdateLicenseDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateLicenseDialogFragment.UpdateLicenseDialogListener)getActivity();
        }
        catch (ClassCastException e) {
            Log.e("Dialog", "onAttach: ClassCastException: "
                    + e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_license_fragment, container, false);

        expiry = view.findViewById(R.id.license_update_date);
        TextView mActionCancel = view.findViewById(R.id.action_cancel);
        TextView mActionOk = view.findViewById(R.id.action_ok);

        Bundle bundle = getArguments();
        names = bundle.getStringArrayList("licenseNames");

        dropdown = view.findViewById(R.id.update_license_dropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, names);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedName = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        mActionCancel.setOnClickListener(
                v -> {
                    Log.d("dialog", "closing dialog");
                    getDialog().dismiss();
                });

        mActionOk.setOnClickListener(
                v -> {
                    Log.d("dialog", "saving license info");
                    listener.onLicenseUpdateDialog(selectedName, expiry);
                    getDialog().dismiss();
                });
        return view;
    }

}
