package com.example.finalyearproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.HashMap;

public class p_make_a_payment_page extends AppCompatActivity {
    p_session_manager session;
    EditText payees_customer_number, payees_account_number, payees_reference, payees_sort_code, amount_your_paying;
    TextView your_customer_number;
    String var1, var2, var3, var4, var5, var6;
    HashMap<String,String> hashMap = new HashMap<>();
    String finalResult;
    Boolean checkField;
    p_httpsParser httpParser = new p_httpsParser();
    String HttpURL = "https://192.168.0.12/lnkn/clientPay.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_make_a_payment);
        overridePendingTransition(0, 0);
        session = new p_session_manager(getApplicationContext());
        session.loginCheck();
        HashMap<String, String> user = session.getClientDetails();
        String CustomerNumber = user.get(p_session_manager.KEY_CustomerNumber);

        your_customer_number = findViewById(R.id.your_customer_number);
        payees_customer_number = findViewById(R.id.payees_customer_number);
        payees_account_number = findViewById(R.id.payees_account_number);
        payees_reference  = findViewById(R.id.payees_reference);
        payees_sort_code = findViewById(R.id.payees_sort_code);
        amount_your_paying = findViewById(R.id.amount_your_paying);
        your_customer_number.setText(CustomerNumber);
    }
    public void ClientPayment(final String R_forename, final String R_Surname, final String R_Living_Address,
                              final String R_Postcode, final String R_Phone_Number, final String R_DOB){
        @SuppressLint("StaticFieldLeak")
        class ClientPaymentClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() { super.onPreExecute(); }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                // A toast popup to show off confirmation.
                Toast.makeText(p_make_a_payment_page.this, "Payment Successful. A total of: £" + var6 + " was sent.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(p_make_a_payment_page.this, p_landing_page.class));
                finish();
            }

            // Params to be entered.
            @Override
            protected String doInBackground(String... params) {
                hashMap.put("txtCustomerNumber_personPaying",params[0]);
                hashMap.put("txtCustomerNumber_payee",params[1]);
                hashMap.put("txtAccountNumber",params[2]);
                hashMap.put("txtReferenceNumber",params[3]);
                hashMap.put("txtSortCode",params[4]);
                hashMap.put("txtAmountPaid",params[5]);
                try {
                    finalResult = httpParser.postRequest(hashMap, HttpURL);
                } catch (IOException e) { e.printStackTrace(); }
                return finalResult;
            }
        }
        ClientPaymentClass clientPaymentClass = new ClientPaymentClass();
        clientPaymentClass.execute(R_forename,R_Surname,R_Living_Address, R_Postcode, R_Phone_Number, R_DOB);
    }

    // Empty field check
    public void isEmpty() {
        var1 = your_customer_number.getText().toString();
        var2 = payees_customer_number.getText().toString();
        var3 = payees_account_number.getText().toString();
        var4 = payees_reference.getText().toString();
        var5 = payees_sort_code.getText().toString();
        var6 = amount_your_paying.getText().toString();
        checkField = !TextUtils.isEmpty(var1) && !TextUtils.isEmpty(var2) && !TextUtils.isEmpty(var3) &&
                !TextUtils.isEmpty(var4) && !TextUtils.isEmpty(var5) && !TextUtils.isEmpty(var6) && !var1.equals(var2);
    }
    public void onClick(View view) {
        isEmpty();
        if(checkField){
            ClientPayment(var1, var2, var3, var4, var5, var6);
        }
        else{
            Toast.makeText(p_make_a_payment_page.this, "Not all form fields are filled out.", Toast.LENGTH_LONG).show();
        }
    }


// Navigation
    public void recent_transactions_nav(View view) {
        finish();
        startActivity(new Intent(p_make_a_payment_page.this, p_your_accounts_page.class));
    }
    public void settings_nav_icon(View view) {
        finish();
        startActivity(new Intent(p_make_a_payment_page.this, p_settings_page.class));
    }
    public void home_nav_icon(View view) {
        startActivity(new Intent(p_make_a_payment_page.this, p_landing_page.class));
    }
    public void cancel_btn(View view) {
        finish();
        startActivity(new Intent(p_make_a_payment_page.this, p_landing_page.class));
    }
}


