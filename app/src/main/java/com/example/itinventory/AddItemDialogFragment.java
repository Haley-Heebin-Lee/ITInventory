package com.example.itinventory;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddItemDialogFragment extends DialogFragment implements View.OnClickListener {
    private EditText add_name, add_amount, add_desc;
    private String location;

    public AddItemDialogFragment() {
    }

    public interface AddItemDialogListener {
        void onAddDialog(String inputName, int inputAmount, @Nullable String inputDesc, String location);
    }

    private AddItemDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AddItemDialogListener)getActivity();
        }
        catch (ClassCastException e) {
            Log.e("Dialog", "onAttach: ClassCastException: "
                    + e.getMessage());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_item_fragment, container, false);

        view.findViewById(R.id.radio_evans).setOnClickListener(this);
        view.findViewById(R.id.radio_rh).setOnClickListener(this);
        add_name = view.findViewById(R.id.add_name);
        add_amount = view.findViewById(R.id.add_amount);
        add_desc = view.findViewById(R.id.add_desc);
        TextView mActionCancel = view.findViewById(R.id.action_cancel);
        TextView mActionOk = view.findViewById(R.id.action_ok);

        add_name.requestFocus();
        add_amount.requestFocus();
        add_desc.requestFocus();

        mActionCancel.setOnClickListener(
                v -> {
                    Log.d("dialog", "closing dialog");
                    getDialog().dismiss();
                });

        mActionOk.setOnClickListener(
                v -> {
                    Log.d("dialog", "saving inventory item");
                    if(add_desc.getText().toString().isEmpty())
                        listener.onAddDialog(add_name.getText().toString(), Integer.parseInt(add_amount.getText().toString()), "", location);
                    else
                        listener.onAddDialog(add_name.getText().toString(), Integer.parseInt(add_amount.getText().toString()), add_desc.getText().toString(), location);

                    getDialog().dismiss();
                });
        return view;
    }
    @Override
    public void onClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_rh:
                if (checked)
                    location = "RH";
                    break;
            case R.id.radio_evans:
                if (checked)
                    location = "Evans";
                    break;
        }
    }
}
