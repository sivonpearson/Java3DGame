import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.awt.Color;

public class CrosshairTexture {
    private int[] pixels;
    private String loc;
    private int imgWidth;
    private int imgHeight;
    private BufferedImage image;
    private int magnification; // increase size of crosshair
    /*
        Color RGB Values:
        yellow - (255, 255, 0)
        
    */
    private int color = new Color(255, 255, 0).getRGB();
    
    public CrosshairTexture(String location, int magnification) {
        loc = location;
        this.magnification = magnification;
        load();
    }
    
    private void load() {
        try {
            image = ImageIO.read(new File(loc));
            imgWidth = image.getWidth();
            imgHeight = image.getHeight();
            pixels = new int[imgWidth * imgHeight];
            //pixels = image.getRGB(0, 0, w, h, pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int y = 0; y < imgHeight; y++) {
            for(int x = 0; x < imgWidth; x++) {
                pixels[x + (y * imgWidth)] = image.getRGB(x, y);
            }
        }
    }

    public int[] applyCrosshair(int[] pixels, int frameWidth) {
        for(int crosshairPosCount=0; crosshairPosCount<this.getPixels().length; crosshairPosCount++) {
            if(!this.getPixelColor(crosshairPosCount).equals(this.getPixelColor(0, 0))) {
                pixels[((pixels.length - this.getImgWidth())/2)+(crosshairPosCount % this.getImgWidth())+((int)(crosshairPosCount / this.getImgWidth())*frameWidth)] = color; //crosshairColor
            }
        }
        return pixels;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public int[] getPixels() {
        return pixels;
    }

    public Color getPixelColor(int x, int y) {
        return new Color(pixels[x + (y * imgWidth)]);
    }

    public Color getPixelColor(int pos) {
        return new Color(pixels[pos]);
    }
    
}