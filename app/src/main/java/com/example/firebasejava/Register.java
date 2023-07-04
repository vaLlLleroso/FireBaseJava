package com.example.firebasejava;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Register extends AppCompatActivity {

    TextInputEditText editEmail, editPass, editUN;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("userlist");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editEmail = findViewById(R.id.emailIN);
        editPass = findViewById(R.id.passwordIN);
        editUN = findViewById(R.id.dispIN);
        buttonReg = findViewById(R.id.regBTN);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        Toast.makeText(Register.this, ref.toString(),
                Toast.LENGTH_SHORT).show();
        buttonReg.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String email, password, user;
            email = Objects.requireNonNull(editEmail.getText()).toString();
            password = Objects.requireNonNull(editPass.getText()).toString();
            user = Objects.requireNonNull(editUN.getText()).toString();


            if (TextUtils.isEmpty(email)){
                Toast.makeText(Register.this,"email cannot be empty.",Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText(Register.this,"password cannot be empty.",Toast.LENGTH_SHORT).show();
            }
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            User newUser = new User(email,user,password);
                            ref.child(user).setValue(newUser);

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Register.this, "Account Created.",
                                    Toast.LENGTH_SHORT).show();
                            Bundle b = ActivityOptions.makeSceneTransitionAnimation(Register.this).toBundle();
                            startActivity(new Intent(Register.this, Login.class), b);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            editEmail.setText(null);
                            editPass.setText(null);
                        }
                    });
        });

        TextView textView = findViewById(R.id.logText);
        String text = "click here to log in.";

        SpannableString ss = new SpannableString(text);

        ClickableSpan cs1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(Register.this).toBundle();
                startActivity(new Intent(Register.this, Login.class), b);
                finish();
            }
            @Override
            public void updateDrawState(TextPaint ds){
                super.updateDrawState(ds);
                ds.setColor(Color.rgb(250,107,212));
            }
        };
        ss.setSpan(cs1, 13,21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

    }
}

