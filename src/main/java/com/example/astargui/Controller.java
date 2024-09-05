package com.example.astargui;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable {

  @FXML
  private Canvas canvas;
  public static final int PIXEL_SIZE = 10;
  private GraphicsContext graphicsContext;
  private static int width;
  private static int height;
  private AStar aStar;
  private String currButton;
  private boolean[][]  walls;
  private int sX, sY, eX, eY;









  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
        width = (int) canvas.getWidth()/PIXEL_SIZE;
        height = (int) canvas.getHeight()/PIXEL_SIZE;
        graphicsContext = canvas.getGraphicsContext2D();
        canvas.requestFocus();
        aStar = new AStar(width, height);



  }
  private void  render (){
    for (int i = 0 ; i < width; i++)
      for (int j = 0; j < height; j++){
        graphicsContext.clearRect(i * PIXEL_SIZE, j * PIXEL_SIZE,
            PIXEL_SIZE, PIXEL_SIZE);
        if (aStar.isWall(i,j)) graphicsContext.setFill(Color.DARKGRAY.darker().darker().darker());
        else if (aStar.isStart(i, j)) graphicsContext.setFill(Color.GREEN);
        else if (aStar.isEnd(i, j)) graphicsContext.setFill(Color.RED);
        else if (aStar.isOnPath(i, j)) graphicsContext.setFill(Color.MEDIUMBLUE);
        else continue;
        graphicsContext.fillRect(i * PIXEL_SIZE, j * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
      }
    graphicsContext.setFill(Color.BLACK);
    for (int i = 0; i < canvas.getWidth(); i += PIXEL_SIZE)
      graphicsContext.strokeLine(i, 0, i, canvas.getHeight());
    for (int i = 0; i < canvas.getHeight(); i += PIXEL_SIZE)
      graphicsContext.strokeLine(0, i, canvas.getWidth(), i);

  }

  public void setStart(){
    currButton = "setStart";
    canvas.setOnMousePressed(mouseEvent -> {
     if(currButton.equals("setStart")) {
        int x = (int) mouseEvent.getX() / PIXEL_SIZE;
        int y = (int) mouseEvent.getY() / PIXEL_SIZE;

        if (aStar.isPointValid(x, y) && !aStar.isWall(x,y)) {
          aStar.setStartNode(x, y);
         if (aStar.isReady()) aStar.pathfinder();
       }
        render();
      }

    });
  }

  public void setEnd(){
    currButton = "setEnd";
    canvas.setOnMousePressed(mouseEvent -> {
      if(currButton.equals("setEnd")) {
        int x = (int) mouseEvent.getX() / PIXEL_SIZE;
        int y = (int) mouseEvent.getY() / PIXEL_SIZE;
        if (aStar.isPointValid(x, y) && !aStar.isWall(x,y)) {
          aStar.setEndNode(x,y);
          if (aStar.isReady()) aStar.pathfinder();
        }
        render();
     }

    });
  }

  public void drawWalls(){
    currButton = "drawWalls";
    canvas.setOnMouseDragged(mouseEvent -> {
      if (currButton.equals("drawWalls")){
        int x = (int) mouseEvent.getX()/PIXEL_SIZE;
        int y = (int) mouseEvent.getY()/PIXEL_SIZE;
        if (aStar.isPointValid(x,y)){
          aStar.setWall(x,y,true);
          if (aStar.isReady()) aStar.pathfinder();
        }
        render();
      }
    });
  }

  public void deleteWalls(){
    currButton = "deleteWalls";
    canvas.setOnMouseDragged(mouseEvent -> {
      if (currButton.equals("deleteWalls")){
        int x = (int) mouseEvent.getX()/PIXEL_SIZE;
        int y = (int) mouseEvent.getY()/PIXEL_SIZE;
        if (aStar.isPointValid(x,y)){
          aStar.setWall(x,y,false);
          if (aStar.isReady()) aStar.pathfinder();
        }
        render();
    }
    });
  }

  public void runAlgorithm() {
    if (aStar.isReady())
     aStar.pathfinder();
    render();
  }

  public void clearGrid() {
    aStar = new AStar(width,height);
    render();
  }




}

