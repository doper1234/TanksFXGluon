/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanksfxgluon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chris
 */
public class Map {
    
    private int map;
    private int[][] mapGrid;
    private int mapLength = 26;
    
    public Map(int mapNumber){
        loadMap(mapNumber);
    }
    
    private void loadMap(int mapNumber){
        System.out.println("print");
        try {
            Scanner s = new Scanner(new File("src/maps/map" + mapNumber + ".txt"));
            mapGrid = new int[mapLength][mapLength];
            for (int i = 0; i < mapGrid.length; i++) {
                for (int j = 0; j < mapGrid.length; j++) {
                    mapGrid[i][j] = s.nextInt();
                System.out.print(mapGrid[i][j]);
                }
            System.out.println();
            }
            
        } catch (Exception ex) {
            System.out.println("didn't work"); 
        }
    }
    
    public int[][] getMap(){
        return mapGrid;
    }
    
    
}
