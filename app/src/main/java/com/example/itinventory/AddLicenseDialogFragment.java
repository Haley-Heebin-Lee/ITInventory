package com.example.itinventory;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class AddLicenseDialogFragment extends DialogFragment implements View.OnClickListener{
    private EditText name;
    private boolean accountWide;
    private DatePicker expiry;
    TextView text;

    public AddLicenseDialogFragment() {
    }

    public interface AddLicenseDialogListener {
        void onLicenseDialog(String name, boolean accountWise, DatePicker expiry);
    }

    private AddLicenseDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AddLicenseDialogListener)getActivity();
        }
        catch (ClassCastException e) {
            Log.e("Dialog", "onAttach: ClassCastException: "
                    + e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_license_fragment, container, false);

        view.findViewById(R.id.accountWide_checkbox).setOnClickListener(this);
        name = view.findViewById(R.id.license_name);
        expiry = view.findViewById(R.id.license_date);
        TextView mActionCancel = view.findViewById(R.id.action_cancel);
        TextView mActionOk = view.findViewById(R.id.action_ok);
        text = view.findViewById(R.id.warning_txt);
        text.setText("");

        name.requestFocus();

        mActionCancel.setOnClickListener(
                v -> {
                    Log.d("dialog", "closing dialog");
                    getDialog().dismiss();
                });

        mActionOk.setOnClickListener(
                v -> {
                    Log.d("dialog", "saving license info");
                    if (!accountWide)
                        //set expiry date to default
                        //it will have individual users
                        expiry.updateDate(2000, 0, 1);
                    if(!name.getText().toString().isEmpty()) {
                        listener.onLicenseDialog(name.getText().toString(), accountWide, expiry);
                        getDialog().dismiss();
                    }else{
                        text.setText("Please fill all the fields");
                    }
                });
        return view;
    }
    @Override
    public void onClick(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.accountWide_checkbox:
                if(checked)
                    accountWide = true;
                else
                    accountWide = false;
                break;

        }
    }
}
