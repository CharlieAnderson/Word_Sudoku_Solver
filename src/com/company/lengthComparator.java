package com.company;

import java.util.Comparator;

/**
 * Created by charlesanderson on 10/18/16.
 */
public class LengthComparator implements Comparator<String> {

    @Override
    public int compare(String str1, String str2) {
        return Integer.compare(str1.length(), str2.length());
    }
}
