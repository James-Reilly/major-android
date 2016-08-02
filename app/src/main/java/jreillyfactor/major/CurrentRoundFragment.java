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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import jreillyfactor.major.Adapters.RoundPagerAdapter;
import jreillyfactor.major.Models.Round;

/**
 * Created by James Reilly on 7/27/2016.
 */
public class CurrentRoundFragment extends Fragment {
    private BottomBar mBottomBar;
    private ViewPager mViewPager;
    private RoundPagerAdapter mRoundPagerAdapter;
    private final String TAG = "CurrentRoundFragment";
    private String mCourseName = "";
    private DatabaseReference mDatabase;
    private DatabaseReference mRoundReference;
    private String mKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Loading!");
        mKey = getArguments().getString("roundKey");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (mKey != null) {
            mRoundReference = mDatabase.child("rounds").child(mKey);
            ValueEventListener roundListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Round object and use the values to update the UI
                    Round round = dataSnapshot.getValue(Round.class);
                    if (round != null) {
                        System.out.println("got round!");
                        mCourseName = round.courseName;
                        mRoundPagerAdapter.setRound(round);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Round failed, log a message
                    Log.w(TAG, "loadRound:onCancelled", databaseError.toException());
                    // ...
                }
            };
            mRoundReference.addValueEventListener(roundListener);
        }


        View view = inflater.inflate(R.layout.fragment_current_round, container, false);
        // Init button on click listeners
        mRoundPagerAdapter = new RoundPagerAdapter(getActivity(), getChildFragmentManager(), null);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mRoundPagerAdapter);
        Log.d(TAG, "loadRound:onDataChange" + mCourseName);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mCourseName);
        }

        //mViewPager.setCurrentItem(0);
        showBottomBar(view, savedInstanceState);

        return mBottomBar;
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
                        if (mViewPager != null) {
                            mViewPager.setCurrentItem(0);
                        }
                        break;
                    case (R.id.bottomBarYourRound):
                        Log.d(TAG, "selectYourRound");
                        if (mViewPager != null) {
                            mViewPager.setCurrentItem(1);
                        }
                        break;
                    case (R.id.bottomBarChat):
                        Log.d(TAG, "selectChat");
                        if (mViewPager != null) {
                            mViewPager.setCurrentItem(2);
                        }
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
