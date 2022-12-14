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

public class ClientSignUpActivity extends AppCompatActivity {

    private String firstName, lastName, username, password, address, creditCard;
    private EditText firstNameText, lastNameText, usernameText, passwordText,
            addressText, creditCardText;
    private Button buttonClientSignup;
    private List<User> users = new ArrayList<>();
    private DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_sign_up);

        firstNameText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameText = (EditText) findViewById(R.id.lastNameEditText);
        usernameText = (EditText) findViewById(R.id.emailEditText);
        passwordText = (EditText) findViewById(R.id.passwordEditText);
        addressText = (EditText) findViewById(R.id.addressEditText);
        creditCardText = (EditText) findViewById(R.id.creditCardEditText);
        buttonClientSignup = (Button) findViewById(R.id.btn_clientSignup);

        buttonClientSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyInputs()) {
                    User user = new User(username, password, "Client",
                            firstName, lastName, address, creditCard);
                    databaseUsers.push().setValue(user);
                    Toast.makeText(getApplicationContext(), "Sign up success!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean verifyInputs() {
        username = usernameText.getText().toString().trim();
        password = passwordText.getText().toString().trim();
        firstName = firstNameText.getText().toString().trim();
        lastName = lastNameText.getText().toString().trim();
        address = addressText.getText().toString().trim();
        creditCard = creditCardText.getText().toString().trim();

        if (username == null || username.equals("")) {
            Toast.makeText(getApplicationContext(), "Username can not be empty!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password == null || password.equals("")) {
            Toast.makeText(getApplicationContext(), "Password can not be empty!", Toast.LENGTH_LONG).show();
            return false;
        }
        for (User x : users) {
            if (x.getUsername().equals(username)) {
                Toast.makeText(getApplicationContext(), "This username has already exist!", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }
}