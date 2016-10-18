package com.company;

import java.util.ArrayList;

/**
 * Created by charlesanderson on 10/17/16.
 */
public class WordSudoku {
    private Grid grid;
    private ArrayList<String> wordBank;

    public WordSudoku() {
        this.grid = new Grid();
        this.wordBank = new ArrayList<String>();
    }

    public void initGame() {
        this.grid = new Grid();
    }
}
