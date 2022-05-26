import org.junit.jupiter.api.Test;
import pickyeater.algorithms.BodyFatCalculator;
import pickyeater.algorithms.DeurenbergCalculator;
import pickyeater.basics.user.Sex;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;


class PlusMinusCellRenderer extends JPanel implements TableCellRenderer {
    public Component getTableCellRendererComponent(
            final JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {

        if(row != 0 || column != 0) return this;
        JPanel jPanel = new JPanel(new BorderLayout());
        JButton jButton = new JButton("-");
        jButton.addActionListener( e -> System.out.println("A"));
        jButton.setSelected(true);
        jPanel.add(BorderLayout.LINE_START,jButton);
        jPanel.add(BorderLayout.LINE_END,new JTextField( value.toString()));
        this.add(jPanel);
        return this;
    }


    @Test
    public void bodyFat(){

        BodyFatCalculator c = new DeurenbergCalculator();
        System.out.println(c.calculate(150,300,21, Sex.FEMALE));
    }
}
