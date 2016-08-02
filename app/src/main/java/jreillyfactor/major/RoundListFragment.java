package jreillyfactor.major;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;
import java.util.Map;

import jreillyfactor.major.Adapters.FriendAdapter;
import jreillyfactor.major.Adapters.RoundAdapter;
import jreillyfactor.major.Models.Round;
import jreillyfactor.major.Models.User;

/**
 * Created by James Reilly on 7/29/2016.
 */
public class RoundListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RoundAdapter mAdapter;
    private Round[] mRounds = new Round[0];
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabase;
    private final String TAG = "RoundListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_round_list, container, false);
        // Init button on click listeners

        mDatabase = FirebaseDatabase.getInstance().getReference();
        getRounds();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RoundAdapter(this.getContext(), mRounds);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private void getRounds() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            mDatabase.child("user-rounds").child(user.getUid()).addListenerForSingleValueEvent(

                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get user value
                            System.out.println(dataSnapshot.getValue());
                            // This is how you get things from firebase!
                            GenericTypeIndicator<Map<String, Round>> typeIndicator = new GenericTypeIndicator<Map<String, Round>>() {};
                            Map<String, Round> r = dataSnapshot.getValue(typeIndicator);
                            if (r != null) {
                                mRounds = new Round[r.keySet().size()];
                                int index = 0;
                                for(String key : r.keySet()) {
                                    System.out.println(key);
                                    mRounds[index] = r.get(key);
                                }
                                System.out.println(mRounds.toString());
                                mAdapter.updateList(mRounds);
                            } else {
                                Log.d(TAG, "Round list was null");
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d(TAG, "Cacelled!");
                        }
                    });
        } else {
            Log.d(TAG, "No User");
        }

    }
}
