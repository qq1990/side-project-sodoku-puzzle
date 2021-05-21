package Sodoku.Model;

import backtracker.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SodokuConfiguration implements Configuration {
    int DIM = 9;
    int[][] board;
    int row;
    int col;

    public SodokuConfiguration(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        //create empty board
        this.board = new int[DIM][DIM];
        //create the board
        for (int row=0; row<DIM; ++row) {
            for (int col=0; col<DIM; ++col) {
                int cell = Character.getNumericValue(in.read());
                this.board[row][col] = cell;
                in.read();
            }
        }
        in.close();
        this.row = 0;
        this.col = -1;
    }

    /**
     * check row if there is duplicate
     * @return false if there is, true if there is not
     */
    private boolean rowCheck(){
        for (int i = 0; i < DIM; i++){
            if (this.board[row][col] == this.board[row][i] && this.col != i){
                return false;
            }
        }
        return true;
    }

    /**
     * check column if there is duplicate
     * @return false if there is, true if there is not
     */
    private boolean colCheck(){
        for (int i = 0; i < DIM; i++){
            if (this.board[row][col] == this.board[i][col] && this.row != i){
                return false;
            }
        }
        return true;
    }

    /**
     * check subbox if there is duplicate
     * @return false if there is, true if there is not
     */
    private boolean boxCheck(){
        int boxCol = Math.floorDiv(col,3);
        int boxRow = Math.floorDiv(row,3);
        for(int i = boxRow*3; i < boxRow*3+3; i++){
            for(int j = boxCol*3; j < boxCol*3+3; j++){
                if(this.board[row][col]==this.board[i][j]&&this.row!=i &&this.col!=j){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Copy constructor.  Takes a config, other, and makes a full "deep" copy
     * of its instance data.
     * @param other the config to copy
     */
    private SodokuConfiguration(SodokuConfiguration other, int num) {
        this.row = other.row;
        this.col = other.col;
        this.board = new int[DIM][DIM];
        for (int r = 0; r < DIM; r++) {
            System.arraycopy(other.board[r], 0, this.board[r], 0, DIM);
        }
        this.board[this.row][this.col] = num;
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        List<Configuration> successors = new LinkedList<>();
        // advance cursor to this cell (for checking in isValid)
        this.col += 1;
        if (this.col == DIM) {
            this.row += 1;
            this.col = 0;
        }
        if (this.board[row][col]!=0){
            successors.add(new SodokuConfiguration(this, this.board[row][col]));
        }
        else {
            for (int i = 1; i < 10; i++) {
                successors.add(new SodokuConfiguration(this, i));
            }
        }
        return successors;
    }


    public SodokuConfiguration getValidConfig(){
        if(this.isValid()){
            return this;
        }
        return null;
    }

    public int getDIM(){
        return this.DIM;
    }


    @Override
    public boolean isValid() {
        return colCheck()&&rowCheck()&&boxCheck();
    }

    @Override
    public boolean isGoal() {
        //is last cell or not
        return this.row == DIM-1 && this.col == DIM-1;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for (int row=0; row<DIM; row++) {
            result.append("\n");
            for (int col=0; col<DIM; col++) {
                result.append(this.board[row][col]);
                result.append(" ");
            }
        }
        return result.toString();
    }
}
