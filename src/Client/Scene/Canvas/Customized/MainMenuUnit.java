package Client.Scene.Canvas.Customized;

import Client.Scene.Canvas.Util.CanvasDrawer;
import Client.Scene.Canvas.Util.Rescaler;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainMenuUnit implements CanvasDrawer {

    private double[] screenSize;
    private BufferedImage usFlag;
    private BufferedImage ussrFlag;
    private RoundRectangle2D usButton;
    private RoundRectangle2D ussrButton;
    private RoundRectangle2D quitButton;

    public MainMenuUnit(double[] ScreenSize) {
        this.screenSize = ScreenSize;

        //Parsing assets scene
        try {
            this.usFlag = Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource("/images/us.png"))), ScreenSize[0]/2.5, ScreenSize[1]/2.5);
            this.ussrFlag = Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource("/images/ussr.png"))), ScreenSize[0]/2.5, ScreenSize[1]/2.5);
        }catch (Exception e){
            e.printStackTrace();
        }

        //Buttons
        usButton = new RoundRectangle2D.Double(200 * screenSize[0], 125 * screenSize[1], 480 * screenSize[0], 912 * screenSize[1], 100 *screenSize[0], 100 * screenSize[1]);
        ussrButton = new RoundRectangle2D.Double(770 * screenSize[0], 125 * screenSize[1], 480 * screenSize[0], 912 * screenSize[1], 100 *screenSize[0], 100 * screenSize[1]);
        quitButton = new RoundRectangle2D.Double(1370 * screenSize[0], 125 * screenSize[1], 480 * screenSize[0], 912 * screenSize[1], 100 *screenSize[0], 100 * screenSize[1]);
    }

    @Override
    public void draw(FXGraphics2D graphics2D) {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.025f));

        graphics2D.setColor(Color.white);
        //graphics2D.draw(usButton);
        graphics2D.setPaint(new TexturePaint(usFlag, new Rectangle2D.Double(usButton.getX(),usButton.getY(),usButton.getWidth(),usButton.getHeight())));
        graphics2D.fill(usButton);

        graphics2D.setColor(Color.white);
        //graphics2D.draw(ussrButton);
        graphics2D.setPaint(new TexturePaint(ussrFlag, new Rectangle2D.Double(ussrButton.getX(),ussrButton.getY(),ussrButton.getWidth(),ussrButton.getHeight())));
        graphics2D.fill(ussrButton);


    }

    @Override
    public void update(double time) {

    }

    @Override
    public List<Shape> getClickableSurfaces() {
        return null;
    }
}
