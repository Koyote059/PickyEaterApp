package pickyeater.UI.pages.app.dailyprogresspage;

import pickyeater.utils.pagesutils.NutrientsBarChart;

import javax.swing.*;
import java.awt.*;

public class ProgressGraphPage extends JDialog {
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JButton btContinue = new JButton("Continue");

    public ProgressGraphPage(JFrame parent, Point location) {
        super(parent);
        setContentPane(mainPanel);
        mainPanel.add(BorderLayout.SOUTH, btContinue);
        mainPanel.add(BorderLayout.CENTER, new NutrientsBarChart().getPanel());
        setSize(new Dimension(677, 507));
        setResizable(false);
        setLocation(location);
        setVisible(true);
        btContinue.setBorder(null);
        btContinue.addActionListener(e -> dispose());
    }
}
