package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClientPurchaseActivity extends AppCompatActivity {

    private String clientId, mealName, cookId, lastName, address, description, rating, pickupTime;
    private TextView mealNameText, mealTypeText, cuisineTypeText, ingredientsText, allergensText,
            priceText, descriptionText, lastNameText, addressText, cookDescriptionText, ratingText;
    private EditText pickupTimeText;
    private Button buttonPurchase;

    private DatabaseReference databaseUsers, databasePurchases;
    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_purchase);

        mealNameText = (TextView) findViewById(R.id.mealName);
        mealTypeText = (TextView) findViewById(R.id.mealType);
        cuisineTypeText = (TextView) findViewById(R.id.cuisineType);
        ingredientsText = (TextView) findViewById(R.id.ingredients);
        allergensText = (TextView) findViewById(R.id.allergens);
        priceText = (TextView) findViewById(R.id.price);
        descriptionText = (TextView) findViewById(R.id.description);
        lastNameText = (TextView) findViewById(R.id.lastName);
        addressText = (TextView) findViewById(R.id.address);
        cookDescriptionText = (TextView) findViewById(R.id.cookDescription);
        ratingText = (TextView) findViewById(R.id.rating);

        Intent intent = getIntent();
        mealNameText.setText(intent.getStringExtra("name"));
        mealTypeText.setText(intent.getStringExtra("mtype"));
        cuisineTypeText.setText(intent.getStringExtra("ctype"));
        ingredientsText.setText(intent.getStringExtra("ingredients"));
        allergensText.setText(intent.getStringExtra("allergens"));
        priceText.setText(intent.getStringExtra("price"));
        descriptionText.setText(intent.getStringExtra("description"));
        cookId = intent.getStringExtra("cookid");
        clientId = intent.getStringExtra("clientid");
        mealName = intent.getStringExtra("name");

        pickupTimeText = (EditText) findViewById(R.id.pickupTime);


        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databasePurchases = FirebaseDatabase.getInstance().getReference("purchases");
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    User user = child.getValue(User.class);
                    user.setId(child.getKey());
                    users.add(user);
                }

                for (User x: users) {
                    if (x.getId().equals(cookId)) {
                        lastName = x.getLastName();
                        address = x.getAddress();
                        description = x.getDescription();
                        rating = x.getRating();
                        break;
                    }
                }
                lastNameText.setText(lastName);
                addressText.setText(address);
                cookDescriptionText.setText(description);
                ratingText.setText(rating);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buttonPurchase = (Button) findViewById(R.id.btn_purchase);
        buttonPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickupTime = pickupTimeText.getText().toString().trim();
                if (!pickupTime.equals("")) {
                    Purchase purchase = new Purchase(clientId, cookId, lastName, pickupTime, mealName, address, "Pending");
                    databasePurchases.push().setValue(purchase);
                    Toast.makeText(getApplicationContext(), "Make purchase successfully!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}