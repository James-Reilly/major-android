package jreillyfactor.major;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import jreillyfactor.major.Models.Score;

/**
 * Created by James Reilly on 7/27/2016.
 */
public class EnterScoreFragment extends Fragment {

    private Score mScore;
    private String mKey;
    private TextView mScoreTextView;
    private TextView mHoleNumberTextView;
    private TextView mParTextView;
    private TextView mPuttTextView;
    private TextView mPenaltyTextView;

    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frament_score, container, false);
        // Init button on click listeners
        mScore = getArguments().getParcelable("score");
        mKey = getArguments().getString("roundKey");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (mScore != null) {
            initTextViews(view);
            initPagerButtons(view);
            initStatButtons(view);
        }

        return view;
    }

    private void initPagerButtons(View view) {
        ImageButton prevButton = (ImageButton) view.findViewById(R.id.prev_hole);
        ImageButton nextButton = (ImageButton) view.findViewById(R.id.next_hole);
        if (mScore.number == 1) {
            prevButton.setVisibility(View.INVISIBLE);
        } else {
            prevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Prev Hole
                    ((ScoreEnterPagerFragment)getParentFragment()).goToPage(mScore.number - 1);
                }
            });
        }

        if (mScore.number == 18) {
            nextButton.setVisibility(View.INVISIBLE);
        } else {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Next Hole
                    ((ScoreEnterPagerFragment)getParentFragment()).goToPage(mScore.number + 1);
                }
            });
        }

    }

    private void initStatButtons(View view) {
        ImageButton subScore = (ImageButton) view.findViewById(R.id.score_subtract);
        ImageButton addScore = (ImageButton) view.findViewById(R.id.score_add);
        subScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mScore.rawScore != 1) {
                    mScore.rawScore -= 1;
                    setTextFromScore();
                    writeScoreToServer();
                }
            }
        });
        addScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScore.rawScore += 1;
                setTextFromScore();
                writeScoreToServer();
            }
        });

        ImageButton subPutts = (ImageButton) view.findViewById(R.id.putts_subtract);
        ImageButton addPutts = (ImageButton) view.findViewById(R.id.putts_add);
        subPutts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mScore.putts != 0) {
                    mScore.putts -= 1;
                    setTextFromScore();
                    writeScoreToServer();
                }
            }
        });

        addPutts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScore.putts += 1;
                setTextFromScore();
                writeScoreToServer();
            }
        });

        ImageButton subPenalties = (ImageButton) view.findViewById(R.id.penalties_subtract);
        ImageButton addPenalties = (ImageButton) view.findViewById(R.id.penalties_add);
        subPenalties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mScore.penalties != 0) {
                    mScore.penalties -= 1;
                    setTextFromScore();
                    writeScoreToServer();
                }
            }
        });

        addPenalties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScore.penalties += 1;
                setTextFromScore();
                writeScoreToServer();
            }
        });

    }

    private void initTextViews(View view) {
        mHoleNumberTextView = (TextView) view.findViewById(R.id.hole_number);
        mParTextView = (TextView) view.findViewById(R.id.par);
        mHoleNumberTextView.setText("Hole: " + mScore.number.toString());
        mParTextView.setText("Par: " + mScore.par.toString());

        mScoreTextView = (TextView) view.findViewById(R.id.score);
        mPuttTextView = (TextView) view.findViewById(R.id.putts);
        mPenaltyTextView = (TextView) view.findViewById(R.id.penalties);
        setTextFromScore();
    }

    private void setTextFromScore() {
        mScoreTextView.setText(mScore.rawScore.toString());
        mPuttTextView.setText(mScore.putts.toString());
        mPenaltyTextView.setText(mScore.penalties.toString());
    }

    private void writeScoreToServer() {
        Integer scoreIndex = mScore.number - 1;
        System.out.println(mKey);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        System.out.println(mDatabase.child("rounds").child(mKey).child("scores").child(scoreIndex.toString()));
        mDatabase.child("rounds").child(mKey).child("scores").child(user.getUid()).child(scoreIndex.toString()).setValue(mScore);
    }

}
