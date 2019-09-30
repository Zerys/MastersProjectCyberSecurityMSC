package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class p_account_details_page extends AppCompatActivity {
    p_session_manager session;
    TextView var1, var2, var3, var4, var5, var8, var9, var10, var11;

    // define vars

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_account_details);
        // Override the pending transition, when switching pages.
        overridePendingTransition(0, 0);

        // Setting up a static session on this page, checks the manager and does a login check.
        session = new p_session_manager(getApplicationContext());
        session.loginCheck();

        // Get already stored client details from the shared prefs and put them into variables.
        HashMap<String, String> user = session.getClientDetails();
        String CustomerNumber = user.get(p_session_manager.KEY_CustomerNumber);
        String SSNNumber = user.get(p_session_manager.KEY_SSNNumber);
        String Forename = user.get(p_session_manager.KEY_Forename);
        String Surname = user.get(p_session_manager.KEY_Surname);
        String Address = user.get(p_session_manager.KEY_Address);
        String Postcode = user.get(p_session_manager.KEY_Postcode);
        String Emailaddress = user.get(p_session_manager.KEY_Emailaddress);
        String Currentlogon = user.get(p_session_manager.KEY_Currentlogon);
        String Lastlogon = user.get(p_session_manager.KEY_Lastlogon);
        String Lastunsuccessfullogon = user.get(p_session_manager.KEY_Lastunsuccessfullogon);

        // Search for in page text fields.
        var1 = findViewById(R.id.customerID);
        var2 = findViewById(R.id.customer_ssn_number);
        var3 = findViewById(R.id.customer_fullname);
        var4 = findViewById(R.id.customer_address);
        var5 = findViewById(R.id.customer_postcode);
        var8 = findViewById(R.id.customer_email_address);
        var9 = findViewById(R.id.current_logon_time);
        var10 = findViewById(R.id.last_logon_time);
        var11 = findViewById(R.id.last_unsucessful_logon_time);

        // Bind vars in shared prefs to text fields on page.
        var1.setText(CustomerNumber);
        var2.setText(SSNNumber);
        // Some text formatting to get the forename and surname to line up properly.
        var3.setText(String.format("%s%s", Forename,  " " + Surname));
        var4.setText(Address);
        var5.setText(Postcode);
        var8.setText(Emailaddress);
        // Timestamp related stuff.
        var9.setText(String.format("%s","Current Login Date: " + Currentlogon));

        if (!Lastlogon.equals("null")) {
            var10.setText(String.format("%s", "Previous Login Date: " + Lastlogon));
        } else{var9.setText("No logon data");}

        if (!Lastunsuccessfullogon.equals("null")) {
            var11.setText(String.format("%s", "Last failed Login: " +Lastunsuccessfullogon));
        } else{var11.setText("No logon data");}
       // var10.setText(Lastlogon);
       // var11.setText(Lastunsuccessfullogon);
    }

    // Top bar navigation
    public void home_nav_icon(View view) {
        finish();
        startActivity(new Intent(p_account_details_page.this, p_landing_page.class));
    }
    public void settings_nav_home(View view) {
        finish();
        startActivity(new Intent(p_account_details_page.this, p_settings_page.class));
    }
    public void make_a_payment_nav(View view) {
        finish();
        startActivity(new Intent(p_account_details_page.this, p_make_a_payment_page.class));
    }
    public void recent_transactions_nav(View view) {
        finish();
        startActivity(new Intent(p_account_details_page.this, p_your_accounts_page.class));
    }
}
