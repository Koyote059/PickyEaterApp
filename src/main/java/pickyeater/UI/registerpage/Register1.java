package pickyeater.UI.registerpage;

/**
 * @author Claudio Di Maio
 */
import com.toedter.calendar.JDateChooser;
import pickyeater.executors.user.RegisterExecutor;
import pickyeater.themes.filehandler.ThemeHandler;
import pickyeater.themes.filehandler.ThemesEnum;
import pickyeater.utils.AgeCalculator;
import pickyeater.algorithms.BodyFatCalculator;
import pickyeater.algorithms.DeurenbergCalculator;
import pickyeater.basics.user.Sex;
import pickyeater.builders.UserBuilder;
import pickyeater.executors.ExecutorProvider;
import pickyeater.utils.JCalendarToLocalDate;
import pickyeater.utils.StringToNumber;

import javax.swing.*;
import java.awt.*;
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
    private JDateChooser jBirthdayChooser;
    private final UserBuilder userBuilder;

    public Register1() {
        RegisterExecutor registerExecutor = ExecutorProvider.getRegisterExecutor();
        this.userBuilder = registerExecutor.getUserBuilder();
        setContentPane(mainPanel);
        setSize(677, 507);    //pack();
        setResizable(false);
        //setLocation(350,150);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        if (new ThemeHandler().ReadTheme() == ThemesEnum.LIGHT_THEME) {
            btMale.setBackground(Color.decode("#FFFFFF"));
            btFemale.setBackground(Color.decode("#FFFFFF"));
        } else if (new ThemeHandler().ReadTheme() == ThemesEnum.DARK_THEME){
            btMale.setBackground(Color.decode("#121212"));
            btFemale.setBackground(Color.decode("#121212"));
        }

        // Sex
        btMale.addActionListener(actionEvent -> {
            if (new ThemeHandler().ReadTheme() == ThemesEnum.LIGHT_THEME) {
                btMale.setBackground(Color.decode("#B1EA9D"));
                btFemale.setBackground(Color.decode("#FFFFFF"));
            } else if (new ThemeHandler().ReadTheme() == ThemesEnum.DARK_THEME){
                btMale.setBackground(Color.decode("#32AB5E"));
                btFemale.setBackground(Color.decode("#121212"));
            }
            userBuilder.setSex(Sex.MALE);
        });
        btFemale.addActionListener(actionEvent -> {
            if (new ThemeHandler().ReadTheme() == ThemesEnum.LIGHT_THEME) {
                btMale.setBackground(Color.decode("#FFFFFF"));
                btFemale.setBackground(Color.decode("#B1EA9D"));
            } else if (new ThemeHandler().ReadTheme() == ThemesEnum.DARK_THEME){
                btMale.setBackground(Color.decode("#121212"));
                btFemale.setBackground(Color.decode("#32AB5E"));
            }
            userBuilder.setSex(Sex.FEMALE);
        });

        // Birthday
        jBirthdayChooser.addPropertyChangeListener(propertyChangeEvent -> {

            userBuilder.setDateOfBirth(new JCalendarToLocalDate().jCalToLocDate(jBirthdayChooser.getDate()));

            if (LocalDate.now().compareTo(userBuilder.getDateOfBirth()) <= 0){   //TODO: If a person is older than 150 years old -> null
                userBuilder.setDateOfBirth(null);
            }
        });
        btContinue.addActionListener(actionEvent -> {
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
                userBuilder.setWeight(new StringToNumber().convertPositiveFloat(tfWeight.getText()));
                if (userBuilder.getWeight() > 800 || userBuilder.getWeight() < 10){
                    JOptionPane.showMessageDialog(panelZeroOne, "Insert valid weight", "Error", JOptionPane.ERROR_MESSAGE);
                    userBuilder.setWeight(0);
                }
            } else {
                JOptionPane.showMessageDialog(panelZeroOne, "Missing weight", "Warning", JOptionPane.WARNING_MESSAGE);
            }

            // Height
            if (!tfHeight.getText().isEmpty()) {
                userBuilder.setHeight(new StringToNumber().convertPositiveInteger(tfHeight.getText()));
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
                userBuilder.setBodyFat(new StringToNumber().convertPositiveFloat(tfBodyfat.getText()));
                if (userBuilder.getBodyFat() < 0 | userBuilder.getBodyFat() > 100){
                    JOptionPane.showMessageDialog(panelZeroOne, "Insert valid body-fat percentage", "Error", JOptionPane.ERROR_MESSAGE);
                userBuilder.setBodyFat(0);
                } else {
                    next(registerExecutor);
                }
            } else {
                next(registerExecutor);
            }
        });
    }

    private void next(RegisterExecutor registerExecutor){
        if (userBuilder.getName() != null && userBuilder.getSex() != null && userBuilder.getWeight() != 0 && userBuilder.getHeight() != 0 && userBuilder.getDateOfBirth() != null) {
            if (userBuilder.getBodyFat() == 0) {
                BodyFatCalculator bodyFatCalculator = new DeurenbergCalculator();
                userBuilder.setBodyFat(bodyFatCalculator.calculate(userBuilder.getHeight(), userBuilder.getWeight(),
                 new AgeCalculator().age(userBuilder.getDateOfBirth()), userBuilder.getSex()));
            }
            JOptionPane.showMessageDialog(panelZeroOne, "Selected:" + "\n" + "Name: " + userBuilder.getName() + "\n" + "Height: " + userBuilder.getHeight() + "cm\n" + "Weight: " + userBuilder.getWeight() + "Kg\n" + "Birthday: " + userBuilder.getDateOfBirth() + "\n" + "Sex: " + userBuilder.getSex() + "\n" + "Body fat: " + userBuilder.getBodyFat() + "%");

            setVisible(false);
            new Register2(registerExecutor);
        }
    }
    private void createUIComponents() {
        jBirthdayChooser = new JDateChooser();
    }
}
