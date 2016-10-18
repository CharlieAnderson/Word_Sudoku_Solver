package com.company;

/**
 * Created by charlesanderson on 10/17/16.
 */
public class Grid {
    public CellGroup[][] grid;

    /*TODO: maybe create a class for each 3x3 cell that contains the cells within,
     * to easily check if a character is in a cell
     */
    public Grid() {
        this.grid = new CellGroup[3][3];
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                grid[i][j] = new CellGroup(j, i); // i, j or j, i???
            }
        }
    }
}
