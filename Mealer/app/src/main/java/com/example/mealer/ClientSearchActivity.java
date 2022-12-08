package com.example.mealer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientSearchActivity extends AppCompatActivity {

    private String clientId, mealName, mealType, cuisineType;
    private DatabaseReference databaseMeals;
    private List<Meal> meals = new ArrayList<>();
    private List<Meal> results = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_search);

        listView = (ListView) findViewById(R.id.resultList);
        Intent intent = getIntent();
        clientId = intent.getStringExtra("id");
        mealName = intent.getStringExtra("mealname");
        mealType = intent.getStringExtra("mealtype");
        cuisineType = intent.getStringExtra("cuisinetype");

        databaseMeals = FirebaseDatabase.getInstance().getReference("menu");
        databaseMeals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                meals.clear();

                List<Map<String, String>> data = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    Meal meal = postSnapshot.getValue(Meal.class);
                    meals.add(meal);
                }

                for (Meal x: meals) {
                    if (x.getMealName().equals(mealName) && x.getStatus().equals("Offered")) {
                        results.add(x);
                        continue;
                    }
                    if (x.getMealType().equals(mealType) && x.getStatus().equals("Offered")) {
                        results.add(x);
                        continue;
                    }
                    if (x.getCuisineType().equals(cuisineType) && x.getStatus().equals("Offered")) {
                        results.add(x);
                        continue;
                    }
                }

                for (Meal y: results) {
                    Map<String, String> dataMap = new HashMap<>();
                    dataMap.put("cookid", y.getCookId());
                    dataMap.put("name", y.getMealName());
                    dataMap.put("mtype", y.getMealType());
                    dataMap.put("ctype", y.getCuisineType());
                    dataMap.put("ingredients", y.getIngredients());
                    dataMap.put("allergens", y.getAllergens());
                    dataMap.put("price", y.getPrice());
                    dataMap.put("description", y.getDescription());
                    data.add(dataMap);
                }

                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.activity_meal_result,
                        new String[]{"cookid", "name", "mtype", "ctype", "ingredients", "allergens", "price", "description"},
                        new int[]{R.id.cookId, R.id.mealName, R.id.mealType, R.id.cuisineType, R.id.ingredients, R.id.allergens, R.id.price, R.id.description});
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView cookId = (TextView) view.findViewById(R.id.cookId);
                        TextView mealName = (TextView) view.findViewById(R.id.mealName);
                        TextView mealType = (TextView) view.findViewById(R.id.mealType);
                        TextView cuisineType = (TextView) view.findViewById(R.id.cuisineType);
                        TextView ingredients = (TextView) view.findViewById(R.id.ingredients);
                        TextView allergens = (TextView) view.findViewById(R.id.allergens);
                        TextView price = (TextView) view.findViewById(R.id.price);
                        TextView description = (TextView) view.findViewById(R.id.description);

                        Intent intent = new Intent(getApplicationContext(), ClientPurchaseActivity.class);
                        intent.putExtra("clientid", clientId);
                        intent.putExtra("cookid", cookId.getText());
                        intent.putExtra("name", mealName.getText());
                        intent.putExtra("mtype", mealType.getText());
                        intent.putExtra("ctype", cuisineType.getText());
                        intent.putExtra("ingredients", ingredients.getText());
                        intent.putExtra("allergens", allergens.getText());
                        intent.putExtra("price", price.getText());
                        intent.putExtra("description", description.getText());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
