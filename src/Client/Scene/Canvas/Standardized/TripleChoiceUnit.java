package Client.Scene.Canvas.Standardized;

import Client.Scene.Canvas.Util.CanvasDrawer;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class TripleChoiceUnit implements CanvasDrawer {
    @Override
    public void draw(FXGraphics2D graphics2D) {

    }

    @Override
    public void update(double time) {

    }

    @Override
    public String getClickableSurfaces(Point2D point2D) {
        /*for (Map.Entry<RoundRectangle2D, String> entry: buttonMap.entrySet()) {
            if (entry.getKey().contains(point2D))return entry.getValue();
        }*/
        return null;
    }
}
