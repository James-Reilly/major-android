package jreillyfactor.major.Models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by James Reilly on 7/25/2016.
 */
@IgnoreExtraProperties
public class User {
    public String uid;
    public String username;
    public String email;

    public User() {}

    public User(String uid, String displayName, String email) {
        this.uid = uid;
        this.username = displayName;
        this.email = email;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("username", username);
        result.put("email", email);

        return result;
    }
}
