package jreillyfactor.major.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import jreillyfactor.major.MainActivity;
import jreillyfactor.major.Models.Round;
import jreillyfactor.major.R;

/**
 * Created by James Reilly on 7/29/2016.
 */
public class RoundAdapter extends RecyclerView.Adapter <RoundAdapter.ViewHolder> {
    private Round[] mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mCourseName;
        public TextView mDate;
        public ViewHolder(View v) {
            super(v);
            mCourseName = (TextView) v.findViewById(R.id.course_name);
            mDate = (TextView) v.findViewById(R.id.date);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RoundAdapter(Context context, Round[] myDataset) {
        mDataset = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RoundAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.round_card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        Round round = mDataset[position];
        if (round != null) {
            System.out.println(round.courseName);
            holder.mCourseName.setText(round.courseName);
            Date d = new Date(mDataset[position].date);
            holder.mDate.setText(d.toString());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Clicking the round!");
                    goToRound(mDataset[position].key);
                }
            });
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public void updateList(Round[] data) {
        mDataset = data;
        notifyDataSetChanged();
    }
    public void goToRound(String key) {
        if (mContext == null) {
            return;
        }
        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) mContext;
            mainActivity.showRound(key);
        }
    }
}
