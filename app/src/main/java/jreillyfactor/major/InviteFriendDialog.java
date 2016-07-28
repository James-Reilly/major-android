package jreillyfactor.major;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import jreillyfactor.major.Models.User;

/**
 * Created by James Reilly on 7/25/2016.
 */
public class InviteFriendDialog extends DialogFragment {

    private DatabaseReference mDatabase;
    private User[] mFriendList = {};
    private ArrayList<User> mSelectedFriends = new ArrayList<User>();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        loadFriends();
        builder.setTitle("Invite Friends").setMultiChoiceItems(getFriendNames(), null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            mSelectedFriends.add(mFriendList[which]);
                        } else if (mSelectedFriends.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            mSelectedFriends.remove(Integer.valueOf(which));
                        }
                    }
                })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    private void loadFriends() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        System.out.println(mDatabase.child("users"));
        mDatabase.child("users").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value

                        GenericTypeIndicator<Map<String, User>> t = new GenericTypeIndicator<Map<String, User>>() {};
                        Map<String, User> users = (Map<String, User>) dataSnapshot.getValue();

                        System.out.println("USERS");
                        System.out.println(dataSnapshot.getValue());
                        System.out.println( users.values().toArray()[0].getClass());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("Dialog", "getUser:onCancelled", databaseError.toException());
                    }
                });
    }


    private String[] getFriendNames() {
        String[] friends = {};
        return friends;
    }
}
