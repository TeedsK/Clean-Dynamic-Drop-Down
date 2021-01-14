import javax.swing.BorderFactory;
import javax.swing.JLabel;
import button.PressSpreadButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.Font;
@SuppressWarnings("serial")
/**
 * An item for being added to a Drop Down
 * @author Teeds - Theo K
 */
public class Item extends PressSpreadButton {
    JLabel title;
    boolean selected = false;
    IncreaseJLabelAlpha increase;

    /**
     * Creates the Item object
     * @param name the name of the item
     * @param width the width of the item jpanel
     * @param height the height of the item jpanel
     * @param dropDownButton the DropDown button 
     */
    public Item(String name, int width, int height, DropSelection dropDownButton) {
        super(new Color(49,51,67), new Color(56,58,75), new Color(66,69,87), new Color(95,97,116), 15);
        super.setMaximumSize(new Dimension(width,height));
        super.setPreferredSize(new Dimension(width,height));
        super.setMinimumSize(new Dimension(width,height));
        buttonColorFadeOut();
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        setHoverIncrementAmount(15);
        setPressIncrementAmount(15);
        setPressedSleepAmount(3);
        this.title = new JLabel(name);
        setupTitle();
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        final Item thisItem = this;
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selected = true;
                dropDownButton.setSelectedItem(thisItem);
                increase.setMinimumAlpha(255);
                buttonColorFadeIn();
            }
        });
    }

    /**
     * sets up the title of the item panel
     */
    private void setupTitle() {
        this.title.setForeground(new Color(255,255,255));
        this.title.setForeground(new Color(255,255,255, 140));
        increase = new IncreaseJLabelAlpha(this.title, new Color(255,255,255, 140), 255, 140, 5);
        addMouseListener(increase);
        Font u = new Font("Calibri", Font.PLAIN, 15);
        try {
            u = Font.createFont(Font.TRUETYPE_FONT, new File("src//Fonts//OpenSans//open-sans.semibold.ttf"));
        } catch(Exception err1) {}
        this.title.setFont(u.deriveFont(Font.PLAIN, 14.3f));
        add(this.title, BorderLayout.WEST);
    }

    /**
     * @return wether or not this item is selected
     */
    public boolean getSelected() {
        return selected;
    }

    /**
     * Sets this item to selected or not
     * @param b boolean on setting this item to selected
     */
    public void setSelected(boolean b) {
        if(!b) {
            increase.setMinimumAlpha(140);
            increase.FadeOut();
            buttonColorFadeOut();
        }
        this.selected = b;
    }

    /**
     * @return the name of the item
     */
    public String toString() {
        return title.getText();
    }
}
