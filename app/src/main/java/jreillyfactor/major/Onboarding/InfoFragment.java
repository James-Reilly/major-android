package jreillyfactor.major.Onboarding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import jreillyfactor.major.R;


/**
 * Created by James Reilly on 7/18/2016.
 */
public class InfoFragment extends Fragment {

    OnInfoOptionSelectedListener mCallback;

    public interface OnInfoOptionSelectedListener {
        public void presentLogin();
        public void presentSignUp();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedIntanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        // Init button on click listeners
        setUpButtons(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("Attaching!");
        if (context instanceof Activity){
            Activity activity = (Activity) context;
            System.out.println("Found actiivyt");
            try {
                mCallback = (OnInfoOptionSelectedListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() +
                        " must implement OnHeadlineSelectedListener");
            }
        }

    }

    private void setUpButtons(View view) {
        final Button loginButton = (Button) view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Login!");
                mCallback.presentLogin();
            }
        });

        final Button signUpButton = (Button) view.findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Sign Up!");
                mCallback.presentSignUp();
            }
        });
    }
}
