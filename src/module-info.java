module side.projects.sodoku.puzzle {
    requires transitive javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    exports backtracker;
    exports Sodoku.GUI;
    exports Sodoku.Model;
}