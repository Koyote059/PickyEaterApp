package pickyeater.UI.RegisterPage;

/**
 * @author Claudio Di Maio
 */

import com.toedter.calendar.JDateChooser;
import pickyeater.algorithms.BodyFatCalculator;
import pickyeater.algorithms.BodyFatCaluclatorWrong;
import pickyeater.basics.user.Sex;
import pickyeater.builders.UserBuilder;
import pickyeater.executors.RegisterExecutor;

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

    RegisterExecutor registerExecutor;
    UserBuilder userBuilder;
    public Register1(RegisterExecutor registerExecutor) {
        this.registerExecutor = registerExecutor;
        this.userBuilder = registerExecutor.getUserBuilder();
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // Sex
        btMale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btMale.setBackground(Color.green);
                btFemale.setBackground(Color.white);
                userBuilder.setSex(Sex.MALE);
            }
        });
        btFemale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btMale.setBackground(Color.white);
                btFemale.setBackground(Color.green);
                userBuilder.setSex(Sex.FEMALE);
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

                userBuilder.setDateOfBirth(LocalDate.of(parseInt(year), monthInt, parseInt(day)));

                if (LocalDate.now().compareTo(userBuilder.getDateOfBirth()) <= 0){   //TODO: If a person is older than 150 years old -> null
                    userBuilder.setDateOfBirth(null);
                }
            }
        });
        btContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Name
                if (!tfName.getText().isEmpty()) {
                    userBuilder.setName(tfName.getText());
                    if (userBuilder.getName().length() > 20){
                        JOptionPane.showMessageDialog(panelZeroOne, "Insert valid name", "Error", JOptionPane.ERROR_MESSAGE);
                        userBuilder.setName(null);
                    }
                } else {
                    JOptionPane.showMessageDialog(panelZeroOne, "Missing name", "Warning", JOptionPane.WARNING_MESSAGE);
                }

                // Weight
                if (!tfWeight.getText().isEmpty()) {
                    userBuilder.setWeight(Float.parseFloat(tfWeight.getText()));
                    if (userBuilder.getWeight() > 800 || userBuilder.getWeight() < 10){
                        JOptionPane.showMessageDialog(panelZeroOne, "Insert valid weight", "Error", JOptionPane.ERROR_MESSAGE);
                        userBuilder.setWeight(0);
                    }
                } else {
                    JOptionPane.showMessageDialog(panelZeroOne, "Missing weight", "Warning", JOptionPane.WARNING_MESSAGE);
                }

                // Height
                if (!tfHeight.getText().isEmpty()) {
                    userBuilder.setHeight(Integer.parseInt(tfHeight.getText()));
                    if (userBuilder.getHeight() > 300 || userBuilder.getHeight() < 10){
                        JOptionPane.showMessageDialog(panelZeroOne, "Insert valid height", "Error", JOptionPane.ERROR_MESSAGE);
                        userBuilder.setHeight(0);
                    }
                } else {
                    JOptionPane.showMessageDialog(panelZeroOne, "Missing height", "Warning", JOptionPane.WARNING_MESSAGE);
                }

                // Birthday
                if (userBuilder.getDateOfBirth() == null){
                    JOptionPane.showMessageDialog(panelZeroOne, "Insert valid birthday", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Sex
                if (userBuilder.getSex() == null){
                    JOptionPane.showMessageDialog(panelZeroOne, "Missing sex", "Warning", JOptionPane.WARNING_MESSAGE);
                }

                // BodyFat
                if (!tfBodyfat.getText().isEmpty()) {
                    userBuilder.setBodyFat(Float.parseFloat(tfBodyfat.getText()));
                    if (userBuilder.getBodyFat() < 0 | userBuilder.getBodyFat() > 100){
                        JOptionPane.showMessageDialog(panelZeroOne, "Insert valid body-fat percentage", "Error", JOptionPane.ERROR_MESSAGE);
                    userBuilder.setBodyFat(0);
                    } else {
                        // Continue
                        Continue(registerExecutor);
                    }
                } else {
                    // Continue
                    Continue(registerExecutor);
                }
            }
        });
    }

    private void Continue(RegisterExecutor registerExecutor){
        if (userBuilder.getName() != null && userBuilder.getSex() != null && userBuilder.getWeight() != 0 && userBuilder.getHeight() != 0 && userBuilder.getDateOfBirth() != null) {
            if (userBuilder.getBodyFat() == 0) {
                BodyFatCalculator bodyFatCalculator = new BodyFatCaluclatorWrong();
                userBuilder.setBodyFat(bodyFatCalculator.calculate(userBuilder.getHeight(), userBuilder.getWeight(), userBuilder.getSex()));
            }
            JOptionPane.showMessageDialog(panelZeroOne, "Selected:" + "\n" + "Name: " + userBuilder.getName() + "\n" + "Height: " + userBuilder.getHeight() + "cm\n" + "Weight: " + userBuilder.getWeight() + "Kg\n" + "Birthday: " + userBuilder.getDateOfBirth() + "\n" + "Sex: " + userBuilder.getSex() + "\n" + "Body fat: " + userBuilder.getBodyFat() + "%");

            // TODO: User Save - technically it's already done

            setVisible(false);
            new Register2(registerExecutor);
        }
    }
    private void createUIComponents() {
        jBirthdayChooser = new JDateChooser();
    }
}
