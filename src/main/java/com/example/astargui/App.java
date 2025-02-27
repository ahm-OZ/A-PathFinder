package com.example.astargui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {


  @Override
  public void start(Stage stage) throws Exception {
    // Load the FXML file which contains the layout of the GUI
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("primary.fxml")));
    // Set the title of the application window
    stage.setTitle("A* Pathfinder");
    // Create a scene to hold the GUI elements in, of size 800x500
    stage.setScene(new Scene(root, 800, 530));
    // Make the window non-resizable
    stage.setResizable(false);
    // Finally display the window

    stage.show();
  }
}

