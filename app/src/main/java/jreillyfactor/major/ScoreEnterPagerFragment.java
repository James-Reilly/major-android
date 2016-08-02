package jreillyfactor.major;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jreillyfactor.major.Adapters.RoundPagerAdapter;
import jreillyfactor.major.Adapters.ScorePagerAdapter;
import jreillyfactor.major.Models.Round;
import jreillyfactor.major.Models.Score;

/**
 * Created by James Reilly on 8/1/2016.
 */
public class ScoreEnterPagerFragment extends Fragment {

    private ScorePagerAdapter mScorePagerAdapter;
    private ViewPager mViewPager;
    private DatabaseReference mDatabase;
    private DatabaseReference mScoreReference;
    private FirebaseUser mUser;
    private String TAG = "ScoreEnterFragment";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList<Score> scores = getArguments().getParcelableArrayList("scores");
        String key = getArguments().getString("roundKey");
        final View view = inflater.inflate(R.layout.fragment_enter_score_pager, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        /*
        if (scores != null && mUser != null) {
            System.out.println("Getting score data");
            mScoreReference = mDatabase.child("rounds").child(key).child("scores").child(mUser.getUid());
            System.out.println(mScoreReference);
            ValueEventListener roundListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Round object and use the values to update the UI
                    System.out.println(dataSnapshot.getValue());
                    GenericTypeIndicator<List<Score>> typeIndicator = new GenericTypeIndicator<List<Score>>() {};
                    List<Score> scoreList = dataSnapshot.getValue(typeIndicator);
                    System.out.println("Got data for score");
                    System.out.println(scoreList);
                    if (scoreList != null) {
                        setPager(scoreList);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Round failed, log a message
                    Log.w(TAG, "loadRound:onCancelled", databaseError.toException());
                    // ...
                }
            };
            mScoreReference.addValueEventListener(roundListener);
        }
        */
        setPager(view, scores, key);

        // Init button on click listeners

        return view;
    }

    private void setPager(View v, List<Score> scores, String key) {

        mScorePagerAdapter = new ScorePagerAdapter(getActivity(), getChildFragmentManager(), scores, key);
        mViewPager = (ViewPager) v.findViewById(R.id.score_pager);
        mViewPager.setAdapter(mScorePagerAdapter);
    }

    public void goToPage(int hole) {
        mViewPager.setCurrentItem(hole - 1);
    }

}
