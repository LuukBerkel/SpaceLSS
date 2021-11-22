package Client.Scene.Canvas.Util;

import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public interface CanvasDrawer {
    public void draw(FXGraphics2D graphics2D);
    public void update(double time);
    public List<Shape> getClickableSurfaces();
}
