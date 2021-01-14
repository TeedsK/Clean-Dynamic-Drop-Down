import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import javax.swing.JLabel;

/**
 * Simple way to gradually increase the alpha of a given JLabel
 * @author Teeds - Theo K
 */
public class IncreaseJLabelAlpha implements MouseListener {
    JLabel label;
    int alpha;
    int maximumALpha;
    int minimumAlpha;
    Color color;
    boolean hover = false;
    int increment = 1;

    /**
     * @param label The label being changed
     * @param color The base color
     * @param maximumAlpha Maximum Alpha allowed
     * @param minimumAlpha Minimum Alpha allowed
     */
    public IncreaseJLabelAlpha(JLabel label, Color color, int maximumAlpha, int minimumAlpha) {
        this.label = label;
        this.color = color;
        this.alpha = minimumAlpha;
        this.maximumALpha = maximumAlpha;
        this.minimumAlpha = minimumAlpha;
    }

    /**
     * @param label The label being changed
     * @param color The base color
     * @param maximumAlpha Maximum Alpha allowed
     * @param minimumAlpha Minimum Alpha allowed
     * @param increment Speed to change the alpha
     */
    public IncreaseJLabelAlpha(JLabel label, Color color, int maximumAlpha, int minimumAlpha, int increment) {
        this.label = label;
        this.color = color;
        this.alpha = minimumAlpha;
        this.maximumALpha = maximumAlpha;
        this.minimumAlpha = minimumAlpha;
        this.increment = increment;
    }

    /**
     * changes the maximum Alpha
     * @param maximumALpha desired maximum Alpha
     */
    public void setMaximumALpha(int maximumALpha) {
        this.maximumALpha = maximumALpha;
    }
    
    /**
     * change the minimum alpha
     * @param minimumAlpha desired minimum Alpha
     */
    public void setMinimumAlpha(int minimumAlpha) {
        this.minimumAlpha = minimumAlpha;
    }

    /**
     * change the current alpha
     * @param currentAlpha desired current Alpha
     */
    public void setCurrentAlpha(int currentAlpha) {
        this.alpha = currentAlpha;
    }

    /**
     * Decreases the Alpha
     */
    public void FadeOut() {
        Thread t = new Thread() {
            public void run() {
                hover = false;
                while(!hover && alpha > minimumAlpha) {
                    alpha-=increment;
                    try {
                        sleep(8);
                    } catch(Exception err1) {}
                    label.setForeground(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
                }
            }
        }; t.start();
    }

    /**
     * Increases the Alpha
     */
    public void FadeIn() {
        Thread t = new Thread() {
            public void run() {
                hover = true;
                while(hover && alpha < maximumALpha) {
                    alpha+=increment;
                    try {
                        sleep(8);
                    } catch(Exception err1) {}
                    label.setForeground(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
                }
            }
        }; t.start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        FadeIn();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        FadeOut();
    }
    
}
