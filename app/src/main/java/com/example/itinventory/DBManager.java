package com.example.itinventory;

import android.util.Log;

import androidx.annotation.Nullable;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Inventory;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    // Callback instance
    itemObjCallback itemObjCallbackInstance;

    // Callback definition
    public interface itemObjCallback {
        void getItemList(List<Inventory> inventoryList);
        void getItem(Inventory inventory);
    }

    // Callback setter
    public void setItemObjCallbackInstance(itemObjCallback main) {
        itemObjCallbackInstance = main;

    }

    //create item
    public void addNewItem(String name, int amount, @Nullable String desc, String location) {
        String formattedName = "";
        if(location.matches("RH")){
            formattedName = name.toLowerCase();
        }else{
            formattedName = name.toUpperCase();
        }

        Inventory item = Inventory.builder()
                .name(formattedName)
                .amount(amount)
                .location(location)
                .description(desc)
                .build();

        Amplify.DataStore.save(
                item,
                success -> {
                    Log.i("Amplify", "Added new item to Inventory: " + success.item().getId());
                },
                error -> {
                    Log.e("Amplify", "Could not save item to DataStore", error);
                }
        );
    }

    // Read item
    public void readItemList() {
        Amplify.DataStore.query(Inventory.class,
                items -> {
                    List<Inventory> inventoryList = new ArrayList<>(0);
                    while (items.hasNext()) {
                        Inventory item = items.next();
                        inventoryList.add(item);
                        Log.i("Amplify", "Name: " + item.getName() + " - Amount: " + item.getAmount());
                    }

                    itemObjCallbackInstance.getItemList(inventoryList);
                },
                failure -> {
                    Log.e("Amplify", "Could not query DataStore", failure);
                }
        );
    }
    // Read item
    public void listByLocation(String location) {
        Amplify.DataStore.query(Inventory.class, Where.matches(Inventory.LOCATION.eq(location)),
                items -> {
                    List<Inventory> inventoryList = new ArrayList<>(0);
                    while (items.hasNext()) {
                        Inventory item = items.next();
                        inventoryList.add(item);
                        Log.i("Amplify", "Name: " + item.getName() + " - Amount: " + item.getAmount());
                    }

                    itemObjCallbackInstance.getItemList(inventoryList);
                },
                failure -> {
                    Log.e("Amplify", "Could not query DataStore", failure);
                }
        );
    }
    //delete item
    public void deleteItem(String name) {
        Amplify.DataStore.query(Inventory.class, Where.matches(Inventory.NAME.eq(name)),
                items -> {
                    Inventory item = items.next();
                    Amplify.DataStore.delete(item,
                            success -> {
                                itemObjCallbackInstance.getItem(item);
                            },
                            failure -> {
                                Log.e("Amplify", "Could not query DataStore", failure);
                            }

                    );
                },
                failure -> {
                    Log.e("Amplify", "Could not query DataStore", failure);
                }
        );
    }

    // Update description
    public void updateDesc(String name, String desc) {
        Amplify.DataStore.query(Inventory.class, Where.matches(Inventory.NAME.eq(name)),
                items -> {
                    Inventory item = items.next();

                    Inventory updatedItem = item.copyOfBuilder()
                            .description(desc)
                            .build();
                    //created updated subscribed object with new MP name
                    //save the data
                    Amplify.DataStore.save(
                            updatedItem,
                            success -> {
                                Log.i("Amplify", "Item updated: " + success.item().getName());
                                itemObjCallbackInstance.getItem(item);
                            },
                            error -> {
                                Log.e("Amplify", "Could not save item to DataStore", error);
                            }
                    );

                },
                failure -> {
                    Log.e("Amplify", "Could not query DataStore", failure);
                }
        );
    }

    // Increase amount
    public void plusAmount(String name) {
        Amplify.DataStore.query(Inventory.class, Where.matches(Inventory.NAME.eq(name)),
                items -> {
                    Inventory item = items.next();
                    int original = item.getAmount();
                    Inventory updatedItem = item.copyOfBuilder()
                            .amount(original+1)
                            .build();
                    //created updated subscribed object with new MP name
                    //save the data
                    Amplify.DataStore.save(
                            updatedItem,
                            success -> {
                                Log.i("Amplify", "Item updated: " + success.item().getName());
                                itemObjCallbackInstance.getItem(item);
                            },
                            error -> {
                                Log.e("Amplify", "Could not save item to DataStore", error);
                            }
                    );

                },
                failure -> {
                    Log.e("Amplify", "Could not query DataStore", failure);
                }
        );
    }

    // decrease amount
    public void minusAmount(String name) {
        Amplify.DataStore.query(Inventory.class, Where.matches(Inventory.NAME.eq(name)),
                items -> {
                    Inventory item = items.next();
                    int original = item.getAmount();
                    if(original > 0){
                        Inventory updatedItem = item.copyOfBuilder()
                                .amount(original-1)
                                .build();
                        //created updated subscribed object with new MP name
                        //save the data
                        Amplify.DataStore.save(
                                updatedItem,
                                success -> {
                                    Log.i("Amplify", "Item updated: " + success.item().getName());
                                    itemObjCallbackInstance.getItem(item);
                                },
                                error -> {
                                    Log.e("Amplify", "Could not save item to DataStore", error);
                                }
                        );
                    }else{
                        //alert the amount cannot go minus
                    }


                },
                failure -> {
                    Log.e("Amplify", "Could not query DataStore", failure);
                }
        );
    }
}
