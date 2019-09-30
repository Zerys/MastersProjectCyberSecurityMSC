package com.example.finalyearproject;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
import static android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

public class p_login_page extends AppCompatActivity implements View.OnClickListener {
    EditText et1, et2, et3;
    String result, var1, var2, var3;
    Boolean check;
    ImageButton btn;
    p_httpsParser httpsParser = new p_httpsParser();
    p_session_manager session;
    private ProgressBar progressBar;
    HashMap<String, String> hashMap = new HashMap<>();
    String HttpURL = "https://192.168.0.12/lnkn/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_login_page);
        overridePendingTransition(0, 0);
        et1 = findViewById(R.id.customerID_textfield);
        et2 = findViewById(R.id.customerID_textfield1);
        et3 = findViewById(R.id.customerID_textfield2);
        btn = findViewById(R.id.btn_login);
        btn.setOnClickListener(this);
        progressBar = findViewById(R.id.indeterminateBar);
        session = new p_session_manager(getApplicationContext());
    }

    public void ClientLogin(final String CustomerID, final String CustomerPassword, final String Customer_SSN_Number) {
        @SuppressLint("StaticFieldLeak")
        class ClientLoginClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() { super.onPreExecute(); }
            @Override
            protected void onPostExecute(String httpsResponseMsg) {
                super.onPostExecute(httpsResponseMsg);
                System.out.println(httpsResponseMsg);

                // Sends off data to server, if the message is not equal to login failed which would get sent back
                // Continue the process.

                    if (!httpsResponseMsg.equals("Login Failed<html>")) {
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(new Intent(p_login_page.this, p_landing_page.class));
                        finish();
                        Toast.makeText(p_login_page.this, "Login Successful", Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonObject = new JSONObject(httpsResponseMsg);
                            JSONArray jsonArray = jsonObject.getJSONArray("result");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            String customer_number = jsonObject1.getString("CD_Customer_Number");
                            String ssn_number = jsonObject1.getString("CD_SSN_Number");
                            String Forename = jsonObject1.getString("CD_Forename");
                            String Surname = jsonObject1.getString("CD_Surname");
                            String Address = jsonObject1.getString("CD_Address");
                            String Postcode = jsonObject1.getString("CD_Post_Code");
                            String Phonenumber = jsonObject1.getString("CD_Phone_Number");
                            String Dateofbirth = jsonObject1.getString("CD_Date_Of_Birth");
                            String Emailaddress = jsonObject1.getString("CD_Email_Address");
                            String Currentlogon = jsonObject1.getString("TS_Current_Logon_Time");
                            String Lastlogon = jsonObject1.getString("TS_Last_Logon");
                            String Lastunsuccessfullogon = jsonObject1.getString("TS_Last_Unsuccessful_Logon");
                            session.GenerateUserSession(customer_number, ssn_number, Forename, Surname,
                                    Address, Postcode, Phonenumber, Dateofbirth, Emailaddress,
                                    Currentlogon, Lastlogon, Lastunsuccessfullogon);
                        } catch (Exception e) {
                            System.out.println("Fields were empty, try again");
                        }
                    } else {
                        Toast.makeText(p_login_page.this, "Login Failed. One or more of the fields are incorrect.", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
            }

            @Override
            protected String doInBackground(String... params) {
                // Parameters that are to be sent to the server.
                hashMap.put("txtSSN",params[0]);
                hashMap.put("txtCustomerNumber",params[1]);
                hashMap.put("txtPassword",params[2]);
                try {
                    result = httpsParser.postRequest(hashMap, HttpURL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }
        }
        ClientLoginClass clientLoginClass = new ClientLoginClass();
        clientLoginClass.execute(Customer_SSN_Number, CustomerID, CustomerPassword);
    }

    // Error checking if the text fields are empty.
    public void isEmpty() {
        var1 = et1.getText().toString();
        var2 = et2.getText().toString();
        var3 = et3.getText().toString();
        check = !TextUtils.isEmpty(var1) && !TextUtils.isEmpty(var2)&& !TextUtils.isEmpty(var3);
    }
    //Navigation to the register page.
    public void dont_have_an_account_btn(View view) {
        finish();
        startActivity(new Intent(p_login_page.this, p_register_page.class));
    }
    // Toggles hiding of the text or not.
    public void hide_text_btn(View view) {
        if (et1.getInputType() == 129) {
            et1.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            et1.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
    public void hide_text_btn1(View view) {
        if (et2.getInputType() == 129) {
            et2.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            et2.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
    public void hide_text_btn2(View view) {
        if (et3.getInputType() == 129) {
            et3.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            et3.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    // The main button on click function that activates the whole process seen above.
    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isAcceptingText()){
            imm.hideSoftInputFromWindow(et1.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(et2.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(et3.getWindowToken(), 0);
        }
        isEmpty();
        if (check) {
            progressBar.setVisibility(View.VISIBLE);
            ClientLogin(var1, var2, var3);
        } else {
            Toast.makeText(p_login_page.this, "Not all form fields are filled out. Or your information is incorrect.", Toast.LENGTH_LONG).show();
        }
    }
}
