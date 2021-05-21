package Sodoku.Model;

import backtracker.Observer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SodokuModel{
    /** the collection of observers of this model */
    private final List<Observer<SodokuModel, ClientData>> observers = new LinkedList<>();

    /** the current configuration */
    private SodokuConfiguration currentConfig;
    private String fileRunning;

    /**
     * Construct a ConcentrationModel; there is only one configuration.
     */
    public SodokuModel(String arg) throws IOException {
        this.currentConfig = new SodokuConfiguration(arg);
        alertObservers(new ClientData("Loaded: " + arg));
        this.fileRunning = arg;
    }


    /**
     * return the current configuration
     * @return current config
     */
    public SodokuConfiguration getCurrentConfig(){
        return this.currentConfig;
    }


    /**
     * hint
     */
    public void solve(){
        try {
            if (this.currentConfig.isGoal()){
                alertObservers(new ClientData("Solved!"));
            }
            else {
                this.currentConfig = this.currentConfig.getValidConfig();
                alertObservers(new ClientData("Next Step!"));
            }
        }
        catch (IndexOutOfBoundsException e){
            alertObservers(new ClientData("No solution!"));
        }
    }

    /**
     * reset the game
     */
    public void reset(){
        try {
            this.currentConfig = new SodokuConfiguration(fileRunning);
            alertObservers(new ClientData("Loaded: " + fileRunning));
            alertObservers(new ClientData("Puzzle reset!"));
        }
        catch (Exception e){
            alertObservers(new ClientData("Trouble resetting game (check file)"));
        }
    }

    /**
     * load a file to play
     * @param file list of string
     */
    public void load(String file){
        try {
            this.currentConfig = new SodokuConfiguration(file);
            this.fileRunning = file;
            alertObservers(new ClientData("Loaded: " + fileRunning));
        }
        catch (IOException e){
            alertObservers(new ClientData("Failed to load: " + file));
        }
    }

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<SodokuModel, ClientData> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(ClientData data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }
}
