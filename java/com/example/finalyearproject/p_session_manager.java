package com.example.finalyearproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.HashMap;


public class p_session_manager
{

    // A class for storing a users information locally and privately within Android.

    private SharedPreferences sp;
    private SharedPreferences.Editor e;
    @SuppressLint("StaticFieldLeak")
    static Context _context;

    // Define base entries that will be used across the application.

    private static final String PREF_NAME = "MyPrefs";
    private static final String IS_LOGGED_IN = "IsLoggedIn";
    public static final String KEY_CustomerNumber = "Customer_Number";
    public static final String KEY_SSNNumber = "SSN_Number";
    public static final String KEY_Forename = "Forename";
    public static final String KEY_Surname = "Surname";
    public static final String KEY_Address = "Address";
    public static final String KEY_Postcode = "Postcode";
    public static final String KEY_Phonenumber = "Phonenumber";
    public static final String KEY_Dateofbirth = "Dateofbirth";
    public static final String KEY_Emailaddress = "Emailaddress";
    public static final String KEY_Currentlogon = "Currentlogon";
    public static final String KEY_Lastlogon = "Lastlogon";
    public static final String KEY_Lastunsuccessfullogon = "Lastunsuccessfullogon";


    // Main call to use the context of the session manger anywhere.

    @SuppressLint("CommitPrefEdits")
    public p_session_manager(Context context)
    {
        int PRIVATE_MODE = 0;
        _context = context;
        sp = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        e = sp.edit();

    }


    // When data is sent to this method function it puts the following fields within the
    // values and then confirms the change.
    public void GenerateUserSession(String Customer_Number, String SSN_Number,
                                    String Forename, String Surname, String Address,
                                    String Postcode, String Phonenumber, String Dateofbirth,
                                    String Emailaddress, String Currentlogon, String Lastlogon,
                                    String Lastunsuccessfullogon) {
        e.putBoolean(IS_LOGGED_IN, true);
        e.putString(KEY_CustomerNumber, Customer_Number);
        e.putString(KEY_SSNNumber, SSN_Number);
        e.putString(KEY_Forename, Forename);
        e.putString(KEY_Surname, Surname);
        e.putString(KEY_Address, Address);
        e.putString(KEY_Postcode, Postcode);
        e.putString(KEY_Phonenumber, Phonenumber);
        e.putString(KEY_Dateofbirth, Dateofbirth);
        e.putString(KEY_Emailaddress, Emailaddress);
        e.putString(KEY_Currentlogon, Currentlogon);
        e.putString(KEY_Lastlogon, Lastlogon);
        e.putString(KEY_Lastunsuccessfullogon, Lastunsuccessfullogon);
        e.apply();
    }

    // A login check that can be called when referenced, it checks against the known value.
    private boolean isClientLoggedIn() {
        return sp.getBoolean(IS_LOGGED_IN, false);
    }

    // Uses the above function to check the value and then takes action accordingly.
    // Most times redirecting the user to the login page.
    public void loginCheck() {
        if (!this.isClientLoggedIn()) {
            Intent intent;
            intent = new Intent(_context, p_login_page.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(intent);
        }
    }

    // Logs out the user and clears their data stored in shared preferences.
    public void logoutUser() {
        e.clear();
        e.commit();
        Intent intent = new Intent(_context, p_login_page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(intent);
    }

    // A method to return data stored within shared preferences variables.
    public HashMap<String, String> getClientDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_CustomerNumber, sp.getString(KEY_CustomerNumber, null));
        user.put(KEY_SSNNumber, sp.getString(KEY_SSNNumber, null));
        user.put(KEY_Forename, sp.getString(KEY_Forename, null));
        user.put(KEY_Surname, sp.getString(KEY_Surname, null));
        user.put(KEY_Address, sp.getString(KEY_Address, null));
        user.put(KEY_Postcode, sp.getString(KEY_Postcode, null));
        user.put(KEY_Phonenumber, sp.getString(KEY_Phonenumber, null));
        user.put(KEY_Dateofbirth, sp.getString(KEY_Dateofbirth, null));
        user.put(KEY_Emailaddress, sp.getString(KEY_Emailaddress, null));
        user.put(KEY_Currentlogon, sp.getString(KEY_Currentlogon, null));
        user.put(KEY_Lastlogon, sp.getString(KEY_Lastlogon, null));
        user.put(KEY_Lastunsuccessfullogon, sp.getString(KEY_Lastunsuccessfullogon, null));
        return user;
    }
}