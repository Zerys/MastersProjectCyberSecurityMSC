package com.example.finalyearproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import static android.content.ContentValues.TAG;

public class p_landing_page extends AppCompatActivity {
    // Define vars

    p_session_manager session;
    TextView var1, var2, var3, var4, var5, var6, var7, tv;
    HashMap<String,String> hashMap = new HashMap<>();
    String finalResult;
    TableLayout var8;
    p_httpsParser httpParser = new p_httpsParser();
    // Set url link
    String HttpURL = "https://192.168.0.12/lnkn/myAccounts.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_landing_page);
        overridePendingTransition(0, 0);
        // Run a series of checks, such as login checks, and grab current users account details.
        session = new p_session_manager(getApplicationContext());
        HashMap<String, String> user = session.getClientDetails();
        session.loginCheck();
        // Function call for loading my accounts info on the main page if existing.
        MyAccounts();
        // Basic greetings message.
        String Forename = user.get(p_session_manager.KEY_Forename);
        String Surname = user.get(p_session_manager.KEY_Surname);
        tv = findViewById(R.id.placeholder_name);
        tv.setText(String.format("%s%s", "Welcome, " + " " + Forename,  " " + Surname));
    }

    public void MyAccounts(){
        @SuppressLint("StaticFieldLeak")
        class MyAccountsClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() { super.onPreExecute(); }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                try {

                    // Main incoming connection to get incoming details and then do some formatting with them.
                    // So that they get displayed on the page in a pretty fashion.

                    JSONObject jsonObject = new JSONObject(httpResponseMsg);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String CA_Contact_Reference = jsonObject1.getString("CA_Contact_Reference");
                    String CA_Account_Name = jsonObject1.getString("CA_Account_Name");
                    String CD_Customer_Number = jsonObject1.getString("CD_Customer_Number");
                    String CF_Account_Number = jsonObject1.getString("CF_Account_Number");
                    String CF_Sort_Code = jsonObject1.getString("CF_Sort_Code");
                    String CA_Account_Owner = jsonObject1.getString("CA_Account_Owner");
                    String CA_Account_Balance = jsonObject1.getString("CA_Account_Balance");

                    var1 = findViewById(R.id.a_ref);
                    var2 = findViewById(R.id.acc_accName);
                    var3 = findViewById(R.id.ac_cn);
                    var4 = findViewById(R.id.a_accNub);
                    var5 = findViewById(R.id.ac_sortcode);
                    var6 = findViewById(R.id.ac_account_owner);
                    var7 = findViewById(R.id.a_balance);
                    var1.setVisibility(View.VISIBLE);
                    var2.setVisibility(View.VISIBLE);
                    var3.setVisibility(View.VISIBLE);
                    var4.setVisibility(View.VISIBLE);
                    var5.setVisibility(View.VISIBLE);
                    var6.setVisibility(View.VISIBLE);
                    var7.setVisibility(View.VISIBLE);
                    var8 = findViewById(R.id.tableLayout1);
                    var8.setVisibility(View.VISIBLE);
                    var1.setText(CA_Contact_Reference);
                    var2.setText(CA_Account_Name);
                    var3.setText(CD_Customer_Number);
                    var4.setText(CF_Account_Number);
                    var5.setText(CF_Sort_Code);
                    var6.setText(CA_Account_Owner);
                    var7.setText(String.format("%s%s", getString(R.string.poundsign), CA_Account_Balance));
                }
                catch (JSONException e){ Log.e(TAG, "My Account's Failed to load,  Error = " + e.toString()); }
            }
            @Override
            protected String doInBackground(String... params) {
                // Customer number is used as reference as to what account information to pull.
                HashMap<String, String> user = session.getClientDetails();
                String CustomerNumber = user.get(p_session_manager.KEY_CustomerNumber);
                hashMap.put("txtCustomerNumber",CustomerNumber);
                try {
                    finalResult = httpParser.postRequest(hashMap, HttpURL);
                } catch (IOException e) { e.printStackTrace(); }
                return finalResult;
            }
        }
        MyAccountsClass myAccountsClass = new MyAccountsClass();
        myAccountsClass.execute();
    }
    // Navigation
    public void money_nav_icon(View view) {
        finish();
        startActivity(new Intent(p_landing_page.this, p_make_a_payment_page.class));
    }
    public void charts_nav_icon(View view) {
        finish();
        startActivity(new Intent(p_landing_page.this, p_your_accounts_page.class));
    }
    public void settings_nav_icon(View view) {
        startActivity(new Intent(p_landing_page.this, p_settings_page.class));
    }
}
