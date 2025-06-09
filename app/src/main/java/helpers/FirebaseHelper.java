package helpers;

import static android.content.ContentValues.TAG;
import static com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class FirebaseHelper {

    private FirebaseAuth mAuth;

    private static final int RC_SIGN_IN = 9001;

    private static final String WEB_CLIENT_ID = "51508480623-48oj0kv90oqfja3o5glrvm7vq9o416cq.apps.googleusercontent.com";

    public interface AuthCallback {
        void onSuccess(FirebaseUser user);
        void onFailure(Exception e);
    }

    public FirebaseHelper() {
        mAuth = FirebaseAuth.getInstance();
    }



    public boolean isUserLoggedIn() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null;
    }

    public void registerUserWithEmailAndPassword(String email, String password, Activity activity, AuthCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        callback.onSuccess(user);
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    public void signInWithEmail(String email, String password, Activity activity, AuthCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(mAuth.getCurrentUser());
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }


    private void handleSignIn(Credential credential, Activity activity, AuthCallback callback) {
        // Check if credential is of type Google ID
        if (credential instanceof CustomCredential){
            CustomCredential customCredential = (CustomCredential) credential;
            if (credential.getType().equals(TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
                // Create Google ID Token
                Bundle credentialData = customCredential.getData();
                GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credentialData);

                // Sign in to Firebase with using the token
                firebaseAuthWithGoogle(googleIdTokenCredential.getIdToken(), activity, callback);
            } else {
                Log.w(TAG, "Credential is not of type Google ID!");
            }
        }
    }


    public void signInWithGoogle(Context context, boolean setFilerByAuthorizedAccounts, Activity activity, AuthCallback callback){
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(setFilerByAuthorizedAccounts)
                .setServerClientId(WEB_CLIENT_ID)
                .build();

        // Create the Credential Manager request
        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();

        CredentialManager credentialManager = CredentialManager.create(context);

        CancellationSignal cancellationSignal = new CancellationSignal();

        credentialManager.getCredentialAsync(
                context,
                request,
                cancellationSignal,
                ContextCompat.getMainExecutor(context),
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(@NonNull GetCredentialResponse result) {
                        Credential credential = result.getCredential();
                        handleSignIn(credential, activity, callback);
                    }

                    @Override
                    public void onError(@NonNull GetCredentialException e) {
                        Toast.makeText(context, "Google Sign-In failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void firebaseAuthWithGoogle(String idToken, Activity activity, AuthCallback callback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(mAuth.getCurrentUser());
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    public void signOut() {
        mAuth.signOut();

    }
}
