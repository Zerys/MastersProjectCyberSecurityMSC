package com.example.finalyearproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class p_your_accounts_page extends AppCompatActivity {
    p_session_manager session;
    TextView your_customer_number, the_account_number, the_account_reference_number, account_balance, account_owner, the_account_sort_code;
    EditText name_of_your_account;
    String var1, var2, var3, var4, var5, var6, var7;
    public int maxLength = 7;
    HashMap<String,String> hashMap = new HashMap<>();
    String finalResult, randomint, randomint2, randsortcode1, randsortcode2, randsortcode3;
    p_httpsParser httpParser = new p_httpsParser();
    String HttpURL = "https://192.168.0.12/lnkn/createMonetaryAccount.php";
    Boolean checkField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_your_accounts);
        overridePendingTransition(0, 0);

        // Basic checks

        session = new p_session_manager(getApplicationContext());
        session.loginCheck();

        // Used to generate a random UUID to be used later.
        String uuid = UUID.randomUUID().toString();
        if(uuid.length() > maxLength){
            uuid = uuid.substring(0, maxLength);
        }

        HashMap<String, String> user = session.getClientDetails();
        String CustomerNumber = user.get(p_session_manager.KEY_CustomerNumber);
        String Forename = user.get(p_session_manager.KEY_Forename);
        String Surname = user.get(p_session_manager.KEY_Surname);

        // Handles account number and account balance.
        // Creates random gen information, useful for testing and demonstrating.
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        int n2 = 100000 + rnd.nextInt(900000);
        randomint = Integer.toString(n);
        randomint2 = Integer.toString(n2);

        // Handles the sort code generation randomly.
        Random ran = new Random();
        int x1 = ran.nextInt(99);
        int x2 = ran.nextInt(99);
        int x3 = ran.nextInt(99);
        randsortcode1 = Integer.toString(x1);
        randsortcode2 = Integer.toString(x2);
        randsortcode3 = Integer.toString(x3);

        your_customer_number = findViewById(R.id.your_customer_number);
        the_account_number = findViewById(R.id.the_account_number);
        the_account_sort_code = findViewById(R.id.the_account_sort_code);
        name_of_your_account = findViewById(R.id.name_of_your_account);
        the_account_reference_number = findViewById(R.id.the_account_reference_number);
        account_balance = findViewById(R.id.account_balance);
        account_owner = findViewById(R.id.account_owner);


        // Field checking and catching errors if anything goes wrong, it shouldn't but this
        // is a safety net.
        your_customer_number.setText(CustomerNumber);
        the_account_reference_number.setText(uuid);
        if (!randomint.equals("null")) {
            the_account_number.setText(randomint);
        } else{the_account_number.setText("Error");}
        if (!randomint.equals("null")) {
            account_balance.setText(randomint2);
        } else{the_account_number.setText("Error");}
        if (!randsortcode1.equals("null") || !randsortcode2.equals("null") || !randsortcode3.equals("null")) {
            the_account_sort_code.setText(String.format("%s", x1 + "-" + x2 + "-" + x3));
        } else{the_account_number.setText("Error");}
        account_owner.setText(String.format("%s%s", Forename,  " " + Surname));
    }

    // Standard sending off client information to the server so tha the account can be registered.
    public void CreateMonetaryAccount(final String ca_account_customerNumber, final String ca_account_owner, final String ca_account_reference,
                                      final String ca_account_number, final String ca_account_sortCode, final String ca_account_name, String ca_account_balance){

        @SuppressLint("StaticFieldLeak")
        class CreateMonetaryAccountClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                Toast.makeText(p_your_accounts_page.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                startActivity(new Intent(p_your_accounts_page.this, p_landing_page.class));
                finish();
            }
            @Override
            protected String doInBackground(String... params) {
                hashMap.put("txtCustomerNumber",params[0]);
                hashMap.put("txtAccountOwner",params[1]);
                hashMap.put("txtAccountReference",params[2]);
                hashMap.put("txtAccountNumber",params[3]);
                hashMap.put("txtAccountSortCode",params[4]);
                hashMap.put("txtAccountName",params[5]);
                hashMap.put("txtAccountBalance",params[6]);
                try {
                    finalResult = httpParser.postRequest(hashMap, HttpURL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return finalResult;
            }
        }
        CreateMonetaryAccountClass createMonetaryAccountClass = new CreateMonetaryAccountClass();
        createMonetaryAccountClass.execute(ca_account_customerNumber,ca_account_owner, ca_account_reference, ca_account_number, ca_account_sortCode, ca_account_name, ca_account_balance);
    }

    // Field check.
    public void isEmpty() {
        var1 = your_customer_number.getText().toString();
        var2 = account_owner.getText().toString();
        var3 = the_account_reference_number.getText().toString();
        var4 = the_account_number.getText().toString();
        var5 = the_account_sort_code.getText().toString();
        var6 = name_of_your_account.getText().toString();
        var7 = account_balance.getText().toString();
        checkField = var4.length() != 0 && var5.length() != 0 && var6.length() != 0 && var7.length() != 0;
    }
    // On click function to start the other processes.
    public void create_account(View view) {
        isEmpty();
        if(checkField){
            CreateMonetaryAccount(var1, var2, var3, var4, var5, var6, var7);
        }
        else{
            Toast.makeText(p_your_accounts_page.this, "Error", Toast.LENGTH_LONG).show();
        }
    }

    // navigation
    public void make_a_payment_nav(View view) {
        finish();
        startActivity(new Intent(p_your_accounts_page.this, p_make_a_payment_page.class));
    }
    public void settings_nav_icont(View view) {
        finish();
        startActivity(new Intent(p_your_accounts_page.this, p_settings_page.class));
    }
    public void home_nav_icon(View view) {
        startActivity(new Intent(p_your_accounts_page.this, p_landing_page.class));
    }
}
