package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ComplaintActivity extends AppCompatActivity {
    private DatabaseReference usersReference;
    private DatabaseReference complaintsReference;

    private List<Complaint> complaints;
    private String complaintId;
    private String cookId;

    private TextView clientTextView, cookTextView, complaintTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        complaints = new ArrayList<>();

        clientTextView = (TextView) findViewById(R.id.clientTextView);
        cookTextView = (TextView) findViewById(R.id.cookTextView);
        complaintTextView = (TextView) findViewById(R.id.complaintTextView);

        Intent intent = getIntent();
        String clientName = intent.getStringExtra("clientName");
        String cookName = intent.getStringExtra("cookName");
        complaintId = intent.getStringExtra("id");
        cookId = intent.getStringExtra("cookId");

        clientTextView.setText(clientName);
        cookTextView.setText(cookName);
        usersReference = FirebaseDatabase.getInstance().getReference("users");
        complaintsReference = FirebaseDatabase.getInstance().getReference("complaints");
        complaintsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaints.clear();
                for (DataSnapshot child:snapshot.getChildren()) {
                    Complaint complaint = child.getValue(Complaint.class);
                    complaint.setId(child.getKey());
                    complaints.add(complaint);
                    if(complaint.getId().equals(complaintId)) {
                        complaintTextView.setText(complaint.getDescription());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void dismiss(View view) {
        complaintsReference.child(complaintId).removeValue();
        finish();
    }

    public void suspend(View view) {
        usersReference.child(cookId).child("status").setValue("Suspend");
        complaintsReference.child(complaintId).removeValue();
        finish();
    }
}