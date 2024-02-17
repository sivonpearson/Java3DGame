import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Texture {
    public int[] pixels;
    private String loc;
    private final int SIZE;
    private final int imgWidth;
    private final int imgHeight;
    
    public Texture(String location, int w, int h) {
        loc = location;
        SIZE = w;
        pixels = new int[w * h];
        imgWidth = w;
        imgHeight = h;
        load();
    }
    
    private void load() {
        try {
            BufferedImage image = ImageIO.read(new File(loc));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getSize() {
        return SIZE;
    }
    
}