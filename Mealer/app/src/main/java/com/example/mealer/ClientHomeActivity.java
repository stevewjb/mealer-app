package com.example.mealer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ClientHomeActivity extends AppCompatActivity {

    private String clientId, mealName, mealType, cuisineType;
    private EditText mealNameText, mealTypeText, cuisineTypeText;
    private Button buttonSearch, buttonMyPurchase;
    private DatabaseReference databaseMeals;
    private List<Meal> meals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        mealNameText = (EditText) findViewById(R.id.searchMealName);
        mealTypeText = (EditText) findViewById(R.id.searchMealType);
        cuisineTypeText = (EditText) findViewById(R.id.searchCuisineType);
        buttonSearch = (Button) findViewById(R.id.btn_search);
        buttonMyPurchase = (Button) findViewById(R.id.btn_myPurchase);
        Intent intent = getIntent();

        databaseMeals = FirebaseDatabase.getInstance().getReference("menu");
        databaseMeals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                meals.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    Meal meal = postSnapshot.getValue(Meal.class);
                    meals.add(meal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyInputs()) {
                    Intent intent1 = new Intent(getApplicationContext(), ClientSearchActivity.class);
                    intent1.putExtra("id", intent.getStringExtra("id"));
                    intent1.putExtra("mealname", mealName);
                    intent1.putExtra("mealtype", mealType);
                    intent1.putExtra("cuisinetype", cuisineType);
                    startActivity(intent1);
                }
            }
        });

        buttonMyPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), ClientMyPurchaseActivity.class);
                intent2.putExtra("id", intent.getStringExtra("id"));
                startActivity(intent2);
            }
        });
    }

    private boolean verifyInputs() {
        mealName = mealNameText.getText().toString().trim();
        mealType = mealTypeText.getText().toString().trim();
        cuisineType = cuisineTypeText.getText().toString().trim();

        if (mealName.equals("") && mealType.equals("") && cuisineType.equals("")) {
            Toast.makeText(getApplicationContext(), "Please specify at least one field!", Toast.LENGTH_LONG).show();
            return false;
        }
        for (Meal x : meals) {
            if (!mealName.equals("") && !x.getMealName().equals(mealName)) {
                Toast.makeText(getApplicationContext(), "The meal you looking for does not exist!", Toast.LENGTH_LONG).show();
                return false;
            }
            if (!mealType.equals("") && !x.getMealType().equals(mealType)) {
                Toast.makeText(getApplicationContext(), "The meal type you looking for does not exist!", Toast.LENGTH_LONG).show();
                return false;
            }
            if (!cuisineType.equals("") && !x.getCuisineType().equals(cuisineType)) {
                Toast.makeText(getApplicationContext(), "The cuisine type you looking for does not exist!", Toast.LENGTH_LONG).show();
                return false;
            }
            break;
        }
        return true;
    }
}
