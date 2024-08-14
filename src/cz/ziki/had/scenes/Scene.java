package cz.ziki.had.scenes;

import java.awt.Graphics;

/**
 * Represents a scene in the game, such as the main menu, gameplay scene, or game over screen.
 * Any scene in the game must implement the update and draw methods.
 */
public interface Scene {

    void update(double dt);

    void draw(Graphics g);

}