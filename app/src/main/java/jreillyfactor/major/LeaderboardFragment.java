package jreillyfactor.major;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import jreillyfactor.major.Adapters.LeaderboardAdapter;
import jreillyfactor.major.Models.Round;
import jreillyfactor.major.Models.Score;

/**
 * Created by James Reilly on 8/2/2016.
 */
public class LeaderboardFragment extends Fragment {

    private List<Round> mRounds;
    private RecyclerView mRecyclerView;
    private LeaderboardAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedIntanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        // Init button on click listeners

        Round round = getArguments().getParcelable("round");
        Log.d("LEADERBOARD:", "Loaded fragment");
        System.out.println(round);

        if (round != null) {
            mRounds = new ArrayList<>();
            mRounds.add(round);
            System.out.println("mRounds != null");
            mRecyclerView = (RecyclerView) view.findViewById(R.id.leaderboard_recycler);
            mLayoutManager = new LinearLayoutManager(this.getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new LeaderboardAdapter(this.getContext(), mRounds);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            System.out.println("mRounds == null");
        }

        return view;
    }
}
