package com.example.itinventory;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Inventory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ListOfInventory extends AppCompatActivity
        implements DBManager.itemObjCallback,
        AddItemDialogFragment.AddItemDialogListener,
        UpdateDescDialogFragment.UpdateDescDialogListener {
    RecyclerView recyclerView;
    ListAdapter listAdapter;
    ArrayList<Inventory> inventoryList = new ArrayList<>(0);
    TextView list_txt;
    Button plus_btn, minus_btn, delete_btn, update_btn;
    DBManager dbManager;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);

        dbManager= ((MainApplication) getApplication()).getDbManager();
        dbManager.readItemList();
        dbManager.setItemObjCallbackInstance(this);

        list_txt = findViewById(R.id.list_txt);

        recyclerView = findViewById(R.id.recyclerView);
        listAdapter = new ListAdapter(inventoryList, new AdapterButtonListner() {
            @Override
            public void plusBtnClicked(int position) {
                dbManager.plusAmount(inventoryList.get(position).getName());
                listAdapter.notifyDataSetChanged();
                ListOfInventory.this.runOnUiThread(() -> Toast.makeText(ListOfInventory.this, inventoryList.get(position).getName()+"'s amount is increased by 1", Toast.LENGTH_SHORT).show());
                reloadPage();
            }

            @Override
            public void minusBtnClicked(int position) {
                dbManager.minusAmount(inventoryList.get(position).getName());
                listAdapter.notifyDataSetChanged();
                ListOfInventory.this.runOnUiThread(() -> Toast.makeText(ListOfInventory.this, inventoryList.get(position).getName()+"'s amount is decreased by 1", Toast.LENGTH_SHORT).show());
                reloadPage();
            }

            @Override
            public void deleteBtnClicked(int position) {
                dbManager.deleteItem(inventoryList.get(position).getName());
                listAdapter.notifyDataSetChanged();
                ListOfInventory.this.runOnUiThread(() -> Toast.makeText(ListOfInventory.this, inventoryList.get(position).getName()+" is deleted", Toast.LENGTH_SHORT).show());
                reloadPage();
            }

            @Override
            public void updateBtnClicked(int position) {
                //call fragment and type desc
                UpdateDescDialogFragment dialog = new UpdateDescDialogFragment();
                Bundle arg = new Bundle();
                arg.putString("itemName", inventoryList.get(position).getName());
                dialog.setArguments(arg);
                dialog.show(getSupportFragmentManager(), "UpdateDescDialogFragment");
            }
        });
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        plus_btn = findViewById(R.id.plus_btn);
        minus_btn = findViewById(R.id.minus_btn);
        delete_btn = findViewById(R.id.delete_btn);
        update_btn = findViewById(R.id.update_btn);

        BottomNavigationView botNav = findViewById(R.id.botNav);
        botNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.all_list) {
                //list all
                inventoryList.clear();
                runOnUiThread(() -> listAdapter.notifyDataSetChanged());
                dbManager.readItemList();
                reloadPage();
            }
            if (item.getItemId() == R.id.evans_list) {
                //list evans loc
                inventoryList.clear();
                runOnUiThread(() -> listAdapter.notifyDataSetChanged());
                dbManager.listByLocation("Evans");
            }
            if (item.getItemId() == R.id.rh_list) {
                //list rh loc
                inventoryList.clear();
                runOnUiThread(() -> listAdapter.notifyDataSetChanged());
                dbManager.listByLocation("RH");
            }
            return true;
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void getItemList(List<Inventory> listFromDB) {
        if (listFromDB.size() == 0) {
            runOnUiThread(() -> list_txt.setText(R.string.empty_list));
        } else {

            runOnUiThread(() -> list_txt.setText(R.string.listEquip));
            inventoryList.addAll(listFromDB);
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getItem(Inventory inventory) {
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_list_add:
                showAddDialog();
                return true;
            case R.id.menu_refresh:
                reloadPage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void reloadPage(){
        finish();
        startActivity(getIntent());
    }
    private void showAddDialog() {
        AddItemDialogFragment dialog = new AddItemDialogFragment();
        dialog.show(getSupportFragmentManager(), "AddItemFragment");
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onAddDialog(String inputName, int inputAmount, @Nullable String inputDesc, String location) {
        dbManager.addNewItem(inputName, inputAmount, inputDesc, location);
        listAdapter.notifyDataSetChanged();
        Toast.makeText(this, inputName + " is added", Toast.LENGTH_SHORT).show();
        reloadPage();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onUpdateDialog(String name, String desc) {
        // call dbManager.updateDesc(name, desc);
        dbManager.updateDesc(name, desc);
        listAdapter.notifyDataSetChanged();
        Toast.makeText(this, name + "'s description is updated", Toast.LENGTH_SHORT).show();
        reloadPage();
    }
}
