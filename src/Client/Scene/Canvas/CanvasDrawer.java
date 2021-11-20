package Client.Scene.Canvas;

import org.jfree.fx.FXGraphics2D;

public interface CanvasDrawer {
    public void draw(FXGraphics2D graphics2D);
    public void update(double time);
}
