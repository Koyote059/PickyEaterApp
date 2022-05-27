package pickyeater.UI.registerpage;

/**
 * @author Claudio Di Maio
 */
import com.toedter.calendar.JDateChooser;
import pickyeater.executors.user.RegisterExecutor;
import pickyeater.themes.ColorButtons;
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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    private JLabel txtChangeTheme;
    private final UserBuilder userBuilder;

    public Register1() {
        RegisterExecutor registerExecutor = ExecutorProvider.getRegisterExecutor();
        ColorButtons cB = new ColorButtons();
        this.userBuilder = registerExecutor.getUserBuilder();
        setContentPane(mainPanel);
        setSize(677, 507);    //pack();
        setResizable(false);
        //setLocation(350,150);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        draw();

        // Sex
        btMale.addActionListener(actionEvent -> {
            cB.ColorButtonGreen(btMale);
            cB.ColorButtonWhite(btFemale);
            userBuilder.setSex(Sex.MALE);
        });
        btFemale.addActionListener(actionEvent -> {
            cB.ColorButtonWhite(btMale);
            cB.ColorButtonGreen(btFemale);
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
        txtChangeTheme.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (new ThemeHandler().ReadTheme().equals(ThemesEnum.DARK_THEME)){
                    new ThemeHandler().ChangeTheme(ThemesEnum.LIGHT_THEME);
                    draw();
                } else {
                    new ThemeHandler().ChangeTheme(ThemesEnum.DARK_THEME);
                    draw();
                }

                for (int i = 0; rootPane.getComponentCount() > i; i++) {
                    SwingUtilities.updateComponentTreeUI(rootPane.getComponent(i));
                }
            }
        });
    }

    private void draw(){
        new ColorButtons().ColorButtonWhite(btMale);
        new ColorButtons().ColorButtonWhite(btFemale);

        txtChangeTheme.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (new ThemeHandler().ReadTheme() == ThemesEnum.DARK_THEME){
            try {
                BufferedImage binImage = ImageIO.read(new File("res/images/sun.png"));
                txtChangeTheme.setIcon(new ImageIcon(binImage.getScaledInstance(30,30,Image.SCALE_SMOOTH)));
                txtChangeTheme.setText("");
            } catch (IOException | NullPointerException ignored) {
            }
        } else {
            try {
                new ThemeHandler().ReadTheme();
                BufferedImage binImage = ImageIO.read(new File("res/images/moon.png"));
                txtChangeTheme.setIcon(new ImageIcon(binImage.getScaledInstance(30,30,Image.SCALE_SMOOTH)));
                txtChangeTheme.setText("");
            } catch (IOException | NullPointerException ignored) {
            }
        }
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

    public static void updateComponentTreeUI0(Component c) {
        SwingUtilities.updateComponentTreeUI(c);
        c.invalidate();
        c.validate();
        c.repaint();
    }
}
