package jreillyfactor.major;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by James Reilly on 7/18/2016.
 */
public class LoginFragment extends Fragment {

    OnLoginListener mCallback;

    public interface OnLoginListener {
        public void login(String email, String password);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedIntanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        // Init button on click listeners

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.login_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle("Login to " + getResources().getString(R.string.app_name));

        setUpButtons(view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            Activity activity = (Activity) context;
            try {
                mCallback = (OnLoginListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() +
                        " must implement OnLoginListener");
            }
        }

    }


    private void setUpButtons(final View view) {
        final Button loginButton = (Button) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Callback.presentLogin();
                EditText mEmailField = (EditText) view.findViewById(R.id.email_field);
                EditText mPasswordField = (EditText) view.findViewById(R.id.password_field);
                String email = mEmailField.getText().toString();
                String password = mPasswordField.getText().toString();
                System.out.println(email + " " + password);
                mCallback.login(email, password);
            }
        });
    }
}
