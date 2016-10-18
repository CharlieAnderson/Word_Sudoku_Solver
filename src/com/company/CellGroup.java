package com.company;

/**
 * Created by charlesanderson on 10/17/16.
 */
public class CellGroup {
    public Cell[][] group;
    public int x,y;

    public CellGroup(int x, int y) {
        this.x = x;
        this.y = y;
        this.group = new Cell[3][3];
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                group[i][j] = new Cell(j, i, '_');
            }
        }
    }
}
