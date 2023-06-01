package com.example.itinventory;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class UpdateDescDialogFragment extends DialogFragment {
    private EditText update_desc;
    private TextView update_txt;

    public UpdateDescDialogFragment() {
    }

    public interface UpdateDescDialogListener {
        void onUpdateDialog(String name, String desc);
    }

    private UpdateDescDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateDescDialogListener)getActivity();
        }
        catch (ClassCastException e) {
            Log.e("Dialog", "onAttach: ClassCastException: "
                    + e.getMessage());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_desc_fragment, container, false);

        Bundle bundle = getArguments();
        String name = bundle.getString("itemName","For Item Desc");

        update_desc = view.findViewById(R.id.update_desc);
        update_txt = view.findViewById(R.id.update_txt);
        TextView mActionCancel = view.findViewById(R.id.action_cancel);
        TextView mActionOk = view.findViewById(R.id.action_ok);

        update_txt.setText(name);
        update_desc.requestFocus();

        mActionCancel.setOnClickListener(
                v -> {
                    Log.d("dialog", "closing dialog");
                    getDialog().dismiss();
                });

        mActionOk.setOnClickListener(
                v -> {
                    Log.d("dialog", "saving inventory item");
                    listener.onUpdateDialog(update_txt.getText().toString(), update_desc.getText().toString());
                    getDialog().dismiss();
                });
        return view;
    }
}
