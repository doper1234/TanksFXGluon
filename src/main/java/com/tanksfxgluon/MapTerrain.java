/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanksfxgluon;

import javafx.scene.image.Image;

/**
 *
 * @author Chris
 */
public class MapTerrain extends Entity{

    public MapTerrain(String SVGdata, double xLoc, double yLoc, Image... spriteCels) {
        super(SVGdata, xLoc, yLoc, spriteCels);
        
    }
    
}
