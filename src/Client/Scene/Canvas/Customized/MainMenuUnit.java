package Client.Scene.Canvas.Customized;

import Client.Logic.GameController;
import Client.Scene.Canvas.Util.CanvasDrawer;
import Client.Scene.Canvas.Util.Rescaler;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainMenuUnit implements CanvasDrawer {

    private double[] screenSize;
    private BufferedImage usFlag;
    private BufferedImage ussrFlag;
    private BufferedImage quitFlag;
    private RoundRectangle2D usButton;
    private RoundRectangle2D ussrButton;
    private RoundRectangle2D quitButton;
    private Rectangle2D backgroundTitle;

    private HashMap<RoundRectangle2D, String> buttonMap= new
    HashMap<>();



    public MainMenuUnit() {
        this.screenSize = GameController.targetSize;

        //Parsing assets scene
        try {
            this.usFlag = Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource("/images/us.png"))), screenSize[0]/2.5, screenSize[1]/2.5);
            this.ussrFlag = Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource("/images/ussr.png"))), screenSize[0]/2.5, screenSize[1]/2.5);
            this.quitFlag = Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource("/images/exit.png"))), screenSize[0]/2.5, screenSize[1]/2.5);
        }catch (Exception e){
            e.printStackTrace();
        }

        //Buttons
        usButton = new RoundRectangle2D.Double(230 * screenSize[0], 135 * screenSize[1], 480 * screenSize[0], 912 * screenSize[1], 100 *screenSize[0], 100 * screenSize[1]);
        ussrButton = new RoundRectangle2D.Double(750 * screenSize[0], 135 * screenSize[1], 480 * screenSize[0], 912 * screenSize[1], 100 *screenSize[0], 100 * screenSize[1]);
        quitButton = new RoundRectangle2D.Double(1270 * screenSize[0], 135 * screenSize[1], 480 * screenSize[0], 912 * screenSize[1], 100 *screenSize[0], 100 * screenSize[1]);
        backgroundTitle = new Rectangle2D.Double(230 * screenSize[0], 105 * screenSize[1], 1520 * screenSize[0], 10 * screenSize[1]);

        //Actions
        buttonMap.put(usButton, "@Main: USA");
        buttonMap.put(ussrButton, "@Main: USSR");
        buttonMap.put(quitButton, "@Main: Quit");
    }

    @Override
    public void draw(FXGraphics2D graphics2D) {



        graphics2D.setColor(Color.white);
        graphics2D.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, (int)(60 * screenSize[0])));
        graphics2D.drawString("Kies een land voor je ruimteprogramma: ", (int) (470 * screenSize[0]), (int)(80 * screenSize[1]));
        graphics2D.fill(backgroundTitle);

        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.025f));
        //graphics2D.draw(usButton);
        graphics2D.setPaint(new TexturePaint(usFlag, new Rectangle2D.Double(usButton.getX(),usButton.getY(),usButton.getWidth(),usButton.getHeight())));
        graphics2D.fill(usButton);

        graphics2D.setColor(Color.white);
        //graphics2D.draw(ussrButton);
        graphics2D.setPaint(new TexturePaint(ussrFlag, new Rectangle2D.Double(ussrButton.getX(),ussrButton.getY(),ussrButton.getWidth(),ussrButton.getHeight())));
        graphics2D.fill(ussrButton);

        graphics2D.setColor(Color.white);
        //graphics2D.draw(ussrButton);
        graphics2D.setPaint(new TexturePaint(quitFlag, new Rectangle2D.Double(quitButton.getX(),quitButton.getY(),quitButton.getWidth(),quitButton.getHeight())));
        graphics2D.fill(quitButton);

    }

    @Override
    public void update(double time) {
    }

    @Override
    public String getClickableSurfaces(Point2D point2D) {
        for (Map.Entry<RoundRectangle2D, String> entry: buttonMap.entrySet()) {
            if (entry.getKey().contains(point2D))return entry.getValue();
        }
        return null;
    }
}
