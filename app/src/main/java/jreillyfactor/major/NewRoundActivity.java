package jreillyfactor.major;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jreillyfactor.major.Adapters.FriendAdapter;
import jreillyfactor.major.Models.Round;
import jreillyfactor.major.Models.User;

/**
 * Created by James Reilly on 7/25/2016.
 */
public class NewRoundActivity extends AppCompatActivity implements InviteFriendDialog.InviteDialogListener {
    private RecyclerView mRecyclerView;
    private FriendAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabase;

    private EditText mCourseName;
    private ParInputView mParView;


    private ArrayList<User> mFriendList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_round_create);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mCourseName = (EditText) findViewById(R.id.course_name);
        mParView = (ParInputView) findViewById(R.id.par_view);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FriendAdapter(mFriendList.toArray(new User[mFriendList.size()]));
        mRecyclerView.setAdapter(mAdapter);

        Button mCreateRound = (Button) findViewById(R.id.create_round);
        if (mCreateRound != null) {
            mCreateRound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    writeNewRound();
                }
            });
        }

        Button mAddFriend = (Button) findViewById(R.id.add_friend);
        if (mAddFriend != null) {
            mAddFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog mDialog = ProgressDialog.show(v.getContext(), "", "", true);



                    System.out.println(mDatabase.child("users"));
                    mDatabase.child("users").addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // Get user value

                                    GenericTypeIndicator<Map<String, User>> t = new GenericTypeIndicator<Map<String, User>>() {};
                                    Map<String, User> users = (Map<String, User>) dataSnapshot.getValue(t);

                                    System.out.println("USERS");
                                    System.out.println(dataSnapshot.getValue());
                                    System.out.println(users.values());

                                    ArrayList<User> selectableFriends = new ArrayList<User>();
                                    FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
                                    for (String key : users.keySet()) {
                                        User u = users.get(key);
                                        if (fireUser != null && !fireUser.getUid().equals(u.uid)) {
                                            // User is signed i
                                            selectableFriends.add(u);
                                        }
                                    }

                                    // This is Questionable Implementation but it works for now
                                    /*
                                    Iterator objIter = users.values().iterator();
                                    while (objIter.hasNext()) {
                                        HashMap<String, String> user = (HashMap<String, String>) objIter.next();
                                        Iterator pairsIter = user.entrySet().iterator();
                                        User u = new User();
                                        u.setFromHashMap(pairsIter);
                                        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
                                        if (fireUser != null && !fireUser.getUid().equals(u.uid)) {
                                            // User is signed i
                                            selectableFriends.add(u);
                                        }

                                        objIter.remove(); // avoids a ConcurrentModificationException
                                    }
                                    */
                                    mDialog.dismiss();
                                    InviteFriendDialog newFragment = new InviteFriendDialog();
                                    newFragment.setFreinds(selectableFriends);
                                    newFragment.show(getSupportFragmentManager(), "invite");
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    mDialog.dismiss();
                                    Log.w("Dialog", "getUser:onCancelled", databaseError.toException());
                                }
                            });

                }
            });
        }

    }

    private void writeNewRound() {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            //String key = mDatabase.child("rounds").push().getKey();

            // Add yourself to the list of players
            mFriendList.add(new User(user.getUid(), user.getDisplayName(), user.getEmail()));

            System.out.println(mDatabase);
            DatabaseReference ref = mDatabase.child("rounds").push();

            // Save the key before we push
            String key = ref.getKey();
            Round round = new Round(key, mCourseName.getText().toString(), mParView.getPars(), mFriendList, new Date());

            Log.d("NEW ROUND:", "KEY:" + key);
            // Push data
            // Save the round to rounds and to the user's rounds;
            ref.setValue(round);
            for(User u: mFriendList) {
                DatabaseReference userRoundRef = mDatabase.child("user-rounds").child(u.uid).child(key);
                userRoundRef.setValue(round);
            }


            Intent data = new Intent();
            data.putExtra("roundKey", key);
            if (getParent() == null) {
                setResult(Activity.RESULT_OK, data);
            } else {
                getParent().setResult(Activity.RESULT_OK, data);
            }
            finish();
        }


    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.d("FREINDS", "GETTING FREINDS");
        this.mFriendList = ((InviteFriendDialog) dialog).mSelectedFriends;
        System.out.println(mFriendList.size());
        this.mAdapter.updateList(mFriendList.toArray(new User[mFriendList.size()]));
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

}
