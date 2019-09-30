package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class p_settings_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_settings);
        overridePendingTransition(0, 0);


    }

    // Top Navigation around the app.



    public void recent_transactions_nav(View view) {
        finish();
        startActivity(new Intent(p_settings_page.this, p_your_accounts_page.class));
    }
    public void home_nav_icon(View view) {
        finish();
        startActivity(new Intent(p_settings_page.this, p_landing_page.class));
    }

    // Settings Page Navigation to other pages.

    public void settings_nav_account_details(View view) {
        finish();
        startActivity(new Intent(p_settings_page.this, p_account_details_page.class));
    }

    public void settings_nav_contact_us(View view) {
        finish();
        startActivity(new Intent(p_settings_page.this, p_contact_us_page.class));
    }

    public void settings_nav_logout(View view) {
        finish();
        startActivity(new Intent(p_settings_page.this, p_logout_page.class));
    }


    public void make_a_payment_nav(View view) {
        finish();
        startActivity(new Intent(p_settings_page.this, p_make_a_payment_page.class));
    }
}
