package Client.Scene.Canvas.Standardized;

import Client.Logic.GameController;
import Client.Scene.Canvas.Util.CanvasDrawer;
import Client.Scene.Canvas.Util.Rescaler;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class WaiterUnit implements CanvasDrawer {

    private BufferedImage backgroundImage;
    private BufferedImage loadingLogo;
    private String textWaiterScreen;
    private String funFact;
    private double angle;

    public WaiterUnit(String imagePath, String text, String funfact) {
        //Parsing assets scene
        try {
            this.backgroundImage = Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource(imagePath))), GameController.targetSize[0], GameController.targetSize[1]);
            this.loadingLogo = Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource("/images/LoadingScreen/loading.png"))), GameController.targetSize[0]/2, GameController.targetSize[1]/2);

        }catch (Exception e){
            e.printStackTrace();
        }
        this.textWaiterScreen = text;
        this.funFact= funfact;
    }

    @Override
    public void draw(FXGraphics2D graphics2D) {
        graphics2D.drawImage(backgroundImage, (int) (GameController.targetSize[0] * 0 ),  (int) (GameController.targetSize[1] * 0 ), null);
        graphics2D.setColor(Color.white);
        graphics2D.fill(new Rectangle2D.Double(230 * GameController.targetSize[0], 800 * GameController.targetSize[1],  1460 *  GameController.targetSize[0], 10 *  GameController.targetSize[1]));
        graphics2D.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, (int)(60 * GameController.targetSize[0])));
        graphics2D.drawString(textWaiterScreen, (int) (230 * GameController.targetSize[0]), (int)(880 * GameController.targetSize[1]));
        graphics2D.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, (int)(30 * GameController.targetSize[0])));
        graphics2D.drawString(funFact, (int) (230 * GameController.targetSize[0]), (int)(940 * GameController.targetSize[1]));

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate((int) (960 * GameController.targetSize[0]), (int)(440 * GameController.targetSize[1] ));
        affineTransform.rotate(angle);
        affineTransform.translate((int) (-200 * GameController.targetSize[0]/2), (int)(-500 * GameController.targetSize[1]/2 ));
        graphics2D.drawImage(loadingLogo, affineTransform, null);
    }

    @Override
    public void update(double time) {
        angle += 0.5 * time;
        if (angle >= 180){
            angle = 0;
        }
    }

    @Override
    public String getClickableSurfaces(Point2D point2D) {
        return null;
    }
}
