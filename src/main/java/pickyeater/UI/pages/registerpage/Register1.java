package pickyeater.UI.pages.registerpage;

/**
 * @author Claudio Di Maio
 */
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
import pickyeater.utils.JCalendarToLocalDate;
import pickyeater.utils.StringToNumber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Register1 extends PickyPage {
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

    public Register1(RegisterExecutor registerExecutor, JFrame parent) {
        super(parent);
        ColorButtons cB = new ColorButtons();
        this.userBuilder = registerExecutor.getUserBuilder();
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        parent.setSize(677, 507);    //pack();
        //setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2 - 677/2,Toolkit.getDefaultToolkit().getScreenSize().height/2 - 507/2);

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
                for (int i = 0; getComponentCount() > i; i++) {
                    SwingUtilities.updateComponentTreeUI(getComponent(i));
                }

                SwingUtilities.updateComponentTreeUI(parent);
            }
        });
    }

    private void draw(){
        new RegisterChangeTheme(txtChangeTheme);
        ColorButtons cb = new ColorButtons();
        if (userBuilder.getSex() == Sex.MALE){
            cb.ColorButtonGreen(btMale);
            cb.ColorButtonWhite(btFemale);
        } else if (userBuilder.getSex() == Sex.FEMALE){
            cb.ColorButtonWhite(btMale);
            cb.ColorButtonGreen(btFemale);
        } else {
            cb.ColorButtonWhite(btMale);
            cb.ColorButtonWhite(btFemale);
        }
    }

    private void next(RegisterExecutor registerExecutor){
        if (userBuilder.getName() != null && userBuilder.getSex() != null && userBuilder.getWeight() != 0 && userBuilder.getHeight() != 0 && userBuilder.getDateOfBirth() != null) {
            if (userBuilder.getBodyFat() == 0) {
                BodyFatCalculator bodyFatCalculator = new DeurenbergCalculator();
                userBuilder.setBodyFat(bodyFatCalculator.calculate(userBuilder.getHeight(), userBuilder.getWeight(),
                 new AgeCalculator().age(userBuilder.getDateOfBirth()), userBuilder.getSex()));
            }
            JOptionPane.showMessageDialog(panelZeroOne,"Selected:" + "\n" + "Name: " + userBuilder.getName() + "\n" + "Height: " + userBuilder.getHeight() + "cm\n" + "Weight: " + userBuilder.getWeight() + "Kg\n" + "Birthday: " + userBuilder.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" + "Sex: " + userBuilder.getSex() + "\n" + "Body fat: " + userBuilder.getBodyFat() + "%");
            RegisterMainFrame.changePage(2);
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
}
