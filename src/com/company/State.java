package com.company;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by charlesanderson on 10/24/16.
 */
public class State {
    public State prev;
    public Grid grid;
    public ArrayList<String> values;
    public ArrayList<String> wordbank;
    public ArrayList<Pair> variables;

    public State(State prev, Grid grid, ArrayList values, ArrayList variables, ArrayList wordbank) {
        this.prev = prev;
        this.grid = grid;
        this.values = values;
        this.variables = variables;
        this.values.sort(new LengthComparator());
        this.variables.sort(Collections.reverseOrder(new ConstraintComparator()));
        this.wordbank = wordbank;
    }


    // finds and sets the current list of variables in order of how constrained they are
    public ArrayList<Pair> findVariables() {
        ArrayList<Pair> vars = grid.findEmptyVariables();
        vars.sort(Collections.reverseOrder(new ConstraintComparator()));
        this.variables = vars;
        return vars;
    }

    //  most constrained variable, this will be the x,y coord with the most other variables in its row/col
    public Pair<Integer, Integer> findMCV () {
        Pair mcv;
        if(!this.variables.isEmpty()) {
            mcv = this.variables.get(0);
            return mcv;
        }
        else
            return null;
    }


    // lease constraining word should just be the shortest word?
    public String findLCV(int row, int col) {
        this.values.sort(new LengthComparator());
        return this.values.get(0);
    }

    // lease constraining word should just be the shortest word?
    public void sortLCV(int row, int col) {
        this.values.sort(new LengthComparator());
    }
    public void sortMCV() {
        this.variables.sort(Collections.reverseOrder(new ConstraintComparator()));
    }
}
