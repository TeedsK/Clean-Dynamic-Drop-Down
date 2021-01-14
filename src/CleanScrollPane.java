import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Color;
import java.awt.Dimension;
@SuppressWarnings("serial")
/**
 * A simple class to create scrollpanes with custom widths and colors for the scroll Thumb
 * @author Teeds - Theo K
 */
public class CleanScrollPane extends JScrollPane {
    JPanel body;
    int width = 4;
    Color background;
    Color thumb;

    /**
     * @param body JPanel the JScrollPane contains
     */
    public CleanScrollPane(JPanel body) {
        this.body = body;
        setup();
    }

    /**
     * @param body JPanel the JScrollPane contains
     * @param width Width of the scroll bar
     */
    public CleanScrollPane(JPanel body, int width) {
        this.body = body;
        this.width = width;
        setup();
    }

    /**
     * @param body JPanel the JScrollPane contains
     * @param width Width of the scroll bar
     * @param backgroundColor background Color of the scrollpane
     */
    public CleanScrollPane(JPanel body, int width, Color backgroundColor) {
        this.body = body;
        this.width = width;
        background = backgroundColor;
        setup();
    }

    /**
     * @param body JPanel the JScrollPane contains
     * @param width Width of the scroll bar
     * @param backgroundColor background Color of the scrollpane
     * @param scrollThumbColor color of the Thumb in the scrollbar
     */
    public CleanScrollPane(JPanel body, int width, Color backgroundColor, Color scrollThumbColor) {
        this.body = body;
        this.width = width;
        this.background = backgroundColor;
        this.thumb = scrollThumbColor;
        setup();
    }

    /**
     * Changes the speed the scrollbar moves at
     * @param movementSpeed speed scrollbar moves at
     */
    public void setIncrementSpeed(int movementSpeed) {
        this.getVerticalScrollBar().setUnitIncrement(movementSpeed);
    }

    /**
     * Creates the JScrollPane
     */
    private void setup() {
        this.getVerticalScrollBar().setPreferredSize(new Dimension(width, 2));
        this.getVerticalScrollBar().setBackground(background);
        this.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = thumb;
            }

            @Override
            protected JButton createIncreaseButton(int o) {
                return emptyButton();
            }

            @Override
            protected JButton createDecreaseButton(int o) {
                return emptyButton();
            }

            private JButton emptyButton() {
                JButton fakeButton = new JButton();
                fakeButton.setMinimumSize(new Dimension(0, 0));
                fakeButton.setMaximumSize(new Dimension(0, 0));
                fakeButton.setPreferredSize(new Dimension(0, 0));
                fakeButton.setOpaque(false);
                return fakeButton;
            }
        });
        if(body != null) {
            this.setViewportView(body);
        }
        this.setOpaque(false);
        this.setBorder(null);
    }
}