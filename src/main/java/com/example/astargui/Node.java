package com.example.astargui;

public class Node implements Comparable<Node>{
    public int x,y;
    public Node parent;
    public int heuristicCost;
    public int finalCost;
    public boolean solution;
    private boolean wall;


    public void setWall(boolean wall) {
        this.wall = wall;
    }
    public boolean getWall (){
        return wall;
    }

    public Node(int x, int y, boolean isWall){
        this.x = x;
        this.y = y;
        this.wall = isWall;
    }

    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "[" + x + "," + y + "]";
    }

    public int compareTo(Node b){
        if(finalCost < b.finalCost) return -1;
        if (finalCost > b.finalCost) return 1;
        else return 0;
    }






}
