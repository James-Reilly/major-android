package jreillyfactor.major.Models;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import jreillyfactor.major.CurrentRoundFragment;

/**
 * Created by James Reilly on 7/27/2016.
 */
public class RoundPagerAdapter extends FragmentPagerAdapter {
    Context context;
    public  RoundPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        System.out.println("Getting fragment");
        switch (position) {
            case 0:
                fragment = new CurrentRoundFragment();
                break;
            case 1:
                fragment = new CurrentRoundFragment();
                break;
            case 2:
                fragment = new CurrentRoundFragment();
                break;
            default:
                // Should never reach
                fragment = null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
