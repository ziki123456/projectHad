import java.awt.Graphics;
/**
 * Represents a scene in the game, such as the main menu, gameplay scene, or game over screen.
 * Any scene in the game must implement the update and draw methods.
 */
public abstract class Scene {

    public abstract void update(double dt);
    public abstract void draw(Graphics g);

}