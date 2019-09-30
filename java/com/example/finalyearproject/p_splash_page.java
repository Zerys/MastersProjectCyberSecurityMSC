package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class p_splash_page extends AppCompatActivity {

    // Just a simple page on a screen.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_spash_screen);
        overridePendingTransition(0, 0);
    }

    public void splash_screen_welcome_btn(View view) {
        startActivity(new Intent(p_splash_page.this, p_login_page.class));
    }
}
