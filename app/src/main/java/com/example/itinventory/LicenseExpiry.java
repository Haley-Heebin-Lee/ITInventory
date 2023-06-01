package com.example.itinventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.datastore.generated.model.License;

import java.util.ArrayList;
import java.util.List;

public class LicenseExpiry extends AppCompatActivity
        implements DBManagerLicense.itemObjCallback,
        AddLicenseDialogFragment.AddLicenseDialogListener,
        UpdateLicenseDialogFragment.UpdateLicenseDialogListener,
        DeleteLicenseDialogFragment.DeleteLicenseDialogListener{

    DBManagerLicense dbManager;
    ArrayList<String> licenseNames = new ArrayList<>(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_menu);

        dbManager= ((MainApplication) getApplication()).getDbManagerLicense();
        dbManager.setItemObjCallbackInstance(this);
        dbManager.listLicenses();
    }

    public void newSubBtn(View view){
        AddLicenseDialogFragment dialog = new AddLicenseDialogFragment();
        dialog.show(getSupportFragmentManager(), "AddLicenseDialogFragment");
    }
    public void updateSubBtn(View view){
        UpdateLicenseDialogFragment dialog = new UpdateLicenseDialogFragment();
        Bundle arg = new Bundle();
        arg.putStringArrayList("licenseNames", licenseNames);
        dialog.setArguments(arg);
        dialog.show(getSupportFragmentManager(), "UpdateLicenseDialogFragment");
    }
    public void deleteSubBtn(View view){
        DeleteLicenseDialogFragment dialog = new DeleteLicenseDialogFragment();
        Bundle arg = new Bundle();
        arg.putStringArrayList("licenseNames", licenseNames);
        dialog.setArguments(arg);
        dialog.show(getSupportFragmentManager(), "UpdateLicenseDialogFragment");
    }
    public void listSubBtn(View view){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ListOfLicenses.class);
        startActivity(intent);
    }

    @Override
    public void getLicenseList(List<License> licensesList) {
        for(int i=0; i<licensesList.size(); i++){
            licenseNames.add(licensesList.get(i).getName());
        }

    }

    @Override
    public void onLicenseDialog(String name, boolean accountWise, DatePicker expiry) {
        dbManager.addLicense(name, accountWise, expiry);
        Toast.makeText(this, name + " is added", Toast.LENGTH_SHORT).show();
        licenseNames.add(name);
    }

    @Override
    public void onLicenseUpdateDialog(String name, DatePicker expiry) {
        dbManager.updateDate(name, expiry);
        Toast.makeText(this, name+"'s expiry date is updated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLicenseDeleteDialog(String name) {
        dbManager.deleteLicense(name);
        Toast.makeText(this, name+"is removed", Toast.LENGTH_SHORT).show();
    }
}
