package jreillyfactor.major;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James Reilly on 7/29/2016.
 */
public class ParInputView extends LinearLayout {

    TextView[] pars = new TextView[18];

    public ParInputView(Context context) {
        super(context);
        init();
    }
    public ParInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ParInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.par_input_view, this);
        initParFields();
        initParButtons();
    }

    private void initParFields() {
        for (int i = 0; i < pars.length; i++) {
            Resources r = getResources();
            String name = getContext().getPackageName();
            pars[i] = (TextView) findViewById(r.getIdentifier("hole" + (i + 1), "id", name));
        }
    }

    private void initParButtons() {
        for (int i = 0; i < pars.length; i++) {
            Resources r = getResources();
            String name = getContext().getPackageName();
            ImageButton add = (ImageButton) findViewById(r.getIdentifier("add_hole" + (i + 1), "id", name));
            ImageButton subtract = (ImageButton) findViewById(r.getIdentifier("subtract_hole" + (i + 1), "id", name));

            // Pass the index to the buttons
            add.setTag(i);
            subtract.setTag(i);

            add.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = (Integer) v.getTag();
                    int value = Integer.parseInt(pars[index].getText().toString()) + 1;
                    pars[index].setText(String.valueOf(value));
                }
            });

            subtract.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = (Integer) v.getTag();
                    int value = Integer.parseInt(pars[index].getText().toString()) - 1;
                    if (value >= 3) {
                        pars[index].setText(String.valueOf(value));
                    }
                }
            });
        }
    }

    public List<Integer> getPars() {
        List<Integer> finalPars = new ArrayList<>();
        for(int i = 0; i < 18; i++) {
            finalPars.add(Integer.parseInt(pars[i].getText().toString()));
        }
        return finalPars;
    }
}
