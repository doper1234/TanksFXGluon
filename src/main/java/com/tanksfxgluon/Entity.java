/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanksfxgluon;

import java.util.*;
import javafx.scene.image.*;
import javafx.scene.shape.SVGPath;





public class Entity {
    
    protected double xLocation, yLocation, ySpeed, xSpeed;
    protected SVGPath spriteBounds;
    protected ImageView spriteFrame;
    protected boolean isAlive, isBonus, hasValu;
    protected List<Image> imageState = new ArrayList<>();
    
    
    public Entity(String SVGdata, double xLoc, double yLoc, Image... spriteCels){
        
        spriteBounds = new SVGPath();
        spriteBounds.setContent(SVGdata);
        spriteFrame = new ImageView(spriteCels[0]);
        imageState.addAll(Arrays.asList(spriteCels));
        xLocation = yLocation = ySpeed = xSpeed = 0;
        isAlive = isBonus = false;
        spriteFrame.setTranslateX(xLoc);
        spriteFrame.setTranslateY(yLoc);
    }
}
