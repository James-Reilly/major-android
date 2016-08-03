package jreillyfactor.major.Onboarding;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import jreillyfactor.major.MainActivity;
import jreillyfactor.major.Models.User;
import jreillyfactor.major.R;

/**
 * Created by James Reilly on 7/18/2016.
 */

public class InfoActivity extends AppCompatActivity
        implements InfoFragment.OnInfoOptionSelectedListener, LoginFragment.OnLoginListener,
        RegisterFragment.OnRegisterListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private final String TAG = "Login Activity";
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        //getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    goToMainActivity();
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                }
            }
        };

        setContentView(R.layout.activity_info);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void presentLogin() {

        System.out.println("Should see login");
        LoginFragment newFragment = new LoginFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.main_fragment, new LoginFragment());
        transaction.addToBackStack("info");
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        transaction.commit();

    }

    @Override
    public void presentSignUp() {
        System.out.println("Should see signUp");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.main_fragment, new RegisterFragment());
        transaction.addToBackStack("info");
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        transaction.commit();
    }

    public void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                if (!task.isSuccessful()) {
                    Toast.makeText(InfoActivity.this, "Authentication failed.", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public void register(String email, final String username, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                if (!task.isSuccessful()) {
                    Toast.makeText(InfoActivity.this, "Authentication failed.", Toast.LENGTH_SHORT);
                } else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User profile updated.");
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        writeNewUser(user.getUid(), user.getDisplayName(), user.getEmail());
                                    }
                                }
                            });
                }
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void writeNewUser(String userId, String name, String email) {
        DatabaseReference ref = mDatabase.child("users").child(userId);
        ref.setValue(new User(userId, name, email));
    }

}
