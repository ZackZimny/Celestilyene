package com.mygdx.game.Entities;

import com.mygdx.game.GameHelpers.AssetManagerHandler;

/**
 * Unpassable entity that enemies can shoot through and the player cannot
 */
public class Tree extends Entity {
    /**
     * Creates an unpassable Tree entity
     * @param x x position of the left side
     * @param y y position of the bottom side
     * @param assetManagerHandler used to load in the Tree.png texture
     */
    public Tree(int x, int y, AssetManagerHandler assetManagerHandler){
        super(x, y, 42, 42, -11, -11);
        setTexture(assetManagerHandler.getTexture("Tree.png"));
    }
}
