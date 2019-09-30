package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class p_logout_page extends AppCompatActivity {
    p_session_manager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_logout_page);
        overridePendingTransition(0, 0);

        // Basic login check

        session = new p_session_manager(getApplicationContext());
        session.loginCheck();
    }

    // Navigation and calls the session manager to clear user data in shared preferences.
    public void settings_home(View view) {
        finish();
        startActivity(new Intent(p_logout_page.this, p_settings_page.class));
    }
    public void logout_btn(View view) {
        session.logoutUser();
        finish();
        startActivity(new Intent(p_logout_page.this, p_login_page.class));
        Toast.makeText(p_logout_page.this, "Logout Completed", Toast.LENGTH_LONG).show();
    }
    public void cancel_btn(View view) {
        finish();
        startActivity(new Intent(p_logout_page.this, p_settings_page.class));
    }
    public void home_nav_icon(View view) {
        finish();
        startActivity(new Intent(p_logout_page.this, p_landing_page.class));
    }
    public void make_a_payment_nav(View view) {
        finish();
        startActivity(new Intent(p_logout_page.this, p_make_a_payment_page.class));
    }
    public void recent_transactions_nav(View view) {
        finish();
        startActivity(new Intent(p_logout_page.this, p_your_accounts_page.class));
    }
    public void settings_nav_icon(View view) {
        finish();
        startActivity(new Intent(p_logout_page.this, p_settings_page.class));
    }
}
