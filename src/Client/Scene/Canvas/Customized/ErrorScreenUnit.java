package Client.Scene.Canvas.Customized;

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

public class ErrorScreenUnit implements CanvasDrawer {

    private BufferedImage backgroundImage;
    private BufferedImage errorImage;
    private String textWaiterScreen;
    private double timePassed;
    private CallBack callBack;

    public ErrorScreenUnit(String text, CallBack callBack) {
        //Parsing assets scene
        try {
            this.backgroundImage = Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource("/images/explosion.jpg"))), GameController.targetSize[0], GameController.targetSize[1]);
            this.errorImage = Rescaler.rescaler((ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource("/images/error.png")))), GameController.targetSize[0]/1.5, GameController.targetSize[1]/1.5);

        }catch (Exception e){
            e.printStackTrace();
        }
        this.textWaiterScreen = text;
        this.callBack = callBack;
    }

    @Override
    public void draw(FXGraphics2D graphics2D) {
        graphics2D.drawImage(backgroundImage, (int) (GameController.targetSize[0] * 0 ),  (int) (GameController.targetSize[1] * 0 ), null);
        graphics2D.setColor(Color.white);
        graphics2D.fill(new Rectangle2D.Double(230 * GameController.targetSize[0], 150 * GameController.targetSize[1],  1460 *  GameController.targetSize[0], 10 *  GameController.targetSize[1]));
        graphics2D.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, (int)(60 * GameController.targetSize[0])));
        graphics2D.drawString(textWaiterScreen, (int) (230 * GameController.targetSize[0]), (int)(120 * GameController.targetSize[1]));
        graphics2D.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, (int)(30 * GameController.targetSize[0])));
        graphics2D.drawImage(errorImage, (int) (GameController.targetSize[0] * 601 ),  (int) (GameController.targetSize[1] * 200), null);
    }

    @Override
    public void update(double time) {
        timePassed += time;
        if (timePassed > 7){
            callBack.animationEnded();
        }
    }

    @Override
    public String getClickableSurfaces(Point2D point2D) {
        return null;
    }
}
