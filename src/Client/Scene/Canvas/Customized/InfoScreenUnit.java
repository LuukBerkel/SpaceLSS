package Client.Scene.Canvas.Customized;

import Client.Logic.GameController;
import Client.Scene.Canvas.Util.CanvasDrawer;
import Client.Scene.Canvas.Util.Rescaler;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class InfoScreenUnit implements CanvasDrawer {
    private BufferedImage backgroundImage;
    private BufferedImage errorImage;
    private String textWaiterScreen;
    private double timePassed;
    private CallBack callBack;
    private String explan;

    public void setCallBack(CallBack callBack){
        this.callBack =callBack;
    }

    public InfoScreenUnit(String text, String explan,  String image) {
        //Parsing assets scene
        try {
            this.backgroundImage = Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource(image))), GameController.targetSize[0], GameController.targetSize[1]);

        }catch (Exception e){
            e.printStackTrace();
        }
        this.textWaiterScreen = text;
        this.callBack = callBack;
        this.explan = explan;
    }

    @Override
    public void draw(FXGraphics2D graphics2D) {
        graphics2D.drawImage(backgroundImage, (int) (GameController.targetSize[0] * 0 ),  (int) (GameController.targetSize[1] * 0 ), null);

        graphics2D.setColor(Color.white);
        graphics2D.fill(new Rectangle2D.Double(230 * GameController.targetSize[0], 150 * GameController.targetSize[1],  1460 *  GameController.targetSize[0], 10 *  GameController.targetSize[1]));
        graphics2D.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, (int)(60 * GameController.targetSize[0])));
        graphics2D.drawString(textWaiterScreen, (int) (230 * GameController.targetSize[0]), (int)(120 * GameController.targetSize[1]));
        graphics2D.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, (int)(30 * GameController.targetSize[0])));
        graphics2D.drawString(explan, (int) (230 * GameController.targetSize[0]), (int)(190 * GameController.targetSize[1]));
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
