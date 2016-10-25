package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by charlesanderson on 10/17/16.
 */
public class WordSudoku {
    private Grid grid;
    private Grid finalgrid;

    private ArrayList<String> wordBank;
    private ArrayList<String> usedWords;
    private int smallest;
    public int nodesExpanded;

    public WordSudoku() {
        this.grid = new Grid();
        this.wordBank = new ArrayList<>();
        this.usedWords = new ArrayList<>();
    }

    public void initGame() {
        this.grid = new Grid();
        this.grid.printGrid();
        readBank("bank2.txt");
        wordBank.sort(new LengthComparator());
        this.smallest = wordBank.get(0).length();
        //System.out.println(solve(0, 0));
        start();
        //this.grid.printGrid();
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



    public void start() {
        State startState = new State(null, grid, wordBank, grid.findEmptyVariables(), wordBank);
        System.out.println("final result: "+backtrack(startState));
        System.out.println("nodes expanded: "+nodesExpanded);
    }


    public boolean backtrack(State state) {

        // check if csp is solved
        if(state.grid.checkFinal()) {
            System.out.println("final grid:");
            state.grid.printGrid();
            finalgrid = state.grid;
            return true;
        }
        else if(state.variables.isEmpty()) {
            System.out.println("final grid:");
            state.grid.printGrid();
            finalgrid = state.grid;
            return true;
        }

        Pair<Integer, Integer> cell = state.variables.get(0);
        String word = state.findLCV(cell.i, cell.j);


        // skip filled variables

        /*
        while((state.grid.grid[cell.i][cell.j].c != '_') && !state.variables.isEmpty()) {
            state.variables.remove(0);
            cell = state.variables.get(0);
        }

*/

        boolean madeAssignment = false;

        // loop through values for this variable
        for(int i = 0; i<state.values.size(); i++) {
            word = state.values.get(i);
            if (state.grid.tryHorizontal(cell.i, cell.j, word)) {
                //System.out.println("placed " + word + " horizontally");
                madeAssignment = true;
                ArrayList<String> values = new ArrayList<>(state.values);
                ArrayList<Pair> variables = new ArrayList<>(state.variables);
                values.remove(word);
                variables.remove(cell);
                state.grid.placeHorizontally(cell.i, cell.j, word); // before or after creating new state?
                State newstate = new State(state, state.grid, values, variables, wordBank);
                //state.variables = variables;
                //newstate.grid.printGrid();
                nodesExpanded++;
                if (backtrack(newstate))
                    return true;
                else {
                    state.grid.removeHorizontal(cell.i, cell.j, word);
                }
            }
            if (state.grid.tryVertical(cell.i, cell.j, word)) {
                //System.out.println("placed " + word + " vertically");
                madeAssignment = true;
                ArrayList<String> values = new ArrayList<>(state.values);
                ArrayList<Pair> variables = new ArrayList<>(state.variables);
                values.remove(word);
                variables.remove(cell);
                state.grid.placeVertically(cell.i, cell.j, word); // before or after creating new state?
                State newstate = new State(state, state.grid, values, variables, wordBank);
                //state.variables = variables;
                //newstate.grid.printGrid();
                nodesExpanded++;
                if (backtrack(newstate))
                    return true;
                else {
                    state.grid.removeVertical(cell.i, cell.j, word);
                }
            }
        }

        //System.out.println(" false row:"+cell.i + " col:"+cell.j);
        state.grid.printGrid();
        finalgrid = state.grid;
        return false;
    }
}
