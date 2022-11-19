package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CookAddMealActivity extends AppCompatActivity {

    private String mealName, mealType, cuisineType, ingredients, allergens, price, description;
    private EditText mealNameText, mealTypeText, cuisineTypeText, ingredientsText, allergensText,
            priceText, descriptionText;
    private Button buttonAdd;
    private List<Meal> meals = new ArrayList<>();
    private DatabaseReference databaseMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        mealNameText = (EditText) findViewById(R.id.mealNameEditText);
        mealTypeText = (EditText) findViewById(R.id.mealTypeEditText);
        cuisineTypeText = (EditText) findViewById(R.id.cuisineTypeEditText);
        ingredientsText = (EditText) findViewById(R.id.ingredientsEditText);
        allergensText = (EditText) findViewById(R.id.allergensEditText);
        priceText = (EditText) findViewById(R.id.priceEditText);
        descriptionText = (EditText) findViewById(R.id.descriptionEditText);
        buttonAdd = (Button) findViewById(R.id.btn_add);

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

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyInputs()) {
                    Meal meal = new Meal(mealName, mealType, cuisineType, ingredients, allergens, price, description);
                    databaseMeals.push().setValue(meal);
                    Toast.makeText(getApplicationContext(), "Add meal success!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    private boolean verifyInputs() {
        mealName = mealNameText.getText().toString().trim();
        mealType = mealTypeText.getText().toString().trim();
        cuisineType = cuisineTypeText.getText().toString().trim();
        ingredients = ingredientsText.getText().toString().trim();
        allergens = allergensText.getText().toString().trim();
        price = priceText.getText().toString().trim();
        description = descriptionText.getText().toString().trim();

        if (mealName == null || mealName.equals("")) {
            Toast.makeText(getApplicationContext(), "Meal name can not be empty!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (mealType == null || mealType.equals("")) {
            Toast.makeText(getApplicationContext(), "Meal type can not be empty!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (price == null || price.equals("")) {
            Toast.makeText(getApplicationContext(), "Price can not be empty!", Toast.LENGTH_LONG).show();
            return false;
        }
        for (Meal x : meals) {
            if (x.getMealName().equals(mealName)) {
                Toast.makeText(getApplicationContext(), "This meal name has already exist!", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }
}
