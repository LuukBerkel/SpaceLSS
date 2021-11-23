package Client.Scene.Canvas.Customized;

import Client.Logic.GameController;
import Client.Scene.Canvas.Util.CanvasDrawer;
import Client.Scene.Canvas.Util.Rescaler;
import Client.Scene.JavaFX.Util.AbstractView;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class SplashScreenUnit implements CanvasDrawer {

    //region Constructor and Attributes
    //Settings
    private final double[] screenSizes;
    private final int blankTime= 1;
    private final int fadeTime = 3;
    private SplashBack splashBack;


    //Resources
    private BufferedImage logo;

    //Local variables
    private float iterationFade = 0f;
    private double timePast = 0;

    public SplashScreenUnit(SplashBack splashBack) {
        //Parsing assets scene
        try {
            this.logo = Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource("/images/logo.jpg"))),GameController.targetSize[0], GameController.targetSize[1]);
        }catch (Exception e){
            e.printStackTrace();
        }

        //Setting up other vars
        this.screenSizes = GameController.targetSize;
        this.splashBack = splashBack;
    }
    //endregion

    //region Methods
    /***
     * draws the animation.
     * @param graphics
     */
    @Override
    public void draw(FXGraphics2D graphics) {
        //Drawing logo
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(0 * screenSizes[0], 0 * screenSizes[1]);
        graphics.drawImage(this.logo, affineTransform, null);

        //Drawing background
        Color color = new Color(0, 0, 0, 1f - iterationFade);
        graphics.setColor(color);
        graphics.fill(new Rectangle(0, 0 , (int)(1920 * screenSizes[0]), (int)(1080 * screenSizes[1])));

    }

    /***
     * This method is for updating the calculations...
     * @param time
     */
    @Override
    public void update(double time) {
        //Setting up calculations
        double iterationAdder =
                (1.0 / fadeTime) * time;
        this.timePast += time;

        //Logic setup
        if (timePast < blankTime);
            //When the screen needs to be blank show nothing : For first second
        else if (timePast < blankTime + fadeTime){
            //When the screen needs to show the logo start fading : For Second to Fourth second.
            if (iterationFade + iterationAdder < 1f) iterationFade += (float) iterationAdder;
        } else  if (timePast < blankTime  + 2 *fadeTime ){
            //When the screen needs to show the logo start fading : For Second to Fourth second.
            if (iterationFade - iterationAdder > 0f) iterationFade -= (float) iterationAdder;
        } else if (timePast < 2 * blankTime + 2 *fadeTime);
            //When the screen needs to be blank show nothing : For last second
        else if (timePast > 2 * blankTime + 2 *fadeTime){
            //Switching to the next scene;
            this.splashBack.animationEnded();
        }
    }


    /**
     * not needed here but is for getting clickable surfaces for logic
     * @return
     */
    @Override
    public String getClickableSurfaces(Point2D point2D) {
        return null;
    }
    //endregion
}

