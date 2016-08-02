package jreillyfactor.major;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.roughike.bottombar.BottomBar;

import jreillyfactor.major.Onboarding.InfoActivity;
import jreillyfactor.major.Onboarding.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private BottomBar mBottomBar;
    private final String TAG = "MainActivity";
    static final int CREATE_ROUND_REQUEST = 1;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private Bundle mRoundBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        // Init navigation view
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Add menu item to toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            VectorDrawableCompat indicator = VectorDrawableCompat.create(getResources(), R.drawable.ic_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(), R.color.white, getTheme()));
            supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set navdrawer behavior


        if (navView != null) {
            navView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        // This method will trigger on item Click of navigation menu
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            // Set item in checked state
                            menuItem.setChecked(true);

                            // TODO: handle navigation
                            switch (menuItem.getItemId()) {
                                case (R.id.rounds) :
                                    System.out.println("Going to Round List!");
                                    RoundListFragment newFragment = new RoundListFragment();

                                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.main_fragment, newFragment);
                                    transaction.addToBackStack("main");
                                    transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                                    transaction.commit();
                                    break;
                                default:
                                    logout();
                                    break;
                            }

                            // Closing drawer on item click
                            mDrawerLayout.closeDrawers();
                            return true;
                        }
                    }
            );
        }

        // Adding Floating Action Button to bottom right of main view

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCreateRound();
            }
        });

    }

    public void goToCreateRound() {
        Intent intent = new Intent(this, NewRoundActivity.class);
        startActivityForResult(intent, CREATE_ROUND_REQUEST);
    }

    public void logout() {
        System.out.println("Logging out");
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CREATE_ROUND_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                mRoundBundle = data.getExtras();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        // Check if we have to show a round
        if (mRoundBundle != null) {
            showRound(mRoundBundle.getString("roundKey"));
            // So we don't do it twice
            mRoundBundle = null;
        }
    }

    public void showRound(String key) {

        Bundle bundle = new Bundle();
        bundle.putString("roundKey", key);
        CurrentRoundFragment newFragment = new CurrentRoundFragment();
        newFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment, newFragment);
        transaction.addToBackStack("main");
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        transaction.commit();
    }



}
