package jreillyfactor.major;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by James Reilly on 7/29/2016.
 */
public class FeedFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedIntanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        // Init button on click listeners

        return view;
    }
}
