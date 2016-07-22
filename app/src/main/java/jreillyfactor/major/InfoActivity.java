package jreillyfactor.major;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by James Reilly on 7/18/2016.
 */

public class InfoActivity extends AppCompatActivity
        implements InfoFragment.OnInfoOptionSelectedListener, LoginFragment.OnLoginListener,
        RegisterFragment.OnRegisterListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private final String TAG = "Login Activity";

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

    public void register(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                if (!task.isSuccessful()) {
                    Toast.makeText(InfoActivity.this, "Authentication failed.", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
