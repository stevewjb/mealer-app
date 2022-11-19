package com.example.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    private TextView welcomeText, messageText;
    private Button buttonLogoff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent();
        welcomeText = (TextView) findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome! You are logged in as " + intent.getStringExtra("role"));

        messageText = (TextView) findViewById(R.id.messageText);

        Intent intent1 = new Intent(getApplicationContext(), AdminHomeActivity.class);
        if (intent.getStringExtra("role").equals("Administrator")) {
            startActivity(intent1);
        }

        Intent intent2 = new Intent(getApplicationContext(), CookHomeActivity.class);
        if (intent.getStringExtra("role").equals("Cook") && !intent.getStringExtra("status").equals("Suspend")) {
            startActivity(intent2);
        }
        if (intent.getStringExtra("status").equals("Suspend")) {
            messageText.setText("Your account has been suspended!");
        }

        buttonLogoff = (Button) findViewById(R.id.btn_logoff);
        buttonLogoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}