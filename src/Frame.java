import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{

    private Panel panel;

    public Frame(CPU c){
        setPreferredSize(new Dimension(640,320));
        pack();
        setPreferredSize(new Dimension(640 + getInsets().left +getInsets().right, 320 + getInsets().top + getInsets().bottom));
        panel = new Panel(c);
        panel.setKeys();
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}

