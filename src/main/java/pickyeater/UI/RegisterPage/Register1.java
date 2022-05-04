package pickyeater.UI.RegisterPage;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

public class Register1 extends JFrame{
    private JPanel mainPanel;
    private JPanel panelZeroOne;
    private JTextField tfName;
    private JButton btMale;
    private JButton btFemale;
    private JButton btContinue;
    private JTextField tfWeight;
    private JTextField tfHeight;
    private JTextField tfBodyfat;
    private JPanel birthdayPanel;
    private JDateChooser jBirthdayChooser;

    String sexTmp = null;
    LocalDate birthDayTmp = null;

    public Register1() {
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        btMale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sexTmp = actionEvent.getActionCommand();
            }
        });
        btFemale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sexTmp = actionEvent.getActionCommand();
            }
        });

        jBirthdayChooser.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                StringBuilder dateTmp = new StringBuilder();
                dateTmp.append(propertyChangeEvent.getNewValue());
                System.out.println(dateTmp);    //TODO: FINISH THIS
//                birthDayTmp = LocalDate.of(propertyChangeEvent.getNewValue());
            }
        });
        btContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Name
                String nameTmp = null;
                if (!tfName.getText().isEmpty()) {
                    nameTmp = tfName.getText();
                } else {
                    JOptionPane.showMessageDialog(panelZeroOne, "Missing name");
                }

                // Weight
                double weightTmp = 0;
                if (!tfWeight.getText().isEmpty()) {
                    weightTmp = Double.parseDouble(tfWeight.getText());
                } else {
                    JOptionPane.showMessageDialog(panelZeroOne, "Missing weight");
                }

                // Height
                double heightTmp = 0;
                if (!tfHeight.getText().isEmpty()) {
                    heightTmp = Double.parseDouble(tfHeight.getText());
                } else {
                    JOptionPane.showMessageDialog(panelZeroOne, "Missing height");
                }

                // Birthday
                if (birthDayTmp == null){
                    JOptionPane.showMessageDialog(panelZeroOne, "Missing birthday");
                }

                // Sex
                if (sexTmp == null){
                    JOptionPane.showMessageDialog(panelZeroOne, "Missing sex");
                }

                // BodyFat
                double bodyFatTmp;
                if (!tfBodyfat.getText().isEmpty()) {
                    bodyFatTmp = Double.parseDouble(tfBodyfat.getText());
                }

                // All
                if (nameTmp != null && sexTmp != null && weightTmp != 0 && heightTmp != 0 && birthDayTmp != null){
                    System.out.println("OK!");
                } else {
                    JOptionPane.showMessageDialog(panelZeroOne, "Add missing stuff.");
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Register1::new);
    }

    private void createUIComponents() {
        jBirthdayChooser = new JDateChooser();
        // TODO: place custom component creation code here
    }
}
