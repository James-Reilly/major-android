package jreillyfactor.major.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import jreillyfactor.major.CurrentRoundFragment;
import jreillyfactor.major.EnterScoreFragment;
import jreillyfactor.major.FeedFragment;
import jreillyfactor.major.Models.Round;
import jreillyfactor.major.ScoreEnterPagerFragment;

/**
 * Created by James Reilly on 7/27/2016.
 */
public class RoundPagerAdapter extends FragmentStatePagerAdapter {
    Context context;
    private Round mRound;
    public  RoundPagerAdapter(Context context, FragmentManager fm, Round round) {
        super(fm);
        this.context = context;
        this.mRound = round;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        System.out.println("Getting fragment");
        switch (position) {
            case 0:
                fragment = new FeedFragment();
                break;
            case 1:

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null && mRound != null) {
                    fragment = new ScoreEnterPagerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("scores", (ArrayList<? extends Parcelable>) mRound.scores.get(user.getUid()));
                    bundle.putString("roundKey", mRound.key);
                    fragment.setArguments(bundle);
                } else {
                    fragment = new FeedFragment();
                }
                break;
            case 2:
                fragment =  new FeedFragment();
                break;
            default:
                // Should never reach
                fragment = null;
        }
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 3;
    }

    public void setRound(Round round) {
        System.out.println("Setting round!");
        this.mRound = round;
        notifyDataSetChanged();
    }
}
