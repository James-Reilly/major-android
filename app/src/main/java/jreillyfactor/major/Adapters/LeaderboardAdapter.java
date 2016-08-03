package jreillyfactor.major.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jreillyfactor.major.MainActivity;
import jreillyfactor.major.Models.Player;
import jreillyfactor.major.Models.Round;
import jreillyfactor.major.Models.Score;
import jreillyfactor.major.Models.User;
import jreillyfactor.major.R;

/**
 * Created by James Reilly on 8/2/2016.
 */
public class LeaderboardAdapter extends RecyclerView.Adapter <LeaderboardAdapter.ViewHolder> {
    private List<Round> mRounds;
    private ArrayList<Player> mPlayers;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mPlayerName;
        public TextView mPosition;
        public TextView mToday;
        public TextView mThru;
        public TextView mTotal;

        public ViewHolder(View v) {
            super(v);
            mPlayerName = (TextView) v.findViewById(R.id.player_name);
            mPosition = (TextView) v.findViewById(R.id.player_position);
            mToday = (TextView) v.findViewById(R.id.score_today);
            mThru = (TextView) v.findViewById(R.id.through_hole);
            mTotal = (TextView) v.findViewById(R.id.score_total);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public LeaderboardAdapter(Context context, List<Round> rounds) {
        this.mRounds = rounds;
        generateDataSet();
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LeaderboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Player player = mPlayers.get(position);
        if (player != null) {
            holder.mPosition.setText(player.position);
            holder.mPlayerName.setText(player.name);
            if (player.today == 0) {
                holder.mToday.setText("E");
            } else if (player.today > 0) {
                holder.mToday.setText("+" + player.today.toString());
            } else {
                holder.mToday.setText(player.today.toString());
            }

            holder.mThru.setText(player.thru);

            if (player.total == 0) {
                holder.mTotal.setText("E");
            } else if (player.total > 0) {
                holder.mTotal.setText("+" + player.total.toString());
            } else {
                holder.mTotal.setText(player.total.toString());
            }
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPlayers.size();
    }

    public void updateList(List<Round> data) {
        mRounds = data;
        generateDataSet();
        notifyDataSetChanged();
    }

    private void generateDataSet() {
        Map<String, Player> playerHash = new HashMap<>();
        if (mRounds != null) {
            // Generate players
            for(int i = 0; i < mRounds.size(); i++) {
                Round round = mRounds.get(i);

                for(String uid : round.scores.keySet()) {
                    List<Score> scores = round.scores.get(uid);
                    Player player = playerHash.get(uid);
                    if (player == null) {
                        player = new Player(uid);
                        playerHash.put(uid, player);
                    }
                    for (int k = 0; k < scores.size(); k++) {
                        Score score = scores.get(k);
                        if (score.rawScore == 0) {
                            player.thru = score.number.toString();
                            break;
                        } else {
                            System.out.println("Adding score for hole " + score.number );
                            System.out.println((score.rawScore - score.par));
                            player.today += (score.rawScore - score.par);
                            player.total += (score.rawScore - score.par);
                        }
                    }
                }

            }
            // Convert Map to List and Sort by score
            mPlayers = new ArrayList<>(playerHash.values());
            Collections.sort(mPlayers, new PlayerComparator());
            // Give Positions
            for (Integer i = 1; i <= mPlayers.size(); i++) {
                mPlayers.get(i - 1).position = i.toString();
                getName(mPlayers.get(i-1));
            }
        }
    }

    private void getName(final Player p) {
        FirebaseDatabase.getInstance().getReference().child("users").child(p.uid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Log.d("PLAYER", "Got User");
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            p.name = user.username;
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("Dialog", "getUser:onCancelled", databaseError.toException());
                    }
                });
    }


    private class PlayerComparator implements Comparator<Player> {
        @Override
        public int compare(Player p1, Player p2) {
            return Integer.compare(p1.total, p2.total);
        }
    }
}
