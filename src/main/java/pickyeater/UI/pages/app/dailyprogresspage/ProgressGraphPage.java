package pickyeater.UI.pages.app.dailyprogresspage;

import pickyeater.utils.pagesutils.NutrientsCategoryChart;

import javax.swing.*;
import java.awt.*;

public class ProgressGraphPage extends JDialog {
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JButton btContinue = new JButton("Continue");

    public ProgressGraphPage(JFrame parent, Point location) {
        super(parent);
        setContentPane(mainPanel);
        mainPanel.add(BorderLayout.SOUTH, btContinue);
        mainPanel.add(BorderLayout.CENTER, new NutrientsCategoryChart().getPanel());
        setSize(new Dimension(677, 507));
        setResizable(false);
        setLocation(location);
        setVisible(true);
        btContinue.setBorder(null);
        btContinue.addActionListener(e -> dispose());
    }
}
