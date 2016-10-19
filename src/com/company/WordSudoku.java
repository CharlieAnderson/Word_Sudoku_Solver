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
    private boolean placedVertical;

    public WordSudoku() {
        this.grid = new Grid();
        this.wordBank = new ArrayList<>();
        this.placedVertical = false;
    }

    public void initGame() {
        this.grid = new Grid();
        this.grid.printGrid();
        readBank("bank1.txt");
        wordBank.sort(Collections.reverseOrder(new LengthComparator()));
        solve(0, 0, "");
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
    public boolean solve(int i, int j, String lastWord ) {
        if(i==9) {
            i = 0;
            if(++j == 9) {
                return true;
            }
        }
        if(!grid.quickCheck(i,j))
            solve(i+1,j, lastWord);

        for(int x = 0; x<wordBank.size(); x++) {
            if(grid.tryVertical(i, j, wordBank.get(x))) {
                lastWord = wordBank.get(x);
                placedVertical = false;
                if(solve(i, j+1, lastWord))
                    return true;
            }
        }
        grid.removeVertical(i, j, lastWord);

        for(int x = 0; x<wordBank.size(); x++) {
            if(grid.tryHorizontal(i, j, wordBank.get(x))) {
                lastWord = wordBank.get(x);
                placedVertical = false;
                if(solve(i, j+1, lastWord))
                    return true;
            }
        }
        grid.removeHorizontal(i, j, lastWord);

        return false;
    }
}
