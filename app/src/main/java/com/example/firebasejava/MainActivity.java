package com.example.firebasejava;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button btn;
    TextView textView;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        btn = findViewById(R.id.logoutBTN);
        textView = findViewById(R.id.user_detail);
        user = auth.getCurrentUser();
        if (user == null){
            Bundle b = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle();
            startActivity(new Intent(getApplicationContext(), Login.class), b);
            finish();
        }
        else{
            textView.setText(user.getEmail());
        }

        btn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Bundle b = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle();
            startActivity(new Intent(getApplicationContext(), Login.class), b);
            finish();
        });
    }
}