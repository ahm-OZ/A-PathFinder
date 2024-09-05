package com.example.astargui;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class AStar {

    public static final int Diagonal_Cost = 14;
    public static final int H_V_Cost = 10;
    public  final Node[][] grid;
    private PriorityQueue<Node> openNodes;
    private boolean[][] closedNodes;

    private  Node startNode;
    private  Node endNode;
    private  List<Node>  path;


    public AStar( int width, int height) {
        grid = new Node[width][height];
        openNodes = new PriorityQueue<>();
        closedNodes = new boolean[width][height];
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = new Node( i, j);
            }
        }
    }


    public void setWall (int x, int y, boolean wall){
        grid[x][y].setWall(wall);
    }
    public boolean isWall (int x, int y){
        return grid[x][y].getWall();
    }

    public void setInitialCost() {
        if (endNode == null || startNode == null)return;

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j].heuristicCost = Math.abs( i - endNode.x) +
                    Math.abs(j - endNode.y);
                grid[i][j].solution = false;
                grid[i][j].finalCost = 0;
                grid[i][j].parent = null;
            }
        }
        startNode.finalCost = 0;
        path = new ArrayList<>();
    }

    public  boolean  isPointValid(int x, int y){
        return x < grid.length && x >= 0 && y < grid[0].length && y >= 0;
    }





    public void setStartNode(int x , int y) {
        if (isPointValid(x,y)) startNode = grid[x][y];
    }

    public  boolean isStart(int x, int y){
        return startNode != null && startNode.equals(grid[x][y]);

    }


    public  void setEndNode(int x, int y) {
        if (isPointValid(x, y))
            endNode = grid[x][y];
    }
    public  boolean isEnd (int x , int y){
        return endNode != null && endNode.equals(grid[x][y]);
    }


    public void updateCost(Node current, Node i, int cost){
        if( i.getWall() || closedNodes[i.x][i.y]) return;
        int iFinalCost = i.finalCost + cost;
        boolean isOpen = openNodes.contains(i);
        if (!isOpen || iFinalCost < i.finalCost){
            i.finalCost = iFinalCost;
            i.parent = current;
            if (!isOpen) openNodes.add(i);
        }

    }

    public void pathfinder(){
        if (startNode == null || endNode == null) throw new IllegalArgumentException(
            "Start Node or End Node were not set.");
        Node current;
        this.path = new ArrayList<>();
        setInitialCost();
        openNodes = new PriorityQueue<>();
        closedNodes = new boolean[grid.length][grid[0].length];
        openNodes.add(grid[startNode.x][startNode.y]);
        while (true){
            Node i;
            current = openNodes.poll();
            if (current == null) break;
            closedNodes[current.x][current.y] = true;
            if (current.equals(grid[endNode.x][endNode.y])) {
                setPath();
                return;
            }

            if (current.x - 1 >= 0){
                i = grid[current.x - 1][current.y];
                updateCost(current, i, current.finalCost + H_V_Cost);
                if (current.y + 1 < grid[0].length) {
                    i = grid[current.x - 1][current.y + 1];
                    updateCost(current, i, current.finalCost + Diagonal_Cost);
                }if (current.y -1 >= 0){
                    i = grid[current.x -1][current.y -1];
                    updateCost(current,i, current.finalCost + Diagonal_Cost);}
            }if (current.x + 1 < grid.length){
                i = grid[current.x + 1][current.y];
                updateCost(current, i, current.finalCost + H_V_Cost);
                if (current.y + 1 < grid[0].length) {
                    i = grid[current.x + 1][current.y + 1];
                    updateCost(current, i, current.finalCost + Diagonal_Cost);
                }if (current.y -1 >= 0){
                    i = grid[current.x +1][current.y -1];
                    updateCost(current,i, current.finalCost + Diagonal_Cost);}

            }if (current.y -1 >= 0){
                i = grid[current.x][current.y -1];
                updateCost(current, i, current.finalCost + H_V_Cost);

            }if (current.y +1 < grid[0].length){
                i = grid[current.x][current.y +1];
                updateCost(current, i, current.finalCost + H_V_Cost);

            }


        }
        path = null;

    }

    public boolean isPathSet(){
        return path != null;
    }

    private  void setPath (){
        if (!isPathSet()) return;
        Node pointer = endNode;
        while (pointer.parent != null){
            path.add(0, pointer);
            pointer = pointer.parent;
        }
    }

    public boolean isOnPath(int x, int y){
        return isPathSet() && path.contains(grid[x][y]);
    }

    public AStar(int width, int height, int startX, int startY, int endX, int endY, int[][] blocks){
        grid = new Node[width][height];
        openNodes = new PriorityQueue<>();
        closedNodes = new boolean[width][height];

        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[i].length; j++) grid[i][j] = new Node( i, j);

        for (int[] block : blocks) setWall(block[0], block[1], true);

        setStartNode(startX, startY);
        setEndNode(endX,endY);
        setInitialCost();


    }

    public void display(){
        System.out.println("Grid: ");

        for (int x = 0 ; x < grid.length; x++ ){
            for (int y = 0; y< grid[x].length; y++){
                if (x == startNode.x && y == startNode.y){
                    System.out.print("SO ");
                }else if (x == endNode.x && y == endNode.y){
                    System.out.print("DE ");
                }else if(grid[x][y] != null){
                    System.out.printf("%-3d", 0);
                }else System.out.print("BL ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displayScores(){
        System.out.println("/nScores for cells: ");
        for (int i = 0; i < grid.length; i++){
            for (int j = 0 ; j< grid[i].length; j++){
                if (!isWall(i,j)) System.out.printf("%-3d", grid[i][j].finalCost);
                else System.out.print("BL ");
            }
            System.out.println();
        }
        System.out.println();
    }


    public void displaySolution(){
        if(closedNodes[endNode.x][endNode.y]){
            System.out.println("path: ");
            Node current = endNode;
            System.out.print(current);
            grid[current.x][current.y].solution = true;

            while (current.parent != null){
                System.out.print("->" + current.parent);
                grid[current.parent.x][current.parent.y].solution = true;
                current = (current.parent);
            }
            System.out.println("/n");

            for (int x = 0 ; x < grid.length; x++ ){
                for (int y = 0; y< grid[x].length; y++){
                    if (x == startNode.x && y == startNode.y){
                        System.out.print("SO ");
                    }else if (x == endNode.x && y == endNode.y){
                        System.out.print("EN ");
                    }else if(!isWall(x,y)){
                        System.out.printf("%-3s", grid[x][y].solution ? "*" : "0");
                    }else System.out.print("BL ");
                }
                System.out.println();
            }


        }
    }

    public boolean isReady(){
        return startNode != null && endNode != null;
    }

    public static void main(String[] args) {
        int[][] x = {{5,0}, {4,5}};
        Scanner disp = new Scanner(System.in);

        AStar y = new AStar(10, 10, 0, 0, 9,9,x);
       y.pathfinder();
        y.display();
        y.displaySolution();

    }



}
