package Client.Scene.Canvas.Standardized;

import Client.Logic.GameController;
import Client.Scene.Canvas.Util.CanvasDrawer;
import Client.Scene.Canvas.Util.Rescaler;
import Shared.CommunicationLibrary;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.*;

public class DualChoiceUnit implements CanvasDrawer {

    //Variables
    private BufferedImage aFlag;
    private BufferedImage bFlag;
    private BufferedImage background;

    //Final
    private final double[] screenSize;
    private final RoundRectangle2D aButton;
    private final RoundRectangle2D bButton;
    private final Rectangle2D lineTitle;
    private final RoundRectangle2D scoresPlain;
    private final String question;

    private final HashMap<RoundRectangle2D, String> buttonMap= new
            HashMap<>();
    private final HashMap<String, Integer> scoreMap;

    public DualChoiceUnit(ArrayList<String> data , HashMap<String, Integer> scores) {
        //Setting variables
        this.screenSize = GameController.targetSize;
        this.scoreMap = scores;

        //Reading list...
        this.question = data.get(data.size()-1);

        //Parsing assets scene
        try {
            this.aFlag = Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource(data.get(1)))), screenSize[0]/2.5, screenSize[1]/2.5);
            this.bFlag = Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource(data.get(3)))), screenSize[0]/2.5, screenSize[1]/2.5);
            this.background= Rescaler.rescaler(ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource(data.get(4)))), screenSize[0], screenSize[1]);
        }catch (Exception e){
            e.printStackTrace();
        }

        //Buttons
        aButton = new RoundRectangle2D.Double(110 * screenSize[0], 135 * screenSize[1], 800 * screenSize[0], 912 * screenSize[1], 100 *screenSize[0], 100 * screenSize[1]);
        bButton = new RoundRectangle2D.Double(1010 * screenSize[0], 135 * screenSize[1], 800 * screenSize[0], 912 * screenSize[1], 100 *screenSize[0], 100 * screenSize[1]);
        lineTitle = new Rectangle2D.Double(110 * screenSize[0], 105 * screenSize[1], 1700 * screenSize[0], 10 * screenSize[1]);
        scoresPlain = new RoundRectangle2D.Double(1470 * screenSize[0], 10 * screenSize[1], 350 * screenSize[0], 80 * screenSize[1], 40 *screenSize[0], 40 * screenSize[1]);

        //Actions
        buttonMap.put(aButton, data.get(0));
        buttonMap.put(bButton, data.get(2));
    }

    @Override
    public void draw(FXGraphics2D graphics2D) {

        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1f));

        graphics2D.drawImage(background, 0,0 , null);
        graphics2D.setColor(Color.white);
        graphics2D.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, (int)(60 * screenSize[0])));
        graphics2D.drawString(question, (int) (100 * screenSize[0]), (int)(80 * screenSize[1]));
        graphics2D.setColor(Color.white);
        graphics2D.fill(lineTitle);

        graphics2D.setColor(new Color(0.5f, 0.5f,0.5f, 0.75f));
        graphics2D.fill(scoresPlain);

        graphics2D.setColor(Color.white);
        graphics2D.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, (int)(20 * screenSize[0])));
        graphics2D.drawString("USA: " + scoreMap.get(CommunicationLibrary.KEYS_SUCCESSES_USA) + " \uD83D\uDC4D , " +
                        scoreMap.get(CommunicationLibrary.KEYS_KILLED_USA) + " \uD83D\uDC80, $  " +  scoreMap.get(CommunicationLibrary.KEYS_WASTED_USA) + " \uD83D\uDCB8"

                , (int) (1496 * screenSize[0]), (int)(50 * screenSize[1]));


        graphics2D.drawString("USSR: " +
                        scoreMap.get(CommunicationLibrary.KEYS_SUCCESSES_USSR) + " \uD83D\uDC4D, " +
                        scoreMap.get(CommunicationLibrary.KEYS_KILLED_USSR) + " \uD83D\uDC80, $ " +  scoreMap.get(CommunicationLibrary.KEYS_WASTED_USSR) + " \uD83D\uDCB8"

                , (int) (1496 * screenSize[0]), (int)(80 * screenSize[1]));

        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 0.85f));

        graphics2D.setPaint(new TexturePaint(aFlag, new Rectangle2D.Double(aButton.getX(), aButton.getY(), aButton.getWidth(), aButton.getHeight())));
        graphics2D.fill(aButton);

        graphics2D.setColor(Color.white);
        graphics2D.setPaint(new TexturePaint(bFlag, new Rectangle2D.Double(bButton.getX(), bButton.getY(), bButton.getWidth(), bButton.getHeight())));
        graphics2D.fill(bButton);
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
