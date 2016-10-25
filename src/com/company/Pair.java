package com.company;

/**
 * Created by charlesanderson on 10/24/16.
 */
public class Pair<I, J> {
        public I i;
        public J j;
        public int cVal; // constraint value for determining most constrained var
        public Pair(I i, J j, int cVal) {
            this.i = i;
            this.j = j;
            this.cVal = cVal;
        }
}
