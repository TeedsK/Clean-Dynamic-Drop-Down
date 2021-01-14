import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class main {
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setMinimumSize(new Dimension(500,500));
        JPanel background = new JPanel();
        background.setBackground(new Color(27, 30, 39));
        background.setLayout(new BoxLayout(background, BoxLayout.X_AXIS));
        background.add(Box.createHorizontalGlue());
        DropSelection d = new DropSelection(150,210, f, "Name");
        background.add(d);
        background.add(Box.createHorizontalGlue());
        f.add(background);
        d.addItem("Date");
        d.addItem("Size");
        d.addItem("Cost");
        d.addItem("Return");
        d.addItem("Percent");
        d.addItem("Status");
        d.addItem("Listed");
        d.addItem("Platform");
        d.addItem("Listed Price");
        d.addItem("Payout");
        f.pack();
        f.setVisible(true);
    }
}