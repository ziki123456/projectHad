import java.awt.*;

public interface Food {
    public void spawn();
    public void update(double dt);
    public void draw(Graphics2D g2);
    public boolean isSpawned();
}
