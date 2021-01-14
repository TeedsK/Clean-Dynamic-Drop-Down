import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;

@SuppressWarnings("serial")
/**
 * This creates an arrow component which can be animated to move then point towards a certain direction
 * @author Teeds - Theo K
 */
public class Arrow extends JComponent {
    int width;
    int height;
    int rotation = 360;
    String rotatingTo = "up";
    boolean opened = false;

    /**
     * Creates the Arrow object
     * @param width Width of the Arrow Component
     * @param height Height of the Arrow Component
     */
    public Arrow(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width,height));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        int pointX = (getWidth() / 4);
        int pointY = (getHeight() /4) - 1;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.rotate(Math.toRadians(rotation), (getWidth() / 2), (getHeight() - 1) / 2);
        graphics.setColor(new Color(97,103,134));
        graphics.setStroke(new BasicStroke(2.3f));
        graphics.drawLine(pointX, pointY * 2, pointX * 2, pointY * 3);
        graphics.drawLine(pointX * 2, pointY * 3, pointX * 3, pointY * 2);
    }
 
    /**
     * @return if the arrow is pointing straight down (down means the drop down is open)
     */
    public boolean getOpened() {
        return opened;
    }

    /**
     * changes if the drop down is active or not
     * @param b boolean on wether or not a drop down is active
     */
    public void setOpened(boolean b) {
        this.opened = b;
    }

    /**
     * 
     * @param destination String naming the destination (safety net to make program doesn't fight itself rotating arrow)
     * @param wantedPosition  The wanted position the arrow will be pointing towards
     */
    public void Transition(String destination, int wantedPosition) {
        Thread t = new Thread() {
            public void run() {
                rotatingTo = destination;
                while(rotatingTo.equals(destination) && rotation != wantedPosition) {
                    if(rotation < wantedPosition) {
                        rotation += 2;
                        if(Math.abs(wantedPosition - Math.abs(rotation)) >= 20) {
                            rotation += 2;
                        }
                        if(Math.abs(wantedPosition - Math.abs(rotation)) >= 35) {
                            rotation += 2;
                        }
                        if(Math.abs(wantedPosition - Math.abs(rotation)) >= 50) {
                            rotation += 2;
                        }
                        if(Math.abs(wantedPosition - Math.abs(rotation)) >= 70) {
                            rotation += 2;
                        }
                    } else {
                        rotation -= 2;
                        if(Math.abs(wantedPosition - Math.abs(rotation)) >= 20) {
                            rotation -= 2;
                        }
                        if(Math.abs(wantedPosition - Math.abs(rotation)) >= 35) {
                            rotation -= 2;
                        }
                        if(Math.abs(wantedPosition - Math.abs(rotation)) >= 50) {
                            rotation -= 2;
                        }
                        if(Math.abs(wantedPosition - Math.abs(rotation)) >= 70) {
                            rotation -= 2;
                        }
                    }
                    if(Math.abs(wantedPosition - Math.abs(rotation)) <= 10) {
                        rotation = wantedPosition;
                    }
                    repaint();
                    sleepTime(10);
                }
            }
        }; t.start();
    } 

    /**
     * Makes the thread sleep for a wanted amount of time
     * @param t The sleep time
     */
    private void sleepTime(int t) {
        try {
            Thread.sleep(t);
        } catch(Exception e) {}
    }

    /**
     * @return the height of the arrow jcomponent
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the width of the arrow jcomponent
     */
    public int getWidth() {
        return width;
    }
}
