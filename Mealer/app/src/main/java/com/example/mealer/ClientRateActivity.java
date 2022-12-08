package com.example.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ClientRateActivity extends AppCompatActivity {

    private TextView cookNameTextView, mealNameTextView;
    private Button buttonRate;
    private Button buttonComplaint;
    private String cookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_rate);

        cookNameTextView = (TextView) findViewById(R.id.cookNameTextView);
        mealNameTextView = (TextView) findViewById(R.id.mealNameTextView);

        Intent intent = getIntent();
        cookId = intent.getStringExtra("cookId");
        cookNameTextView.setText(intent.getStringExtra("cookName"));
        mealNameTextView.setText(intent.getStringExtra("mealName"));
    }
}