package jreillyfactor.major.Models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by James Reilly on 7/29/2016.
 */
public class Round {
    public String key;
    public String courseName;
    public List<Integer> pars;
    public Long date;
    public Map<String, List<Score>> scores;

    public Round() {}

    public Round(String key, String courseName, List<Integer> pars, ArrayList<User> players, Date date) {
        this.courseName = courseName;
        this.pars = pars;
        this.date = date.getTime();
        this.scores = new HashMap<>();
        for (User u: players) {
            // Init scores to par
            List<Score> newScores = new ArrayList<>();
            for (Integer i = 0; i < pars.size(); i++) {
                newScores.add(new Score(i + 1, pars.get(i)));
            }
            this.scores.put(u.uid, newScores);
        }
        this.key = key;
    }
}
