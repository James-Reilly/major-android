package jreillyfactor.major.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by James Reilly on 7/29/2016.
 */
public class Round implements Parcelable {
    public String key;
    public String courseName;
    public List<Integer> pars;
    public Long date;
    public Map<String, List<Score>> scores;

    public Round() {}

    public Round(String key, String courseName, List<Integer> pars, ArrayList<User> players, Date date) {
        this.courseName = courseName;
        this.pars = pars;
        this.date = date.getTime();
        this.scores = new HashMap<>();
        for (User u: players) {
            // Init scores to par
            List<Score> newScores = new ArrayList<>();
            for (Integer i = 0; i < pars.size(); i++) {
                newScores.add(new Score(i + 1, pars.get(i)));
            }
            this.scores.put(u.uid, newScores);
        }
        this.key = key;
    }

    public Round(Parcel in) {
        this.key = in.readString();
        this.courseName = in.readString();
        this.pars = new ArrayList<Integer>();
        in.readList(this.pars, Integer.class.getClassLoader());
        this.date = in.readLong();
        this.scores = new HashMap<>();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            List<Score> val = new ArrayList<>();
            in.readTypedList(val, Score.CREATOR);
            this.scores.put(key, val);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.courseName);
        dest.writeList(this.pars);
        dest.writeLong(date);
        dest.writeInt(this.scores.size());
        for (String key: this.scores.keySet()) {
            List<Score> score = this.scores.get(key);
            dest.writeString(key);
            dest.writeList(score);
        }
    }

    /**
     * This field is needed for Android to be able to
     * create new objects, individually or as arrays
     *
     * If you don’t do that, Android framework will through exception
     * Parcelable protocol requires a Parcelable.Creator object called CREATOR
     */
    public static final Parcelable.Creator<Round> CREATOR = new Parcelable.Creator<Round>() {

        public Round createFromParcel(Parcel in) {
            return new Round(in);
        }

        public Round[] newArray(int size) {
            return new Round[size];
        }
    };
}
