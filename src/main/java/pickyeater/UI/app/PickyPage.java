package pickyeater.UI.app;

import javax.swing.*;
import java.awt.*;

public abstract class PickyPage extends JPanel {

    protected final JFrame parent;
    public PickyPage(JFrame parent){
        this.parent = parent;

        parent.add(this,this.getClass().getName());
    }

    public void showPage(){
        Container container = parent.getContentPane();
        CardLayout layout = (CardLayout) container.getLayout();
        layout.show(container,this.getClass().getName());

    }

}
