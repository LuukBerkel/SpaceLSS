package Client.Scene.Canvas.Standardized;

import Client.Logic.GameController;
import Client.Scene.Canvas.Util.CanvasDrawer;
import Client.Scene.Canvas.Util.Rescaler;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QuadChoiceUnit implements CanvasDrawer {
    //Variables
    private BufferedImage aFlag;
    private BufferedImage bFlag;
    private BufferedImage cFlag;
    private BufferedImage dFlag;
    private BufferedImage background;

    //Final
    private final double[] screenSize;
    private final RoundRectangle2D aButton;
    private final RoundRectangle2D bButton;
    private final RoundRectangle2D cButton;
    private final RoundRectangle2D dButton;
    private final Rectangle2D lineTitle;
    private final String question;

    private final HashMap<RoundRectangle2D, String> buttonMap= new
            HashMap<>();


    public QuadChoiceUnit(String optionAContext, String optionAImage,String optionBContext, String optionBImage, String optionCContext, String optionCImage,  String optionDContext, String optionDImage,String background, String question) {
        this.screenSize = GameController.targetSize;
        this.question =question;

        //Parsing assets scene
        try {
            this.aFlag = Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource(optionAImage))), screenSize[0]/2.5, screenSize[1]/2.5);
            this.bFlag = Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource(optionBImage))), screenSize[0]/2.5, screenSize[1]/2.5);
            this.cFlag = Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource(optionCImage))), screenSize[0]/2.5, screenSize[1]/2.5);
            this.dFlag = Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource(optionDImage))), screenSize[0]/2.5, screenSize[1]/2.5);
            this.background= Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource(background))), screenSize[0], screenSize[1]);
        }catch (Exception e){
            e.printStackTrace();
        }

        //Buttons
        aButton = new RoundRectangle2D.Double(64 * screenSize[0], 135 * screenSize[1], 400 * screenSize[0], 912 * screenSize[1], 100 *screenSize[0], 100 * screenSize[1]);
        bButton = new RoundRectangle2D.Double(528 * screenSize[0], 135 * screenSize[1], 400 * screenSize[0], 912 * screenSize[1], 100 *screenSize[0], 100 * screenSize[1]);
        cButton = new RoundRectangle2D.Double(992 * screenSize[0], 135 * screenSize[1], 400 * screenSize[0], 912 * screenSize[1], 100 *screenSize[0], 100 * screenSize[1]);
        dButton = new RoundRectangle2D.Double(1456 * screenSize[0], 135 * screenSize[1], 400 * screenSize[0], 912 * screenSize[1], 100 *screenSize[0], 100 * screenSize[1]);
        lineTitle = new Rectangle2D.Double(64 * screenSize[0], 105 * screenSize[1], 1790 * screenSize[0], 10 * screenSize[1]);

        //Actions
        buttonMap.put(aButton, optionAContext);
        buttonMap.put(bButton, optionBContext);
        buttonMap.put(cButton, optionCContext);
        buttonMap.put(dButton, optionDContext);
    }

    @Override
    public void draw(FXGraphics2D graphics2D) {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1f));

        graphics2D.drawImage(background, 0,0 , null);
        graphics2D.setColor(Color.white);
        graphics2D.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, (int)(60 * screenSize[0])));
        graphics2D.drawString(question, (int) (64 * screenSize[0]), (int)(80 * screenSize[1]));
        graphics2D.setColor(Color.white);
        graphics2D.fill(lineTitle);

        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 0.75f));
        graphics2D.setPaint(new TexturePaint(aFlag, new Rectangle2D.Double(aButton.getX() -  (0 *screenSize[0] ), aButton.getY(), aButton.getWidth(), aButton.getHeight())));
        graphics2D.fill(aButton);

        graphics2D.setPaint(new TexturePaint(bFlag, new Rectangle2D.Double(bButton.getX() - (0 *screenSize[0] ), bButton.getY(), bButton.getWidth(), bButton.getHeight())));
        graphics2D.fill(bButton);

        graphics2D.setPaint(new TexturePaint(cFlag, new Rectangle2D.Double(cButton.getX() - (0 *screenSize[0] ),  cButton.getY(), cButton.getWidth(), cButton.getHeight())));
        graphics2D.fill(cButton);

        graphics2D.setPaint(new TexturePaint(dFlag, new Rectangle2D.Double(dButton.getX() - (0 *screenSize[0] ), dButton.getY(), dButton.getWidth(), dButton.getHeight())));
        graphics2D.fill(dButton);
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
