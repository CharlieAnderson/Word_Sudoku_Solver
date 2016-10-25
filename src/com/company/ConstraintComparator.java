package com.company;

import java.util.Comparator;

/**
 * Created by charlesanderson on 10/24/16.
 */
public class ConstraintComparator implements Comparator<Pair> {

    // puts lower cVal on top, need to then everse this ordering to get most constrained
    @Override
    public int compare(Pair a, Pair b) {
        return Integer.compare(a.cVal, b.cVal);
    }
}
