package Sodoku.GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import backtracker.Observer;
import Sodoku.Model.ClientData;
import Sodoku.Model.SodokuModel;
import javafx.stage.FileChooser;
import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SodokuGUI extends Application implements Observer<SodokuModel, ClientData> {
    private SodokuModel model;
    private ArrayList<Button> buttonsList = new ArrayList<>();
    private boolean isStartStage = true;
    private Stage stage;
    private int WINDOW_SIZE = 500;
    private String startPuzzle = "data/sodoku1.txt";

    /**
     * initialize the model, add this gui to the observer
     */
    public void init() {
        try {
            this.model = new SodokuModel(startPuzzle);
            model.addObserver(this);
        }
        catch (IOException e){
            System.out.println("Can't open file: " + getParameters().getRaw().get(0));
        }
    }

    /**
     * start the stage
     * @param stage window
     */
    @Override
    public void start(Stage stage){
        String css = this.getClass().getResource("style.css").toExternalForm();
        if (isStartStage){
            this.stage = new Stage();
            Button startButton = new Button("Start");
            startButton.setStyle("buttons:css");
            Button ruleButton = new Button("Rules");
            ruleButton.setStyle("-fx-background-color:\n" +
                    "            #3c7fb1,\n" +
                    "            linear-gradient(#fafdfe, #e8f5fc),\n" +
                    "            linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%);\n" +
                    "    -fx-background-insets: 0,1,2;\n" +
                    "    -fx-background-radius: 3,2,1;\n" +
                    "    -fx-padding: 3 30 3 30;\n" +
                    "    -fx-text-fill: black;\n" +
                    "    -fx-font-size: 14px;");
            VBox vBoxStart = new VBox();
            BorderPane startBorderPane = new BorderPane();
            vBoxStart.getChildren().addAll(startButton,ruleButton);
            startBorderPane.setCenter(vBoxStart);
            startBorderPane.setMaxSize(WINDOW_SIZE,WINDOW_SIZE);
            startBorderPane.setMinSize(WINDOW_SIZE,WINDOW_SIZE);
            Scene scene = new Scene(startBorderPane);
            vBoxStart.setAlignment(Pos.CENTER);
            startBorderPane.setBackground(new Background(new BackgroundImage(new Image(getClass().getResourceAsStream("resources/green_frog.png")), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,  new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true))));
            this.stage.setTitle("Sodoku GUI");
            this.stage.setScene(scene);
            this.stage.setResizable(false);
            this.stage.show();
            isStartStage = false;
            startButton.setOnAction(event -> {
                start(stage);});

        }

        else {
            //create layouts and buttons
            BorderPane borderPane = new BorderPane();
            borderPane.setBackground(new Background(new BackgroundImage(new Image(getClass().getResourceAsStream("resources/green_frog.png")), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,  new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true))));
            GridPane gridPane = new GridPane();
            VBox rightVbox = new VBox();
            Button loadButton = new Button("Reset");
            Button resetButton = new Button("Solve");
            Button hintButton = new Button("Submit");
            Label timeLabel = new Label();
            Button choosePuzzle = new Button();
            VBox leftVbox = new VBox();

            //right buttons
            rightVbox.getChildren().addAll(loadButton, resetButton, hintButton);

            //left
            leftVbox.getChildren().addAll(timeLabel, choosePuzzle);

            //center
            for (int row = 0; row < this.model.getCurrentConfig().getDIM(); ++row) {
                for (int col = 0; col < this.model.getCurrentConfig().getDIM(); ++col) {
                    int tempRow = row;
                    int tempCol = col;
                    Button button = new Button();
                    this.buttonsList.add(button);
//                button.setOnAction(event -> model.select(tempRow, tempCol));
                    gridPane.add(button, col, row);
                }
            }
            //set main border
            borderPane.setCenter(gridPane);
            borderPane.setLeft(leftVbox);
            borderPane.setRight(rightVbox);

//        //buttons get pressed
//        loadButton.setOnAction(event -> {
//            File file = fileChooser.showOpenDialog(stage);
//            if (file != null) {
//                this.rebuildGui = true;
//                this.model.load(file.toString());
//            }
//        });
//
//        resetButton.setOnAction(event -> {
//            model.reset();
//        });
//        hintButton.setOnAction(event -> {
//            model.hint();
//        });
            //set scene
            Scene scene = new Scene(borderPane);
            this.stage.setTitle("Sodoku GUI");
            this.stage.setScene(scene);
//        this.stage.setResizable(false);
            this.stage.show();
        }
    }

    @Override
    public void update(SodokuModel sodokuModel,ClientData clientData) {

    }

    /**
     * main
     * @param args user input
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

}
