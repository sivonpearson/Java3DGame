import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.awt.MouseInfo;
//import java.awt.Robot;
//import java.awt.Dimension;
//import java.awt.Toolkit;
import java.awt.*;
import java.awt.image.MemoryImageSource;

public class Camera implements KeyListener{
    public double xPos, yPos, zPos, xDir, yDir, xPlane, yPlane;
    public boolean lookLeft, lookRight, lookUp, lookDown, moveForward, moveBack, strafeRight, strafeLeft, jump;
    public final double STROLL_SPEED = .01;
    public final double WALK_SPEED = .025;
    public final double SPRINT_SPEED = .045;
    public double MOVE_SPEED = WALK_SPEED;
    public double ROTATION_SPEED = .025;
    public int mouseY = MouseInfo.getPointerInfo().getLocation().y;
    public int mouseX = MouseInfo.getPointerInfo().getLocation().x;
    public boolean mouseEnabled = true;
    private double mouseSensitivity= .005;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    public Camera(double x, double y, double z, double xd, double yd, double xp, double yp) {
        xPos = x;
        yPos = y;
        zPos = z;
        xDir = xd;
        yDir = yd;
        xPlane = xp;
        yPlane = yp;
    }
    public void keyPressed(KeyEvent key) {
        switch(key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                lookLeft = true;
                break;
            case KeyEvent.VK_RIGHT:
                lookRight = true;
                break;
            case KeyEvent.VK_UP:
                lookUp = true;
                break;
            case KeyEvent.VK_DOWN:
                lookDown = true;
                break;
            case KeyEvent.VK_W:
                moveForward = true;
                break;
            case KeyEvent.VK_S:
                moveBack = true;
                break;
            case KeyEvent.VK_A:
                strafeLeft = true;
                break;
            case KeyEvent.VK_D:
                strafeRight = true;
                break;
            case KeyEvent.VK_SPACE:
                jump = true;
                break;
            case KeyEvent.VK_CONTROL:
                MOVE_SPEED = STROLL_SPEED;
                break; 
            case KeyEvent.VK_SHIFT:
                MOVE_SPEED = SPRINT_SPEED;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(1);
                break;
        }
    }
    public void keyReleased(KeyEvent key) {
        switch(key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                lookLeft = false;
                break;
            case KeyEvent.VK_RIGHT:
                lookRight = false;
                break;
            case KeyEvent.VK_UP:
                lookUp = false;
                break;
            case KeyEvent.VK_DOWN:
                lookDown = false;
                break;
            case KeyEvent.VK_W:
                moveForward = false;
                break;
            case KeyEvent.VK_S:
                moveBack = false;
                break;
            case KeyEvent.VK_A:
                strafeLeft = false;
                break;
            case KeyEvent.VK_D:
                strafeRight = false;
                break;
            case KeyEvent.VK_SPACE:
                jump = false;
                break;
            case KeyEvent.VK_CONTROL:
                MOVE_SPEED = WALK_SPEED;
                break; 
            case KeyEvent.VK_SHIFT:
                MOVE_SPEED = WALK_SPEED;
                break;
            case KeyEvent.VK_PLUS:
                mouseSensitivity += .005;
                break;
            case KeyEvent.VK_MINUS:
                if(mouseSensitivity > .005) 
                    mouseSensitivity -= .005;
                break;
        }
    }
    public void update(int[][] map) {
        if(moveForward) {
            if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0) {
                xPos+=xDir*MOVE_SPEED;
            }
            if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] ==0)
                yPos+=yDir*MOVE_SPEED;
        }
        if(moveBack) {
            if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0)
                xPos-=xDir*MOVE_SPEED;
            if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==0)
                yPos-=yDir*MOVE_SPEED;
        }
        if(strafeLeft) {
            if(map[(int)(xPos - yDir * MOVE_SPEED)][(int)yPos] == 0) {
                xPos-=yDir*MOVE_SPEED;
            }
            if(map[(int)xPos][(int)(yPos + xDir * MOVE_SPEED)] ==0)
                yPos+=xDir*MOVE_SPEED;
        }
        if(strafeRight) {
            if(map[(int)(xPos + yDir * MOVE_SPEED)][(int)yPos] == 0)
                xPos+=yDir*MOVE_SPEED;
            if(map[(int)xPos][(int)(yPos - xDir * MOVE_SPEED)]==0)
                yPos-=xDir*MOVE_SPEED;
        }
        if(lookRight) {
            double oldxDir=xDir;
            xDir=xDir*Math.cos(-ROTATION_SPEED) - yDir*Math.sin(-ROTATION_SPEED);
            yDir=oldxDir*Math.sin(-ROTATION_SPEED) + yDir*Math.cos(-ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
            yPlane=oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);
        }
        if(lookLeft) {
            double oldxDir=xDir;
            xDir=xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
            yDir=oldxDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
            yPlane=oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);
        }
        if(mouseEnabled) {
            if(100 < Math.sqrt((mouseX-screenSize.width/2) * (mouseX-screenSize.width/2) + (mouseY-screenSize.height/2) * (mouseY-screenSize.height/2))) {
                try{
                    Robot robot=new Robot(); //mouse robot
                    robot.mouseMove(screenSize.width/2, screenSize.height/2); // center cursor on middle of screen
                } catch (AWTException e) { }
                mouseX = MouseInfo.getPointerInfo().getLocation().x;
                mouseY = MouseInfo.getPointerInfo().getLocation().y;
            }
            else{
                int oldMouseX = mouseX;
                mouseX = MouseInfo.getPointerInfo().getLocation().x;
                int oldMouseY=mouseY;
                mouseY=MouseInfo.getPointerInfo().getLocation().y;
                double mouseMoveDistanceX = (mouseX-oldMouseX) * mouseSensitivity;
                double mouseMoveDistanceY = (mouseY-oldMouseY) * mouseSensitivity;
                double oldxDir = xDir;
                xDir = xDir * Math.cos(-mouseMoveDistanceX) - yDir * Math.sin(-mouseMoveDistanceX);
                yDir = oldxDir * Math.sin(-mouseMoveDistanceX) + yDir * Math.cos(-mouseMoveDistanceX);
                double oldxPlane = xPlane;
                xPlane = xPlane * Math.cos(-mouseMoveDistanceX) - yPlane * Math.sin(-mouseMoveDistanceX);
                yPlane = oldxPlane * Math.sin(-mouseMoveDistanceX) + yPlane * Math.cos(-mouseMoveDistanceX);
                
            }
        }
    }
    public void keyTyped(KeyEvent arg0) {
        
        
    }
}