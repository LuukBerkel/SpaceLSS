package Client.Scene.Canvas.Util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Rescaler {
    public static BufferedImage rescaler(BufferedImage bufferedImage, double wScale, double hScale){
        System.out.println(wScale + " " +  hScale);
        BufferedImage result = null;
        try {
            int w = bufferedImage.getWidth();
            int h = bufferedImage.getHeight();
            BufferedImage scaledImage = new BufferedImage((int)(w * wScale), (int)(h * hScale), BufferedImage.TYPE_INT_ARGB);
            AffineTransform at = AffineTransform.getScaleInstance(wScale, hScale);
            AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            scaledImage = ato.filter(bufferedImage, scaledImage);
            result = scaledImage;
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
