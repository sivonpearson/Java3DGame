import java.awt.Color;
import java.util.Random;

public class Filters {
    public int frameWidth;
    public int frameHeight;
    public Random rand = new Random();

    public Filters(int frameWidth, int frameHeight) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
    }

    final int spacesBetweenLines = 7;
    final int widthOfBlackLine = 1;
    final int RGB_BLACK = new Color(0, 0, 0).getRGB();
    public int[] applyTVFilter(int[] pixels) { //black lines
        for(int pos = 0; pos < frameHeight; pos += spacesBetweenLines) {
            int index = 0;
            while(index < widthOfBlackLine) {
                for(int i = 0; i < frameWidth; i++) {
                    pixels[(pos * frameWidth) + i] = RGB_BLACK;
                }
                index++;
                if(index != 0 || index != widthOfBlackLine) {
                    pos++;
                }
            }
        }

        // for(int pos = 0; pos < pixels.length; pos++) {
        //     Color tempColor = new Color(pixels[pos]);
        //     int colorRandomizerInt = getRandomInt(-5, 10);
        //     tempColor = new Color(truncateValue(tempColor.getRed()+colorRandomizerInt), truncateValue(tempColor.getGreen()+colorRandomizerInt), truncateValue(tempColor.getBlue()+colorRandomizerInt));
        //     pixels[pos] = tempColor.getRGB();
        // }
        
        return pixels;
    } // end of applyTVFilter

    private int truncateValue(int value) {
        if(value < 0)
            return 0;
        else if(value > 255)
            return 255;
        else
            return value;
    }

    private int getRandomInt(int min, int max) {
        return (rand.nextInt(max)+min);
    }

}
