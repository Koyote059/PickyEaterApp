package pickyeater.UI.pages.registerpage;

import com.toedter.calendar.JDateChooser;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.themes.ColorButtons;
import pickyeater.UI.themes.filehandler.ThemeHandler;
import pickyeater.UI.themes.filehandler.ThemesEnum;
import pickyeater.algorithms.BodyFatCalculator;
import pickyeater.algorithms.DeurenbergCalculator;
import pickyeater.basics.user.Sex;
import pickyeater.builders.UserBuilder;
import pickyeater.executors.user.RegisterExecutor;
import pickyeater.utils.AgeCalculator;
import pickyeater.utils.JCalUtils;
import pickyeater.utils.StringToNumber;
import pickyeater.utils.StringsUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Register1 extends PickyPage {
    private final UserBuilder userBuilder;
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
    private JLabel txtName;
    private JLabel txtHeight;
    private JLabel txtWeight;
    private JLabel txtBirthday;
    private JLabel txtSex;
    private JLabel txtBodyfat;
    private final LocalDate nineteen = LocalDate.of(1900, 01, 01);

    public Register1(RegisterExecutor registerExecutor, JFrame parent) {
        super(parent);
        this.userBuilder = registerExecutor.getUserBuilder();
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        parent.setSize(677, 507);
        // Sex
        btMale.addActionListener(actionEvent -> {
            ColorButtons.ColorButtonGreen(btMale);
            ColorButtons.ColorButtonWhite(btFemale);
            userBuilder.setSex(Sex.MALE);
        });
        btFemale.addActionListener(actionEvent -> {
            ColorButtons.ColorButtonWhite(btMale);
            ColorButtons.ColorButtonGreen(btFemale);
            userBuilder.setSex(Sex.FEMALE);
        });
        // Birthday
        jBirthdayChooser.addPropertyChangeListener(propertyChangeEvent -> {
            userBuilder.setDateOfBirth(JCalUtils.jCalToLocDate(jBirthdayChooser.getDate()));
            if (LocalDate.now().compareTo(userBuilder.getDateOfBirth()) <= 0 || JCalUtils.youngerThan(userBuilder.getDateOfBirth(), nineteen)) {
                userBuilder.setDateOfBirth(null);
            }
        });
        btContinue.addActionListener(actionEvent -> {
            // Name
            if (!tfName.getText().isEmpty()) {
                userBuilder.setName(tfName.getText());
                if (userBuilder.getName().length() > 20 || !StringsUtils.isAlpha(userBuilder.getName())) {
                    txtName.setForeground(Color.red);
                    JOptionPane.showMessageDialog(panelZeroOne, "Insert valid name", "Error", JOptionPane.ERROR_MESSAGE);
                    userBuilder.setName(null);
                }
            } else {
                txtName.setForeground(Color.red);
                //JOptionPane.showMessageDialog(panelZeroOne, "Missing name", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            // Weight
            if (!tfWeight.getText().isEmpty()) {
                userBuilder.setWeight(StringToNumber.convertPositiveFloat(tfWeight.getText()));
                if (userBuilder.getWeight() > 800 || userBuilder.getWeight() < 10) {
                    txtWeight.setForeground(Color.red);
                    JOptionPane.showMessageDialog(panelZeroOne, "Insert valid weight", "Error", JOptionPane.ERROR_MESSAGE);
                    userBuilder.setWeight(0);
                }
            } else {
                txtWeight.setForeground(Color.red);
                //JOptionPane.showMessageDialog(panelZeroOne, "Missing weight", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            // Height
            if (!tfHeight.getText().isEmpty()) {
                userBuilder.setHeight(StringToNumber.convertPositiveInteger(tfHeight.getText()));
                if (userBuilder.getHeight() > 300 || userBuilder.getHeight() < 10) {
                    txtHeight.setForeground(Color.red);
                    JOptionPane.showMessageDialog(panelZeroOne, "Insert valid height", "Error", JOptionPane.ERROR_MESSAGE);
                    userBuilder.setHeight(0);
                }
            } else {
                txtHeight.setForeground(Color.red);
                //JOptionPane.showMessageDialog(panelZeroOne, "Missing height", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            // Birthday
            if (userBuilder.getDateOfBirth() == null) {
                txtBirthday.setForeground(Color.red);
    //JOptionPane.showMessageDialog(panelZeroOne, "Insert valid birthday", "Error", JOptionPane.ERROR_MESSAGE);
            }
            // Sex
            if (userBuilder.getSex() == null) {
                txtSex.setForeground(Color.red);
                //JOptionPane.showMessageDialog(panelZeroOne, "Missing sex", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            // BodyFat
            if (!tfBodyfat.getText().isEmpty()) {
                userBuilder.setBodyFat(StringToNumber.convertPositiveFloat(tfBodyfat.getText()));
                if (userBuilder.getBodyFat() < 0 | userBuilder.getBodyFat() > 100) {
                    txtBodyfat.setForeground(Color.red);
                    JOptionPane.showMessageDialog(panelZeroOne, "Insert valid body-fat percentage", "Error", JOptionPane.ERROR_MESSAGE);
                    userBuilder.setBodyFat(0);
                } else {
                    next();
                }
            } else {
                next();
            }
        });
        txtChangeTheme.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (ThemeHandler.ReadTheme().equals(ThemesEnum.DARK_THEME)) {
                    ThemeHandler.ChangeTheme(ThemesEnum.LIGHT_THEME);
                    draw();
                } else {
                    ThemeHandler.ChangeTheme(ThemesEnum.DARK_THEME);
                    draw();
                }
                for (int i = 0; getComponentCount() > i; i++) {
                    SwingUtilities.updateComponentTreeUI(getComponent(i));
                }
                SwingUtilities.updateComponentTreeUI(parent);
            }
        });
    }

    private void next() {
        timer();
        if (userBuilder.getName() != null && userBuilder.getSex() != null && userBuilder.getWeight() != 0 && userBuilder.getHeight() != 0 && userBuilder.getDateOfBirth() != null) {
            if (userBuilder.getBodyFat() == 0) {
                BodyFatCalculator bodyFatCalculator = new DeurenbergCalculator();
                userBuilder.setBodyFat(bodyFatCalculator.calculate(userBuilder.getHeight(), userBuilder.getWeight(), new AgeCalculator().age(userBuilder.getDateOfBirth()), userBuilder.getSex()));
            }
            JOptionPane.showMessageDialog(panelZeroOne, "Selected:" + "\n" + "Name: " + userBuilder.getName() + "\n" + "Height: " + userBuilder.getHeight() + "cm\n" + "Weight: " + userBuilder.getWeight() + "Kg\n" + "Birthday: " + userBuilder.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" + "Sex: " + userBuilder.getSex() + "\n" + "Body fat: " + userBuilder.getBodyFat() + "%");
            RegisterMainFrame.changePage(2);
        }
    }

    private void draw() {
        new RegisterChangeTheme(txtChangeTheme);

        if (userBuilder.getSex() == Sex.MALE) {
            ColorButtons.ColorButtonGreen(btMale);
            ColorButtons.ColorButtonWhite(btFemale);
        } else if (userBuilder.getSex() == Sex.FEMALE) {
            ColorButtons.ColorButtonWhite(btMale);
            ColorButtons.ColorButtonGreen(btFemale);
        } else {
            ColorButtons.ColorButtonWhite(btMale);
            ColorButtons.ColorButtonWhite(btFemale);
        }
    }

    private void createUIComponents() {
        jBirthdayChooser = new JDateChooser();
    }

    @Override
    public void showPage() {
        draw();
        super.showPage();
    }

    private void timer(){
        Timer timer = new Timer(3000, e -> {
            txtName.setForeground(null);
            txtWeight.setForeground(null);
            txtHeight.setForeground(null);
            txtSex.setForeground(null);
            txtBirthday.setForeground(null);
            txtBodyfat.setForeground(null);
        });
        timer.start();
    }
}
