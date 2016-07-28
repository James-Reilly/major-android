package jreillyfactor.major;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import jreillyfactor.major.Models.RoundPagerAdapter;

/**
 * Created by James Reilly on 7/27/2016.
 */
public class CurrentRoundFragment extends Fragment {
    private BottomBar mBottomBar;
    private ViewPager mViewPager;
    private RoundPagerAdapter mRoundPagerAdapter;
    private final String TAG = "CurrentRoundFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Loading!");
        View view = inflater.inflate(R.layout.fragment_current_round, container, false);
        // Init button on click listeners
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Berkshire Valley - Hole 2");
        }
        mRoundPagerAdapter = new RoundPagerAdapter(getActivity(), getChildFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mRoundPagerAdapter);
        //mViewPager.setCurrentItem(0);
        //showBottomBar(view, savedInstanceState);

        return view;
    }

    private void showBottomBar(View view, Bundle savedInstanceState) {
        mBottomBar = BottomBar.attach(view, savedInstanceState);
        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch(menuItemId) {
                    case (R.id.bottomBarLeaderboard):
                        Log.d(TAG, "selectLeaderboard");
                        mViewPager.setCurrentItem(0);
                        break;
                    case (R.id.bottomBarYourRound):
                        Log.d(TAG, "selectYourRound");
                        mViewPager.setCurrentItem(1);
                        break;
                    case (R.id.bottomBarChat):
                        Log.d(TAG, "selectChat");
                        mViewPager.setCurrentItem(2);
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
