package com.example.mealer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MealActivity extends AppCompatActivity {
    private DatabaseReference databaseMeals;
    private TextView mealNameTextView, mealTypeTextView, priceTextView;
    private Button buttonOfferMeal;
    private Button buttonUnofferMeal;
    private Button buttonDeleteMeal;

    private String mealId;
    private List<Meal> meals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        mealNameTextView = (TextView) findViewById(R.id.mealNameTextView);
        mealTypeTextView = (TextView) findViewById(R.id.mealTypeTextView);
        priceTextView = (TextView) findViewById(R.id.priceTextView);

        Intent intent = getIntent();
        mealId = intent.getStringExtra("id");
        String mealName = intent.getStringExtra("name");
        String mealType = intent.getStringExtra("type");
        String price = intent.getStringExtra("price");

        mealNameTextView.setText(mealName);
        mealTypeTextView.setText(mealType);
        priceTextView.setText(price);

        databaseMeals = FirebaseDatabase.getInstance().getReference("menu");
        databaseMeals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                meals.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Meal meal = postSnapshot.getValue(Meal.class);
                    meals.add(meal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buttonOfferMeal = (Button) findViewById(R.id.btn_offer);
        buttonUnofferMeal = (Button) findViewById(R.id.btn_unoffer);
        buttonDeleteMeal = (Button) findViewById(R.id.btn_delete);

        buttonOfferMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseMeals.child(mealId).child("status").setValue("Offered");
                Toast.makeText(getApplicationContext(), "Meal offered!", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        buttonUnofferMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseMeals.child(mealId).child("status").setValue("Unoffered");
                Toast.makeText(getApplicationContext(), "Meal unoffered!", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        buttonDeleteMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Meal x : meals) {
                    if (x.getStatus().equals("Offered")) {
                        Toast.makeText(getApplicationContext(), "Cannot delete offered meal!", Toast.LENGTH_LONG).show();
                        continue;
                    }
                    databaseMeals.child(mealId).removeValue();
                    Toast.makeText(getApplicationContext(), "Meal deleted!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
