package pickyeater.UI.pages.registerpage;

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.pages.leftbuttons.PanelButtons;
import pickyeater.UI.themes.filehandler.ThemeHandler;
import pickyeater.UI.themes.filehandler.ThemesEnum;
import pickyeater.algorithms.HarrisBenedictCalculator;
import pickyeater.algorithms.NutrientsRequirementCalculator;
import pickyeater.basics.food.Nutrients;
import pickyeater.builders.NutrientsBuilder;
import pickyeater.builders.PickyNutrientsBuilder;
import pickyeater.builders.UserBuilder;
import pickyeater.executors.user.RegisterExecutor;
import pickyeater.utils.AgeCalculator;
import pickyeater.utils.StringToNumber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.Objects;

public class Register4 extends PickyPage {
    private final UserBuilder userBuilder;
    NutrientsBuilder nutrientsBuilder = new PickyNutrientsBuilder();
    private JPanel mainPanel;
    private JButton btBack;
    private JButton btDone;
    private JLabel txtCalories;
    private JTextField tfProteins;
    private JTextField tfCarbs;
    private JTextField tfFats;
    private JButton btReset;
    private JLabel txtChangeTheme;

    public Register4(RegisterExecutor registerExecutor, JFrame parent) {
        super(parent);
        userBuilder = registerExecutor.getUserBuilder();
        //setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2 - 677/2,Toolkit.getDefaultToolkit().getScreenSize().height/2 - 507/2);
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        btBack.addActionListener(actionEvent -> RegisterMainFrame.changePage(3));
        btDone.addActionListener(actionEvent -> {
            /*
            JOptionPane.showMessageDialog(mainPanel, "Selected:" + "\n" + "Calories: " + nutrientsBuilder.getCalories() + "\n" + "Proteins: " + nutrientsBuilder.getProteins() + "\n" + "Carbs: " + nutrientsBuilder.getCarbs() + "\n" + "Fats: " + nutrientsBuilder.getFats());
            */
            userBuilder.setRequiredNutrients(nutrientsBuilder.build());
            registerExecutor.saveUser(userBuilder.build());
            new MainFrame();
            MainFrame.changePage(PanelButtons.PROGRESS);
            parent.dispose();
        });
        btReset.addActionListener(actionEvent -> resetNutrients(userBuilder));
        ActionListener listener = actionEvent -> {
            if (StringToNumber.convertPositiveDouble(tfProteins.getText()) > 5000 | StringToNumber.convertPositiveDouble(tfProteins.getText()) < 0) {
                JOptionPane.showMessageDialog(mainPanel, "Insert valid number in Proteins", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                nutrientsBuilder.setProteins(StringToNumber.convertPositiveFloat(tfProteins.getText()));
            }
            if (StringToNumber.convertPositiveDouble(tfFats.getText()) > 5000 | StringToNumber.convertPositiveDouble(tfFats.getText()) < 0) {
                JOptionPane.showMessageDialog(mainPanel, "Insert valid number in Fats", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                nutrientsBuilder.setUnSaturatedFats(StringToNumber.convertPositiveFloat(tfFats.getText()));
            }
            if (StringToNumber.convertPositiveFloat(tfCarbs.getText()) > 5000 | StringToNumber.convertPositiveFloat(tfCarbs.getText()) < 0) {
                JOptionPane.showMessageDialog(mainPanel, "Insert valid number in Carbs", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                nutrientsBuilder.setComplexCarbs(StringToNumber.convertPositiveFloat(tfCarbs.getText()));
            }
            txtCalories.setText(Double.toString(nutrientsBuilder.getCalories()));
        };
        tfFats.addActionListener(listener);
        tfCarbs.addActionListener(listener);
        tfProteins.addActionListener(listener);
        txtChangeTheme.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (Objects.equals(ThemeHandler.ReadTheme(), ThemesEnum.DARK_THEME)) {
                    ThemeHandler.ChangeTheme(ThemesEnum.LIGHT_THEME);
                } else {
                    ThemeHandler.ChangeTheme(ThemesEnum.DARK_THEME);
                }
                new RegisterChangeTheme(txtChangeTheme);
                SwingUtilities.updateComponentTreeUI(parent);
            }
        });
    }

    private void resetNutrients(UserBuilder userBuilder) {
        DecimalFormat df = new DecimalFormat("0.00");
        NutrientsRequirementCalculator nutrientsCalculated = new HarrisBenedictCalculator();
        Nutrients nutrients = nutrientsCalculated.calculate(userBuilder.getHeight(), userBuilder.getWeight(), new AgeCalculator().age(userBuilder.getDateOfBirth()), userBuilder.getSex(), userBuilder.getLifeStyle(), userBuilder.getWeightVariationGoal());
        txtCalories.setText(df.format(nutrients.getCalories()));
        tfProteins.setText(df.format(nutrients.getProteins()));
        tfCarbs.setText(df.format(nutrients.getCarbs()));
        tfFats.setText(df.format(nutrients.getFats()));
        // save also to newNutrientsTmp
        nutrientsBuilder.setComplexCarbs(StringToNumber.convertPositiveFloat(tfCarbs.getText()));
        nutrientsBuilder.setUnSaturatedFats(StringToNumber.convertPositiveFloat(tfFats.getText()));
        nutrientsBuilder.setProteins(StringToNumber.convertPositiveFloat(tfProteins.getText()));
    }

    @Override
    public void showPage() {
        resetNutrients(userBuilder);
        new RegisterChangeTheme(txtChangeTheme);
        super.showPage();
    }
}
