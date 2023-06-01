package com.example.itinventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void listBtn(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ListOfInventory.class);
        startActivity(intent);
    }
    public void licenseBtn(View view){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), LicenseExpiry.class);
        startActivity(intent);
    }
}