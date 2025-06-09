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

public class LoginActivity extends AppCompatActivity {
    FirebaseHelper authHelper = new FirebaseHelper();
    EditText emailEditText;
    EditText passwordEditText;
    TextView registerText;
    Button loginButton;
    CheckBox checkBox;

    SignInButton googleButton;

    private void loginUserWithGoogle(){
        authHelper.signInWithGoogle(this, true, LoginActivity.this, new  FirebaseHelper.AuthCallback(){
            @Override
            public void onSuccess(FirebaseUser user) {
                Toast.makeText(getApplicationContext(), "User Logged-in with Google!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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

    private void loginUserWithEmail(String email, String password){
        authHelper.signInWithEmail(email, password, LoginActivity.this, new FirebaseHelper.AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                Toast.makeText(getApplicationContext(), "User Logged-in !!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerText = findViewById(R.id.registerText);
        loginButton = findViewById(R.id.loginButton);
        checkBox = findViewById(R.id.checkBox);
        googleButton = findViewById(R.id.loginWithGoogle);

        googleButton.setOnClickListener(v -> loginUserWithGoogle());
        googleButton.setSize(SignInButton.SIZE_WIDE);


        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

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

            if ( password.isEmpty() ) {
                passwordEditText.setError("Password is required");
                passwordEditText.requestFocus();
                return;
            }

            if (checkBox.isChecked()){
                loginUserWithEmail(email, password);
            } else {
                Toast.makeText(getApplicationContext(), "Please accept the terms and conditions", Toast.LENGTH_SHORT).show();
            }

        });



        SpannableString spannable = getSpannableString();

        registerText.setText(spannable);
        registerText.setMovementMethod(LinkMovementMethod.getInstance());
        registerText.setHighlightColor(Color.TRANSPARENT);
    }

    @NonNull
    private SpannableString getSpannableString() {
        SpannableString spannable = new SpannableString("Don't have an account? Sign Up");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(getApplicationContext(), "Register clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.WHITE);
                ds.setUnderlineText(false);
            }
        };

        spannable.setSpan(clickableSpan, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }
}
