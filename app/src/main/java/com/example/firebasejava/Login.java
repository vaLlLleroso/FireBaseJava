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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextInputEditText editEmail, editPass;
    Button buttonLogIn;
    FirebaseAuth mAuth;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmail = findViewById(R.id.emailIN);
        editPass = findViewById(R.id.passwordIN);
        buttonLogIn = findViewById(R.id.loginBTN);
        mAuth = FirebaseAuth.getInstance();

        TextView textView = findViewById(R.id.regText);
        String text = "click here to Register.";

        SpannableString ss = new SpannableString(text);

        ClickableSpan cs1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(Login.this).toBundle();
                startActivity(new Intent(Login.this, Register.class), b);
                finish();
            }
            @Override
            public void updateDrawState(TextPaint ds){
                super.updateDrawState(ds);
                ds.setColor(Color.rgb(250,107,212));
            }
        };
        ss.setSpan(cs1, 13,22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        buttonLogIn.setOnClickListener(view -> {

            String email, password;
            email = String.valueOf(editEmail.getText());
            password = String.valueOf(editPass.getText());
            if (TextUtils.isEmpty(email)){
                Toast.makeText(Login.this,"email cannot be empty.",Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText(Login.this,"password cannot be empty.",Toast.LENGTH_SHORT).show();
            }
            if (editEmail != null && editPass != null){
                Toast.makeText(Login.this,"have you tried logging in?",Toast.LENGTH_SHORT).show();
            }
            else{
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"Welcome Back.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Login.this, "bad credentials.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }



        });
    }
}