package jreillyfactor.major;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class MainActivity extends AppCompatActivity {

    private BottomBar mBottomBar;
    private final String TAG = "MainActivity";
    private String[] mMenuArray = {"Stats", "Rounds", "Tournaments", "Friends"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);



        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //mDrawerList.setOnClickListener(new DrawerItemClickListener());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mMenuArray));
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle("closed");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle("open");
            }
        };
    }

    private void showBottomBar(View view, Bundle savedInstanceState) {
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch(menuItemId) {
                    case (R.id.bottomBarLeaderboard):
                        Log.d(TAG, "selectLeaderboard");
                        break;
                    case (R.id.bottomBarYourRound):
                        Log.d(TAG, "selectYourRound");
                        break;
                    case (R.id.bottomBarChat):
                        Log.d(TAG, "selectChat");
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                switch(menuItemId) {
                    case (R.id.bottomBarLeaderboard):
                        Log.d(TAG, "selectLeaderboard");
                        break;
                    case (R.id.bottomBarYourRound):
                        Log.d(TAG, "selectYourRound");
                        break;
                    case (R.id.bottomBarChat):
                        Log.d(TAG, "selectChat");
                        break;
                }
            }
        });
    }

}
