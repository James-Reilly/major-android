package jreillyfactor.major.Models;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import jreillyfactor.major.InviteFriendDialog;

/**
 * Created by James Reilly on 8/2/2016.
 */
public class Player {
    public String uid;
    public String name;
    public String position;
    public Integer today;
    public String thru;
    public Integer total;

    public Player() {}

    public Player(String uid) {
        this.uid = uid;
        this.name = "";
        this.today = 0;
        this.thru = "0";
        this.total = 0;

        // This will be set later
        this.position = "";

    }


}
