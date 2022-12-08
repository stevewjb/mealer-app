package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CookHomeActivity extends AppCompatActivity {

    private DatabaseReference databaseMeals;
    private ListView listView;
    private Button buttonAddMeal;
    private List<Meal> meals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_home);

        listView = (ListView) findViewById(R.id.menuList);
        buttonAddMeal = (Button) findViewById(R.id.btn_addMeal);
        Intent intent = getIntent();

        buttonAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), CookAddMealActivity.class);
                intent1.putExtra("id", intent.getStringExtra("id"));
                startActivity(intent1);
            }
        });

        databaseMeals = FirebaseDatabase.getInstance().getReference("menu");
        databaseMeals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                meals.clear();
                List<Map<String,String>> data = new ArrayList<>();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Meal meal = postSnapshot.getValue(Meal.class);
                    meal.setId(postSnapshot.getKey());
                    meals.add(meal);

                    Map<String,String> dataMap = new HashMap<>();
                    dataMap.put("id", meal.getId());
                    dataMap.put("name", meal.getMealName());
                    dataMap.put("type", meal.getMealType());
                    dataMap.put("price", meal.getPrice());
                    data.add(dataMap);
                }

                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data,
                        R.layout.activity_meal_home, new String[]{"id", "name","type","price"},
                        new int[]{R.id.idText, R.id.mealName,R.id.mealType,R.id.price});
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView idText = (TextView) view.findViewById(R.id.idText);
                        TextView mealName = (TextView) view.findViewById(R.id.mealName);
                        TextView mealType = (TextView) view.findViewById(R.id.mealType);
                        TextView price = (TextView) view.findViewById(R.id.price);

                        Intent intent = new Intent(getApplicationContext(), MealActivity.class);
                        intent.putExtra("id", idText.getText());
                        intent.putExtra("name", mealName.getText());
                        intent.putExtra("type", mealType.getText());
                        intent.putExtra("price", price.getText());
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