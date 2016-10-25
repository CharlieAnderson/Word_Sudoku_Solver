package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by charlesanderson on 10/17/16.
 */
public class Grid {
    public CellGroup[][] groups;


    public Cell[][] grid;


    public Grid() {
        // TODO: actually implement cellgroups instead of ignoring for now
        this.groups = new CellGroup[3][3];
        this.grid = new Cell[9][9];
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                this.grid[i][j] = new Cell(j, i, '_');
            }
        }
    }

    public void printGrid() {
        System.out.println();
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                System.out.print(grid[i][j].c + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    public boolean checkSolution() {
        // Check Rows for duplicates
        Set found = new HashSet();
        for(int i=0; i<9; i++) {
            found.clear();
            for(int j=0; j<9; j++) {
                if(grid[i][j].c == '_')
                    continue;
                else if(!found.add(grid[i][j].c)) {
                    //System.out.println("row violation at row:"+i+" col:"+j);
                    return false;
                }
            }
        }
        // Check Columns for duplicates
        for(int i=0; i<9; i++) {
            found.clear();
            for(int j=0; j<9; j++) {
                if(grid[j][i].c == '_')
                    continue;
                else if(!found.add(grid[j][i].c)) {
                    //System.out.println("col violation at row:"+j+" col:"+i);
                    return false;
                }
            }
        }
        // Check 3x3 groups for duplicates

        for(int k=0; k<9; k++) {
            found.clear();
            for(int i = 0; i<3; i++) {
                for(int j = 0; j<3; j++) {
                    if(grid[i+3*(int)Math.floor(k/3)][j+k%3*3].c == '_')
                        continue;
                    else if(!found.add(grid[i+3*(int)Math.floor(k/3)][j+k%3*3].c)) {
                        //System.out.println("group violation at row:"+(i+3*(int)Math.floor(k/3)) + " col:"+(j+k%3*3));
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean checkFinal() {
        for(int i = 0; i<9; i++) {
            for(int j = 0; j<9; j++) {
                if(grid[i][j].c == '_') {
                    return false;
                }
            }
        }
        return checkSolution();
    }


    public boolean checkMove(int row, int col, String word) {

        if(checkHorizontal(row, col, word)) {
            placeHorizontally(row, col, word);
            if(!checkSolution())
                removeHorizontal(row, col, word);
            else {
                removeHorizontal(row, col, word);
                return true;
            }
        }
        else if(checkVertical(row, col, word)) {
            placeVertically(row, col, word);
            if(!checkSolution())
                removeVertical(row, col, word);
            else {
                removeVertical(row, col, word);
                return true;
            }
        }
        return false;
    }

    public boolean tryVertical(int row, int col, String word) {

        if(checkVertical(row, col, word)) {
            placeVertically(row, col, word);
            //System.out.println("added vertically for tryVertical");
            if(!checkSolution()) {
                //System.out.println(word+ " was not valid for vertical");
                //System.out.println("removed vertically for tryVertical");
                removeVertical(row, col, word);
                return false;
            }
            else {
                removeVertical(row, col, word);
                return true;
            }
        }
        return false;
    }

    public boolean tryHorizontal(int row, int col, String word) {

        if(checkHorizontal(row, col, word)) {
            placeHorizontally(row, col, word);
            //System.out.println("added horizontally for tryHorizontal");

            if(!checkSolution()) {
                //System.out.println(word+ " was not valid for horizontal");
                removeHorizontal(row, col, word);
                //System.out.println("removed horizontally for tryHorizontal");
                return false;
            }
            else {
                removeHorizontal(row, col, word);
                //System.out.println("removed horizontally for tryHorizontal");
                return true;
            }
        }
        return false;
    }

    public boolean quickCheck(int row, int col) {
        //System.out.println("Quickcheck: row "+row+", col "+col);
        return checkHorizontal(row, col, "1") || checkVertical(row, col, "1");
    }

    public boolean checkHorizontal(int row, int col, String word) {
        // Check if word will fit
        if(word.length() <= 9-col) {
            // Check if any of these spaces are taken
            for(int j = 0; j<word.length(); j++) {
                if(grid[row][j+col].c != '_' && grid[row][j+col].c != word.charAt(j))
                    return false;
            }
            return true;
        }
        return false;
    }

    public boolean checkVertical(int row, int col, String word) {
        // Check if word will fit
        if(word.length() <= 9-row) {
            // Check if any of these spaces are taken
            for(int i = 0; i<word.length(); i++) {
                if(grid[i+row][col].c != '_' && grid[i+row][col].c != word.charAt(i))
                    return false;
            }
            return true;
        }
        return false;
    }

    public void placeHorizontally(int row, int col, String word) {
        for(int j=0; j<word.length(); j++) {
            grid[row][j+col].c = word.charAt(j);
        }
    }

    public void placeVertically(int row, int col, String word) {
        for(int i=0; i<word.length(); i++) {
            grid[row+i][col].c = word.charAt(i);
        }
    }

    public void removeHorizontal(int row, int col, String word) {
        for(int j=0; j<word.length(); j++) {
                grid[row][j+col].c = '_';
        }
    }

    public void removeVertical(int row, int col, String word) {
        for(int i=0; i<word.length(); i++) {
                 grid[row+i][col].c = '_';
        }
    }

    // calculates how constrained a cell(variable) is on the grid
    public int calculateConstraints(int row, int col) {
        int count = 0;

        // Check Rows for taken cells
        for(int i=0; i<9; i++) {
            if(grid[i][col].c == '_')
                count++;
        }
        // Check Columns for taken cells
        for(int j=0; j<9; j++) {
            if(grid[row][j].c != '_')
                count++;
        }

        // Check 3x3 groups for taken cells
        int y = (row+3)%3;
        int x = (col+3)%3;
        for(int i = y; i<y+3; i++) {        // should i flip the x's and y's?
            for(int j = x; j<x+3; j++) {
                if(grid[y][x].c != '_')
                    count++;
            }
        }
        return count;
    }

    public ArrayList<Pair> findEmptyVariables() {
        ArrayList<Pair> vars = new ArrayList<>();
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                if(grid[i][j].c == '_'){
                    Pair<Integer, Integer> emptyPair = new Pair<>(i, j, calculateConstraints(i, j));
                    vars.add(emptyPair);
                }
            }
        }

        return vars;
    }

    public boolean checkIfFilled() {
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                if(grid[i][j].c == '_'){
                    return false;
                }
            }
        }
        return true;
    }

}
