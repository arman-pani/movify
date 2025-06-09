package activities;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.R;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseUser;

import helpers.FirebaseHelper;

public class RegisterActivity extends AppCompatActivity {

    FirebaseHelper authHelper = new FirebaseHelper();
    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    TextView loginText;
    Button registerButton;
    CheckBox checkBox;
    SignInButton googleButton;


    private void registerUserWithGoogle(){
        authHelper.signInWithGoogle(this, false, RegisterActivity.this, new  FirebaseHelper.AuthCallback(){
            @Override
            public void onSuccess(FirebaseUser user) {
                Toast.makeText(getApplicationContext(), "Account Created with Google", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void registerUserWithEmail(String email, String password){
        authHelper.registerUserWithEmailAndPassword(
                email,
                password,
                RegisterActivity.this,
                new FirebaseHelper.AuthCallback() {
                    @Override
                    public void onSuccess(FirebaseUser user) {
                        Toast.makeText(getApplicationContext(), "Account Created!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.loginButton);
        checkBox = findViewById(R.id.checkBox);
        loginText = findViewById(R.id.registerText);

        googleButton = findViewById(R.id.registerWithGoogle);
        googleButton.setSize(SignInButton.SIZE_WIDE);

        googleButton.setOnClickListener(v -> registerUserWithGoogle());



        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (email.isEmpty()) {
                System.out.println("Email: " + email);
                emailEditText.setError("Email is required");
                emailEditText.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Please enter a valid email");
                emailEditText.requestFocus();
                return;
            }

            if ( password.isEmpty() || confirmPassword.isEmpty()) {
                passwordEditText.setError("Password is required");
                passwordEditText.requestFocus();
                return;
            }

            if (password.length() < 6) {
                passwordEditText.setError("Password must be at least 6 characters");
                passwordEditText.requestFocus();
                return;
            }

            if (checkBox.isChecked()){
                if (password.equals(confirmPassword)){
                    registerUserWithEmail(email, password);
                } else {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please accept the terms and conditions", Toast.LENGTH_SHORT).show();
            }

        });


        SpannableString spannable = getSpannableString();

        loginText.setText(spannable);
        loginText.setMovementMethod(LinkMovementMethod.getInstance());
        loginText.setHighlightColor(Color.TRANSPARENT);

    }

    @NonNull
    private SpannableString getSpannableString() {
        SpannableString spannable = new SpannableString("Already have an account? Login");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(getApplicationContext(), "Login clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.WHITE);
                ds.setUnderlineText(false);
            }
        };

        spannable.setSpan(clickableSpan, 25, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }
}
