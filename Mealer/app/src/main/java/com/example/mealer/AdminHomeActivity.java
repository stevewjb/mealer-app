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

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminHomeActivity extends AppCompatActivity {
    private DatabaseReference usersReference;
    private DatabaseReference complaintsReference;

    private ListView listView;
    private List<String> clientIds = new ArrayList<>();
    private List<String> cookIds = new ArrayList<>();
    private List<Complaint> complaints = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        listView = (ListView) findViewById(R.id.listview);
        usersReference = FirebaseDatabase.getInstance().getReference("users");
        complaintsReference = FirebaseDatabase.getInstance().getReference("complaints");

        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clientIds.clear();
                cookIds.clear();
                for (DataSnapshot child: snapshot.getChildren()){
                    User user = child.getValue(User.class);
                    user.setId(child.getKey());
                    users.add(user);

                    if(user.getRole().equals("Client")) {
                        clientIds.add(user.getId());
                        continue;
                    } else if (user.getRole().equals("Cook")) {
                        cookIds.add(user.getId());
                        continue;
                    } else {
                        continue;
                    }
                }
                System.out.println(clientIds);
                System.out.println(cookIds);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        complaintsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaints.clear();
                List<Map<String,String>> data = new ArrayList<>();

                for (DataSnapshot child: snapshot.getChildren()) {
                    Complaint complaint = child.getValue(Complaint.class);
                    complaint.setId(child.getKey());
                    complaints.add(complaint);

                    Map<String,String> dataMap = new HashMap<>();
                    dataMap.put("id", complaint.getId());
                    dataMap.put("cookId", complaint.getCookId());
                    for (User user :users) {
                        if(user.getId().equals(complaint.getClientId())) {
                            dataMap.put("client", user.getLastName());
                        } else if(user.getId().equals(complaint.getCookId())) {
                            dataMap.put("cook", user.getLastName());
                        }
                    }
                    data.add(dataMap);
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data,
                        R.layout.activity_complaint_home, new String[]{"id","cookId","client","cook"},
                        new int[]{R.id.idTextView,R.id.cookIdTextView,R.id.clientId,R.id.cookId});
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView idTextView = (TextView) view.findViewById(R.id.idTextView);
                        TextView cookIdTextView = (TextView) view.findViewById(R.id.cookIdTextView);
                        TextView cookTextView = (TextView) view.findViewById(R.id.cookId);
                        TextView clientTextView = (TextView) view.findViewById(R.id.clientId);

                        Intent intent = new Intent(getApplicationContext(), ComplaintActivity.class);
                        intent.putExtra("id", idTextView.getText());
                        intent.putExtra("cookId",cookIdTextView.getText());
                        intent.putExtra("clientName", clientTextView.getText());
                        intent.putExtra("cookName", cookTextView.getText());
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