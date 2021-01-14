import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
@SuppressWarnings("serial")
/**
 * A clean dynamic drop down selection button
 * @author teeds - Theo K
 */
public class DropSelection extends JPanel implements MouseListener {
    Item selected;
    Arrow arrow;
    DropDown drop;
    JLabel title;
    int ItemHeight = 40;
    int ItemWidth = 40;
    /**
     * 
     * @param width width drop down
     * @param maximumHeight maximum height of the drop down
     * @param frame the parent JFrame
     * @param baseSelected the first item that is already selected
     */
    public DropSelection(int width, int maximumHeight, JFrame frame, String baseSelected) {
        setMinimumSize(new Dimension(200,40));
        setPreferredSize(new Dimension(200,40));
        setMaximumSize(new Dimension(200,40));
        setSize(200,40);
        setLayout(new BorderLayout());
        setBackground(new Color(44,46,61));
        addMouseListener(this);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setupArrow();
        drop = new DropDown(width, frame);
        // drop.setMaximumHeight(maximumHeight);
        drop.setArrow(arrow);
        selected = addItem(baseSelected);
        selected.setSelected(true);
        setupTitle();
    }

    /**
     * Sets up the arrow
     */
    private void setupArrow() {
        arrow = new Arrow(30, 30);
        JPanel rightSide = new JPanel();
        int top = (getHeight() - arrow.getHeight()) / 2;
        rightSide.setOpaque(false);
        rightSide.setLayout(new BorderLayout());
        rightSide.add(arrow, BorderLayout.CENTER);
        rightSide.setBorder(BorderFactory.createEmptyBorder(top, 0, 0, 10));
        rightSide.add(arrow, BorderLayout.CENTER);
        add(rightSide, BorderLayout.EAST);
    }

    /**
     * Creates & sets up the Title/Selected Item JLabel
     */
    private void setupTitle() {
        title = new JLabel(selected.toString());
        title.setForeground(new Color(255,255,255));
        add(title, BorderLayout.WEST);
        title.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        Font u = new Font("Calibri", Font.PLAIN, 15);
        try {
            u = Font.createFont(Font.TRUETYPE_FONT, new File("src//Fonts//OpenSans//open-sans.semibold.ttf"));
        } catch(Exception err1) {}
        title.setFont(u.deriveFont(Font.PLAIN, 15.1f));
    }

    public void setSelectedItem(Item item) {
        this.selected.setSelected(false);
        this.selected = item;
        this.title.setText(item.toString());
        drop.updateItems();
    }

    public Item addItem(String title) {
        Item i = new Item(title, 150, ItemHeight, this);
        drop.addItem(i);
        return i;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(arrow.getOpened()) {
            drop.ChangeSize(false, 0, 1);
            arrow.setOpened(false);
            arrow.Transition("down", 360);
        } else {
            drop.ChangeSize(true, drop.getWantedHeight(), 1);
            arrow.setOpened(true);
            arrow.Transition("up", 180);
            Point p = arrow.getParent().getParent().getLocationOnScreen();
            drop.setFrameLocation((int) p.getX(), (int) p.getY() + arrow.getParent().getHeight() + 7);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}
