/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanksfxgluon;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Chris
 */
public class GameTimer extends AnimationTimer{

        private boolean closingGreyScreen = true;
        private final int TOP_OF_THE_SCREEN = 500;
        private final int MIDDLE_OF_THE_SCREEN = 175;
        private int greyScreenY = TOP_OF_THE_SCREEN;
        private int waiting = 0;
        private int waitTime = 100;
        private int moveSpeed = 3;
        TanksFXGluon bc;
        public GameTimer(TanksFXGluon bfx){
            bc = bfx;
        }
        @Override
        public void handle(long now) {
//            animateCursor();
            if(bc.setStage){
                moveScreens();
            }
            else{
                bc.animateBullets();
                animateCursor();
            
            }
            
            
        }
        
        private void moveScreens(){
            if (greyScreenY >= MIDDLE_OF_THE_SCREEN && closingGreyScreen == true && waiting != waitTime) {
                greyScreenY = greyScreenY - moveSpeed;
                bc.stageScreenTop.setTranslateY(greyScreenY);
                bc.stageScreenBottom.setTranslateY(-greyScreenY);
            } else if(greyScreenY < MIDDLE_OF_THE_SCREEN){
                
                    bc.stageLabel.setVisible(true);
                    closingGreyScreen = false;
                    bc.setStage = true;
                    waiting++;
                    if(waiting == waitTime){
                        bc.tanksTheme.play();
                        bc.setNewStage();
                        bc.stageScreenTop.toFront();
                        bc.stageScreenBottom.toFront();
                        
                    }
                    
                    
                    
                
            }
            else {
                
            }
            if (closingGreyScreen == false && waiting == waitTime) {
                if (greyScreenY <= TOP_OF_THE_SCREEN) {
                    //if(setStage){
                    //setNewStage();
                    //}
                    //setStage = false;
                    bc.stageLabel.setVisible(false);
                    greyScreenY = greyScreenY + moveSpeed;
                    bc.stageScreenTop.setTranslateY(greyScreenY);
                    bc.stageScreenBottom.setTranslateY(-greyScreenY);
                    if(greyScreenY == TOP_OF_THE_SCREEN){
                       bc.loadTanksLivesAndOtherShitThatTheUserShouldSee(); 
                    }
                    
                }
            }
            
            
        }
        
        private void animateCursor(){
      //  if(cursorCount <= 6){
//                scene.setCursor(new ImageCursor(tankCursor));
//            }
//            else {
//                scene.setCursor(new ImageCursor(tankCursor2));
//                if(cursorCount < 12){
//                    cursorCount = 0;    
//                }
//                
//            }
//            cursorCount++;
    }
    
    
        
    }
