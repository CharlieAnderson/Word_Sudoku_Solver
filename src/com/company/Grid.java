package com.company;

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
                System.out.print(grid[i][j].c +""+ grid[i][j].c + " ");
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
                else if(!found.add(grid[i][j].c))
                    return false;
            }
        }
        // Check Columns for duplicates
        for(int i=0; i<9; i++) {
            found.clear();
            for(int j=0; j<9; j++) {
                if(grid[j][i].c == '_')
                    continue;
                else if(!found.add(grid[j][i].c))
                    return false;
            }
        }
        // Check 3x3 groups for duplicates
        /*
        for(int k=0; k<9; k++) {
            found.clear();
            for(int i = 0; i<3; i++) {
                for(int j = 0; j<3; j++) {
                    if(grid[i+3*k/3][j+k%3*3].c == '_')
                        continue;
                    else if(!found.add(grid[i+3*k/3][j+k%3*3].c))
                        return false;
                }
            }
        }
        */
        return true;
    }

    public boolean checkMove(int x, int y, String word) {

        if(checkHorizontal(x, y, word)) {
            placeHorizontally(x, y, word);
            if(!checkSolution())
                removeHorizontal(x, y, word);
            else
                return true;
        }
        else if(checkVertical(x, y, word)) {
            placeVertically(x, y, word);
            if(!checkSolution())
                removeVertical(x, y, word);
            else
                return true;
        }
        return false;
    }

    public boolean quickCheck(int x, int y) {
        return checkHorizontal(x, y, "_") || checkVertical(x, y, "_");
    }

    public boolean checkHorizontal(int x, int y, String word) {
        // Check if word will fit
        if(word.length() <= 9-x) {
            // Check if any of these spaces are taken
            for(int j = 0; j<9; j++) {
                if(grid[y][j].c != '_')
                    return false;
            }
            return true;
        }
        return false;
    }

    public boolean checkVertical(int x, int y, String word) {
        // Check if word will fit
        if(word.length() <= 9-y) {
            // Check if any of these spaces are taken
            for(int i = 0; i<9; i++) {
                if(grid[i][x].c != '_')
                    return false;
            }
            return true;
        }
        return false;
    }

    public void placeHorizontally(int x, int y, String word) {
        for(int j=0; j<word.length(); j++) {
            grid[y][j+x].c = word.charAt(j);
        }
    }

    public void placeVertically(int x, int y, String word) {
        for(int i=0; i<word.length(); i++) {
            grid[y+i][x].c = word.charAt(i);
        }
    }

    public void removeHorizontal(int x, int y, String word) {
        for(int j=0; j<word.length(); j++) {
            grid[y][j+x].c = '_';
        }
    }

    public void removeVertical(int x, int y, String word) {
        for(int i=0; i<word.length(); i++) {
            grid[y+i][x].c = '_';
        }
    }
}
