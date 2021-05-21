package Sodoku.Solver;

import backtracker.Configuration;
import backtracker.Backtracker;
import Sodoku.Model.SodokuConfiguration;
import java.io.IOException;
import java.util.Optional;

public class SodokuSolver {
    public static void main(String[] args) {
        // check for file name and debug flag on command line
        if (args.length != 2) {
            System.err.println("Usage: java Sodoku input-file debug");
        } else {
            try {
                // construct the initial configuration from the file based
                Configuration init = new SodokuConfiguration(args[0]);

                System.out.println("Initial config:\n" + init);

                // create the backtracker with the debug flag
                boolean debug = args[1].equals("true");
                Backtracker bt = new Backtracker(debug);

                // attempt to solve the puzzle
                Optional<Configuration> sol = bt.solve(init);

                // display the number of configs generated
                System.out.println("Number of configs generated: " + bt.getNumConfigs());

                // indicate whether there was a solution, or not
                if (sol.isPresent()) {
                    System.out.println("Solution:\n" + sol.get());
                } else {
                    System.out.println("No solution!");
                }
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }
}