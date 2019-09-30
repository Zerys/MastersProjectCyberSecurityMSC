package com.example.finalyearproject;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

public class p_register_page extends AppCompatActivity implements View.OnClickListener{
    EditText reg_forename, reg_surname, reg_living_address,
            reg_postcode, reg_phone_number, reg_date_of_birth,
            reg_email_address, reg_password, reg_confirm_password, reg_ssn_number;
    String var1, var2, var3, var4, var5, var6, var7, var10, var11;
    ImageButton btn;
    HashMap<String,String> hashMap = new HashMap<>();
    String finalResult;
    Boolean checkField;
    private ProgressBar progressBar;
    p_httpsParser httpParser = new p_httpsParser();
    String HttpURL = "https://192.168.0.12/lnkn/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_register_page);
        overridePendingTransition(0, 0);
        reg_forename = findViewById(R.id.customer_forename);
        reg_surname = findViewById(R.id.customer_surname);
        reg_living_address = findViewById(R.id.customer_address);
        reg_postcode = findViewById(R.id.customer_postcode);
        reg_phone_number = findViewById(R.id.customer_phone_number);
        reg_date_of_birth = findViewById(R.id.customer_date_of_birth);
        reg_email_address = findViewById(R.id.customer_email_address);
        reg_password = findViewById(R.id.customer_password);
        reg_confirm_password = findViewById(R.id.confirm_your_password);
        reg_ssn_number = findViewById(R.id.customer_ssn_number);
        btn = findViewById(R.id.register_btn);
        btn.setOnClickListener(this);
        progressBar = findViewById(R.id.indeterminateBar);
    }

    public void ClientRegistration(final String R_forename, final String R_Surname, final String R_Living_Address,
                                   final String R_Postcode, final String R_Phone_Number, final String R_DOB,
                                   final String R_Email, final String  R_SSN, final String R_Password){

        @SuppressLint("StaticFieldLeak")
        class ClientRegistrationClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() { super.onPreExecute(); progressBar.setVisibility(View.VISIBLE); }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressBar.setVisibility(View.INVISIBLE);
                if(!httpResponseMsg.equals("Register Failed<html>")) {
                    startActivity(new Intent(p_register_page.this, p_landing_page.class));
                    Toast.makeText(p_register_page.this, "Register Successful.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(p_register_page.this, p_login_page.class));
                    finish();
                }
                else {
                    Toast.makeText(p_register_page.this, "Register Failed.", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                hashMap.put("txtForename",params[0]);
                hashMap.put("txtSurname",params[1]);
                hashMap.put("txtLivingAddress",params[2]);
                hashMap.put("txtPostcode",params[3]);
                hashMap.put("txtPhoneNumber",params[4]);
                hashMap.put("txtDateOfBirth",params[5]);
                hashMap.put("txtEmailAddress",params[6]);
                hashMap.put("txtPassword",params[7]);
                hashMap.put("txtssnNumber",params[8]);

                try {
                    finalResult = httpParser.postRequest(hashMap, HttpURL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return finalResult;
            }
        }
        ClientRegistrationClass clientRegistrationClass = new ClientRegistrationClass();
        clientRegistrationClass.execute(R_forename,R_Surname,R_Living_Address, R_Postcode, R_Phone_Number, R_DOB, R_Email, R_SSN, R_Password);
    }

    // Empty fields check.
    public void isEmpty() {
        var1 = reg_forename.getText().toString();
        var2 = reg_surname.getText().toString();
        var3 = reg_living_address.getText().toString();
        var4 = reg_postcode.getText().toString();
        var5 = reg_phone_number.getText().toString();
        var6 = reg_date_of_birth.getText().toString();
        var7 = reg_email_address.getText().toString();
        var10 = reg_password.getText().toString();
        var11 = reg_confirm_password.getText().toString();

        checkField = !TextUtils.isEmpty(var1) && !TextUtils.isEmpty(var2) && !TextUtils.isEmpty(var3) &&
                !TextUtils.isEmpty(var4)
                && !TextUtils.isEmpty(var5)
                && !TextUtils.isEmpty(var6)
                && !TextUtils.isEmpty(var7)
                && !TextUtils.isEmpty(var10)
                && !TextUtils.isEmpty(var11)
                && var10.equals(var11);
    }

    // Starts the process also uses random number and letter gen for SSN number
    @Override
    public void onClick(View view) {
        isEmpty();
        if(checkField){
            progressBar.setVisibility(View.VISIBLE);
            int n = 4;
            Random r = new Random();
            byte[] b = new byte[n];
            r.nextBytes(b);
            BigInteger i = new BigInteger(b);
            String ssn = String.valueOf(i);
            ClientRegistration(var1, var2, var3, var4, var5, var6, var7, var10, ssn);
        }
        else{
            Toast.makeText(p_register_page.this, "Not all form fields are filled out. Or your SSN / Password fields do not match.", Toast.LENGTH_LONG).show();
        }
    }
    // navigation to return to previous page.
    public void cancel_btn(View view) {
        finish();
        startActivity(new Intent(p_register_page.this, p_login_page.class));
    }
}
