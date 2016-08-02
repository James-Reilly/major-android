package jreillyfactor.major.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by James Reilly on 8/1/2016.
 */
public class Score implements Parcelable {
    public Integer number;
    public Integer par;
    public Integer rawScore;
    public Integer handicapScore;
    public Integer putts;
    public Integer penalties;
    public String fairway;

    private String[] fairwayTypes = {"HIT", "MISS", "LEFT", "RIGHT", "NA"};

    public Score() {}

    public Score(Integer number, Integer par) {
        this.number = number;
        this.par = par;
        this.handicapScore = par;
        this.rawScore = par;
        this.putts = 2;
        this.penalties = 0;
        this.fairway = "";
    }

    public Score(Parcel in) {
        this.number = in.readInt();
        this.par = in.readInt();
        this.handicapScore = in.readInt();
        this.rawScore = in.readInt();
        this.putts = in.readInt();
        this.penalties = in.readInt();
        this.fairway = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.number);
        dest.writeInt(this.par);
        dest.writeInt(this.handicapScore);
        dest.writeInt(this.rawScore);
        dest.writeInt(this.putts);
        dest.writeInt(this.penalties);
        dest.writeString(this.fairway);
    }

    /**
     * This field is needed for Android to be able to
     * create new objects, individually or as arrays
     *
     * If you don’t do that, Android framework will through exception
     * Parcelable protocol requires a Parcelable.Creator object called CREATOR
     */
    public static final Parcelable.Creator<Score> CREATOR = new Parcelable.Creator<Score>() {

        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        public Score[] newArray(int size) {
            return new Score[size];
        }
    };
}
