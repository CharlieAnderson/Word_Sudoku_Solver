package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by charlesanderson on 10/17/16.
 */
public class WordSudoku {
    private Grid grid;
    private ArrayList<String> wordBank;

    public WordSudoku() {
        this.grid = new Grid();
        this.wordBank = new ArrayList<>();
    }

    public void initGame() {
        this.grid = new Grid();
        this.grid.printGrid();
        readBank("bank1.txt");
        wordBank.sort(Collections.reverseOrder(new LengthComparator()));
        solve(0, 0);
        this.grid.printGrid();
        System.out.println("Result: "+this.grid.checkSolution());
    }
    public void readBank(String bank) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(bank))) {
            String word;
            while((word = buffer.readLine()) != null) {
                wordBank.add(word.trim());
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    public boolean solve(int x, int y) {
        if(y==9) {
            y = 0;
            if(x++ == 9) {
                return true;
            }
        }
        if(!grid.quickCheck(x,y))
            solve(x+1,y);

        for(int i = 0; i<wordBank.size(); i++) {
            if(grid.checkMove(x, y, wordBank.get(i))) {
                if(solve(x+1, y))
                    return true;
            }
        }
        return false;
    }
}
