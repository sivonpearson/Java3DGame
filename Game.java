import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.*;

public class Game extends JFrame implements Runnable{
    
    private static final long serialVersionUID = 1L;
    public int windowWidth = 1300; //640 , 1100 , 1300
    public int windowHeight = 975; //480 , 825 , 975
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels; // TODO change to int[][]
    public ArrayList<Texture> textures;
    public ArrayList<CrosshairTexture> crosshairTextures;
    public Camera camera;
    public Screen screen;
    public Map map;

    public Game() {
        thread = new Thread(this);
        image = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        textures = new ArrayList<Texture>();
        textures.add(new Texture("res/tile.png", 64, 64));
        textures.add(new Texture("res/walltile.png", 64, 64));
        map = new Map("defaultMap");
        
        crosshairTextures = new ArrayList<CrosshairTexture>();
        crosshairTextures.add(new CrosshairTexture("res/crosshair1.png", 1));
        crosshairTextures.add(new CrosshairTexture("res/crosshair2.png", 1));
        
        camera = new Camera(map.getPlayerStartXPos(), map.getPlayerStartYPos(), map.getPlayerStartZPos(), 1, 0, 0, -.66); //7.5, 7.5, 0, 1, 0, 0, -.66
        screen = new Screen(map.getLayout(), map.getWidth(), map.getLength(), textures, windowWidth, windowHeight, crosshairTextures);
        addKeyListener(camera);
        setSize(windowWidth, windowHeight); //640x480
        setResizable(false);
        setTitle("3D Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.black);
        setLocationRelativeTo(null);
        setVisible(true);
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        setCursor(blankCursor);
        start();
    }
    private synchronized void start() {
        running = true;
        thread.start();
    }
    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        bs.show();
    }
    public void run() {
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0;//60 times per second
        double delta = 0;
        requestFocus();
        while(running) {
            long now = System.nanoTime();
            delta = delta + ((now-lastTime) / ns);
            lastTime = now;
            while (delta >= 1)//Make sure update is only happening 60 times a second
            {
                //handles all of the logic restricted time
                screen.update(camera, pixels);
                camera.update(map.getLayout());
                delta--;
            }
            render();//displays to the screen unrestricted time
        }
    }
    public static void main(String[] args) {
        Game game = new Game();
    }
}