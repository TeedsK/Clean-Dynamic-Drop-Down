import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JWindow;

import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusAdapter;
import java.awt.geom.RoundRectangle2D;
@SuppressWarnings("serial")
/**
 * This creates the actual drop down that contains all the items
 * @author Teeds - Theo K
 */
public class DropDown extends JWindow implements ComponentListener, FocusListener{
    ArrayList<Item> items = new ArrayList<Item>();
    JPanel body;
    boolean opening = false;
    boolean focused = false;
    Arrow arrow;
    JFrame parent;
    int maximumHeight = Integer.MAX_VALUE;
    CleanScrollPane scroll;

    /**
     * Creates the drop actual drop down
     * @param width The desired width of the drop down
     * @param parent The JFrame of the application
     */
    public DropDown(int width, JFrame parent) {
        this.parent = parent;
        parent.addComponentListener(this);
        parent.addFocusListener(this);
        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                focused = true;
            }
            public void focusLost(FocusEvent e) {
                focused = false;
            }
        });
        setPreferredSize(new Dimension(width, 0));
        setMinimumSize(new Dimension(width, 0));
        setMaximumSize(new Dimension(width, 0));
        setSize(width, 0);
        setAlwaysOnTop(true);
        setupBody();
        pack();
        setVisible(false);
    }

    /**
     * set the maximum height for the drop down
     * @param height maximum height
     */
    public void setMaximumHeight(int height) {
        maximumHeight = height;
    }

    /**
     * @return The desired maximum height of the drop down
     */
    public int getWantedHeight() {
        if(maximumHeight != Integer.MAX_VALUE) {
            return maximumHeight;
        }
        return (items.size() * items.get(0).getHeight()) + 15;
    }

    /**
     * Sets up the body of the drop down
     */
    private void setupBody() {
        body = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D graphics = (Graphics2D) g;
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setColor(new Color(49,51,67));
                graphics.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        scroll = new CleanScrollPane(body, 2, new Color(49,51,67), new Color(107,114,154));
        scroll.setIncrementSpeed(11);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        body.setDoubleBuffered(true);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        // body.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        this.add(scroll);
    }

    /**
     * Sets the arrow component of the drop down
     * @param arrow Arrow that is going to be changed wether or not drop down is activve
     */
    public void setArrow(Arrow arrow) {
        this.arrow = arrow;
    }

    /**
     * Adds an item to the drop down ArrayList and body
     * @param item Item desired to be added
     */
    public void addItem(Item item) {
        this.items.add(item);
        body.add(item);
        updateItems();
    }

    /**
     * repaints and revalidates the drop down and all its children components
     */
    public void updateItems() {
        for(Item i : items) {
            i.repaint();
            i.revalidate();
            body.repaint();
            body.revalidate();
        }
    }

    /**
     * Updates the drop down's location
     * @param x X Location on the screen
     * @param y Y Location on the screen
     */
    public void setFrameLocation(int x, int y) {
        setLocation(x, y);
    }

    /**
     * Changes the size of the drop down
     * @param open boolean depending on opening or closing
     * @param wantedSize desired size to change to
     * @param multiplier the speed of changing the size
     */
    public void ChangeSize(boolean open, int wantedSize, int multiplier) {
        Thread t = new Thread() {
            public void run() {
                opening = open;
                setVisible(true);
                while (opening == open && getHeight() != wantedSize) {
                    if (getHeight() < wantedSize) {
                        updateSize(getWidth(), getHeight() + (additionAmount(wantedSize) * multiplier));
                    } else {
                        updateSize(getWidth(), getHeight() - (additionAmount(wantedSize) * multiplier));
                    }
                    if(Math.abs(wantedSize - getHeight()) <= 5) {
                        updateSize(getWidth(), wantedSize);
                    }
                    try {
                        sleep(10);
                    } catch (Exception err1) {}
                }
            }
        };
        t.start();
    }
    
    /**
     * This makes the the drop down slow down the closer it gets to the desired size and speed up if its far away.
     * @param wanted the wanted size of the drop down
     * @return the amount the drop will change in size per iteration
     */
    private int additionAmount(int wanted) {
        int h = 2;
        if (Math.abs(wanted - getHeight()) >= 10) {
            h += 2;
        }
        if (Math.abs(wanted - getHeight()) >= 20) {
            h += 4;
        }
        if (Math.abs(wanted - getHeight()) >= 50) {
            h += 5;
        }
        if (Math.abs(wanted - getHeight()) >= 60) {
            h += 6;
        }
        return h;
    }


    /**
     * Updates the size of the drop down
     * @param width desired width of the drop down
     * @param height desired height of the drop down
     */
    private void updateSize(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setShape(new RoundRectangle2D.Double(0, 0, width, height, 15, 15));
        setSize(width, height);
        revalidate();
        repaint();
    }


    /**
     * Updates the location of the drop down to be aligned with the button
     */
    private void updateLocation() {
        if(this.isVisible()) {
            Point p = arrow.getParent().getParent().getLocationOnScreen();
            setFrameLocation((int) p.getX(), (int) p.getY() + arrow.getParent().getHeight() + 7);
        }
        
    }

    /**
     * Quickly hides the drop down and changes the arrow to be closed if it exists
     */
    private void quickHide() {
        if(opening) {
            if(arrow != null && arrow.getOpened()) {
                arrow.setOpened(false);
                arrow.Transition("down", 360);
            }
            updateSize(getWidth(), 0);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        updateLocation();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        updateLocation();
    }

    @Override
    public void componentShown(ComponentEvent e) {
        updateLocation();
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        updateLocation();
    }
    
    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(opening && !focused) {
            quickHide();
        }
    }
}
