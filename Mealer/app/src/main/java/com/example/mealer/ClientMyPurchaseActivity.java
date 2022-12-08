package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class ClientMyPurchaseActivity extends AppCompatActivity {

    private String clientId;
    private DatabaseReference databasePurchases;
    private List<Purchase> purchases = new ArrayList<>();
    private List<Purchase> results = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_my_purchase);

        listView = (ListView) findViewById(R.id.purchaseList);
        Intent intent = getIntent();
        clientId = intent.getStringExtra("id");

        databasePurchases = FirebaseDatabase.getInstance().getReference("purchases");
        databasePurchases.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                purchases.clear();
                List<Map<String, String>> data = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    Purchase purchase = postSnapshot.getValue(Purchase.class);
                    purchases.add(purchase);
                }

                for (Purchase x: purchases) {
                    if (x.getClientId().equals(clientId)) {
                        results.add(x);
                    }
                }

                for (Purchase y: results) {
                    Map<String, String> dataMap = new HashMap<>();
                    dataMap.put("cookId", y.getCookId());
                    dataMap.put("cookName", y.getCookName());
                    dataMap.put("mealName", y.getMealName());
                    dataMap.put("address", y.getAddress());
                    dataMap.put("time", y.getPickupTime());
                    dataMap.put("status", y.getStatus());
                    data.add(dataMap);
                }

                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.activity_purchase_home,
                        new String[]{"cookId", "cookName", "mealName", "address", "time", "status"},
                        new int[]{R.id.cookId, R.id.cookName, R.id.mealName, R.id.address, R.id.pickupTime, R.id.status});
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView cookId = (TextView) view.findViewById(R.id.cookId);
                        TextView cookName = (TextView) view.findViewById(R.id.cookName);
                        TextView mealName = (TextView) view.findViewById(R.id.mealName);

                        Intent intent = new Intent(getApplicationContext(), ClientRateActivity.class);
                        intent.putExtra("cookId", cookId.getText());
                        intent.putExtra("cookName", cookName.getText());
                        intent.putExtra("mealName", mealName.getText());
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