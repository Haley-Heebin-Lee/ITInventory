package com.example.itinventory;

import android.util.Log;
import android.widget.DatePicker;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.License;
import com.amplifyframework.datastore.generated.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBManagerLicense {
    // Callback instance
    DBManagerLicense.itemObjCallback itemObjCallbackInstance;

    // Callback definition
    public interface itemObjCallback {
        void getLicenseList(List<License> licensesList);
    }

    // Callback setter
    public void setItemObjCallbackInstance(DBManagerLicense.itemObjCallback main) {
        itemObjCallbackInstance = main;

    }
    public Temporal.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year =  datePicker.getYear();
        String dayStr = Integer.toString(day);
        if(day<10)
            dayStr = "0"+day;
        String monthStr = Integer.toString(month);
        if(month<10)
            monthStr = "0"+month;

        return new Temporal.Date(year+"-"+monthStr+"-"+dayStr);
    }
    //create item
    public void addLicense(String name, boolean accountWide, DatePicker expiry) {
        License item = License.builder()
                .expiry(getDateFromDatePicker(expiry))
                .accountWide(accountWide)
                .name(name)
                .build();

        Amplify.DataStore.save(
                item,
                success -> Log.i("Amplify", "Added new Sub in License: " + success.item().getId()),
                error -> Log.e("Amplify", "Could not save Sub to DataStore", error)
        );
    }

    // Read item
    public void listLicenses() {
        Amplify.DataStore.query(License.class,
                items -> {
                    List<License> licensesList = new ArrayList<>(0);
                    while (items.hasNext()) {
                        License item = items.next();
                        licensesList.add(item);
                    }

                    itemObjCallbackInstance.getLicenseList(licensesList);
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );
    }

    //delete item
    public void deleteLicense(String name) {
        Amplify.DataStore.query(License.class, Where.matches(License.NAME.eq(name)),
                items -> {
                    License item = items.next();
                    String id = item.getId();

                    Amplify.DataStore.delete(item,
                            success -> Log.e("Amplify", "Successfully deleted"),
                            failure -> Log.e("Amplify", "Could not query DataStore", failure)

                    );
                    Amplify.DataStore.query(User.class, Where.matches(User.LICENSE_ID.eq(id)),
                        users ->{
                            while(users.hasNext()){
                                User user = users.next();
                                Amplify.DataStore.delete(user,
                                        success -> Log.e("Amplify", "Successfully deleted"),
                                        failure -> Log.e("Amplify", "Could not query DataStore", failure));
                            }

                        },error->Log.e("Amplify", "Could not query DataStore", error));
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );
    }

    // Update description
    public void updateDate(String name, DatePicker datePicker) {
        Amplify.DataStore.query(License.class, Where.matches(License.NAME.eq(name)),
                items -> {
                    License item = items.next();

                    License updatedItem = item.copyOfBuilder()
                            .expiry(getDateFromDatePicker(datePicker))
                            .build();

                    Amplify.DataStore.save(
                            updatedItem,
                            success -> Log.i("Amplify", "Item updated: " + success.item().getName()),
                            error -> Log.e("Amplify", "Could not save item to DataStore", error)
                    );

                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );
    }

    // add user to license
    public void addUser(String licenseName, String name, String email, DatePicker expiry) {
        Amplify.DataStore.query(License.class, Where.matches(License.NAME.eq(licenseName)),
                items -> {
                    License item = items.next();
                    String id = item.getId();
                    User user = User.builder()
                            .name(name)
                            .email(email)
                            .expiry(getDateFromDatePicker(expiry))
                            .licenseId(id)
                            .build();

                    Amplify.DataStore.save(
                            user,
                            success -> Log.i("Amplify", "User is saved " + success.item().getName()),
                            error -> Log.e("Amplify", "Could not save item to DataStore", error)
                    );



                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );
    }

    // delete user
    public void deleteUser(String licenseName, String name) {
        Amplify.DataStore.query(License.class, Where.matches(License.NAME.eq(licenseName)),
                items -> {
                    License item = items.next();
                    String id = item.getId();

                    Amplify.DataStore.query(User.class, Where.matches(User.LICENSE_ID.eq(id)),
                            lists->{
                                while(lists.hasNext()){
                                    User user = lists.next();
                                    if(user.getName().matches(name)){
                                        Amplify.DataStore.delete(user,
                                                success -> Log.e("Amplify", "Successfully deleted user"),
                                                failure -> Log.e("Amplify", "Could not query DataStore", failure)
                                        );
                                    }
                                }
                            },
                            error -> Log.e("Amplify", "Could not query DataStore from User", error));
                },
                failure -> Log.e("Amplify", "Could not query DataStore from License", failure)
        );
    }
}
