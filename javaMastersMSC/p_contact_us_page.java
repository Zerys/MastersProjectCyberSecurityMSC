package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class p_contact_us_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_contact_us);
        overridePendingTransition(0, 0);

    }

    // Mainly a filler page, contains some navigation and some basic data.

    public void settings_nav_home(View view) {
        finish();
        startActivity(new Intent(p_contact_us_page.this, p_settings_page.class));
    }

    public void home_nav_icon(View view) {
        finish();
        startActivity(new Intent(p_contact_us_page.this, p_landing_page.class));
    }

    public void make_a_payment_nav(View view) {
        finish();
        startActivity(new Intent(p_contact_us_page.this, p_make_a_payment_page.class));
    }

    public void recent_transactions_nav(View view) {
        finish();
        startActivity(new Intent(p_contact_us_page.this, p_your_accounts_page.class));
    }
}
