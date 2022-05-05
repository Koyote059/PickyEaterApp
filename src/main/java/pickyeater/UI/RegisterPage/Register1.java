package pickyeater.UI.RegisterPage;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

import static java.lang.Integer.parseInt;

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

        // Sex
        btMale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sexTmp = actionEvent.getActionCommand();
                btMale.setBackground(Color.green);
                btFemale.setBackground(Color.white);
            }
        });
        btFemale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sexTmp = actionEvent.getActionCommand();
                btMale.setBackground(Color.white);
                btFemale.setBackground(Color.green);
            }
        });

        // Birthday
        jBirthdayChooser.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                StringBuilder dateTmp = new StringBuilder();
                dateTmp.append(propertyChangeEvent.getNewValue());
                // System.out.println(dateTmp);
                String month = dateTmp.substring(4, 7);
                String day = dateTmp.substring(8, 10);
                String year = dateTmp.substring(dateTmp.length() - 4, dateTmp.length());
                // System.out.println("month: " + month + ", day: " + day + ", year: " + year);

                DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM")
                        .withLocale(Locale.ENGLISH);
                TemporalAccessor accessor = parser.parse(month);
                int monthInt = accessor.get(ChronoField.MONTH_OF_YEAR);

                birthDayTmp = LocalDate.of(parseInt(year), monthInt, parseInt(day));

                if (LocalDate.now().compareTo(birthDayTmp) <= 0){   //TODO: If a person is older than 150 years old -> null
                    birthDayTmp = null;
                }
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
                    JOptionPane.showMessageDialog(panelZeroOne, "Missing name", "Warning", JOptionPane.WARNING_MESSAGE);
                }

                // Weight
                double weightTmp = 0;
                if (!tfWeight.getText().isEmpty()) {
                    weightTmp = Double.parseDouble(tfWeight.getText());
                    if (weightTmp > 800 || weightTmp < 10){
                        JOptionPane.showMessageDialog(panelZeroOne, "Insert valid weight", "Error", JOptionPane.ERROR_MESSAGE);
                        weightTmp = 0;
                    }
                } else {
                    JOptionPane.showMessageDialog(panelZeroOne, "Missing weight", "Warning", JOptionPane.WARNING_MESSAGE);
                }

                // Height
                double heightTmp = 0;
                if (!tfHeight.getText().isEmpty()) {
                    heightTmp = Double.parseDouble(tfHeight.getText());
                    if (heightTmp > 300 || heightTmp < 10){
                        JOptionPane.showMessageDialog(panelZeroOne, "Insert valid height", "Error", JOptionPane.ERROR_MESSAGE);
                        heightTmp = 0;
                    }
                } else {
                    JOptionPane.showMessageDialog(panelZeroOne, "Missing height", "Warning", JOptionPane.WARNING_MESSAGE);
                }

                // Birthday
                if (birthDayTmp == null){
                    JOptionPane.showMessageDialog(panelZeroOne, "Insert valid birthday", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Sex
                if (sexTmp == null){
                    JOptionPane.showMessageDialog(panelZeroOne, "Missing sex", "Warning", JOptionPane.WARNING_MESSAGE);
                }

                // BodyFat
                double bodyFatTmp = 0;
                if (!tfBodyfat.getText().isEmpty()) {
                    bodyFatTmp = Double.parseDouble(tfBodyfat.getText());
                }

                // Continue
                if (nameTmp != null && sexTmp != null && weightTmp != 0 && heightTmp != 0 && birthDayTmp != null){
                    JOptionPane.showMessageDialog(panelZeroOne, "Selected:"  + "\n" +  "Name: " + nameTmp + "\n" + "Weight: " + weightTmp + "Kg\n" + "Height: " + heightTmp + "cm\n" + "Birthday: " + birthDayTmp + "\n" + "Sex: " + sexTmp + "\n" + "Body fat: " + bodyFatTmp + "%");
                    //System.out.println("OK!");

                    setVisible(false);
                    EventQueue.invokeLater(Register2::new);
                }
            }
        });
    }

    private void createUIComponents() {
        jBirthdayChooser = new JDateChooser();
    }
}
