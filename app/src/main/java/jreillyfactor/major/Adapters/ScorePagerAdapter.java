package jreillyfactor.major.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import jreillyfactor.major.EnterScoreFragment;
import jreillyfactor.major.Models.Score;

/**
 * Created by James Reilly on 8/1/2016.
 */
public class ScorePagerAdapter extends FragmentPagerAdapter {
    Context context;
    private List<Score> mScores;
    private String mKey;
    public  ScorePagerAdapter(Context context, FragmentManager fm, List<Score> scores, String key) {
        super(fm);
        this.context = context;
        this.mScores = scores;
        this.mKey = key;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new EnterScoreFragment();
        System.out.println("Getting fragment");
        Bundle bundle = new Bundle();
        bundle.putParcelable("score", mScores.get(position));
        bundle.putString("roundKey", mKey);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
    return mScores.size();
}
}

