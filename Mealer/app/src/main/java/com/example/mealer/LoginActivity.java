package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;
    private Button buttonLogin;
    private Button buttonClientSignup;
    private Button buttonCookSignup;
    private EditText usernameText, passwordText;
    private Users user;
    private List<Users> users = new ArrayList<>();
    private DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText = (EditText) findViewById(R.id.usernameEditText);
        passwordText = (EditText) findViewById(R.id.passwordEditText);
        buttonLogin = (Button) findViewById(R.id.btn_login);
        buttonClientSignup = (Button) findViewById(R.id.btn_clientSignup);
        buttonCookSignup = (Button) findViewById(R.id.btn_cookSignup);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyInputs()) {
                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    intent.putExtra("role", user.getRole());
                    startActivity(intent);
                }
            }
        });

        buttonClientSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClientSignUpActivity.class);
                startActivity(intent);
            }
        });

        buttonCookSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CookSignUpActivity.class);
                startActivity(intent);
            }
        });

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    Users user = postSnapshot.getValue(Users.class);
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
        if (username == null || username.equals("")) {
            Toast.makeText(getApplicationContext(), "Username can not be empty!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password == null || password.equals("")) {
            Toast.makeText(getApplicationContext(), "Password can not be empty!", Toast.LENGTH_LONG).show();
            return false;
        }
        for (Users x : users) {
            if (x.getUsername().equals(username)) {
                user = x;
            }
        }
        if (user == null) {
            Toast.makeText(getApplicationContext(), "Username does not exist!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!user.getPassword().equals(password)) {
            Toast.makeText(getApplicationContext(), "Password is incorrect!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}