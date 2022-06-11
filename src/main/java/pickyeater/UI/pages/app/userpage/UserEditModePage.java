package pickyeater.UI.pages.app.userpage;

import com.toedter.calendar.JDateChooser;
import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.pages.leftbuttons.PanelButtons;
import pickyeater.UI.pages.leftbuttons.PanelButtonsConverter;
import pickyeater.UI.themes.ColorButtons;
import pickyeater.algorithms.BodyFatCalculator;
import pickyeater.algorithms.DeurenbergCalculator;
import pickyeater.algorithms.HarrisBenedictCalculator;
import pickyeater.algorithms.NutrientsRequirementCalculator;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.*;
import pickyeater.builders.NutrientsBuilder;
import pickyeater.builders.PickyNutrientsBuilder;
import pickyeater.builders.PickyUserBuilder;
import pickyeater.builders.UserBuilder;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.user.UserEditModeExecutor;
import pickyeater.utils.AgeCalculator;
import pickyeater.utils.JCalUtils;
import pickyeater.utils.StringToNumber;
import pickyeater.utils.StringsUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class UserEditModePage extends PickyPage {
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btDiet;
    private JTextField tfName;
    private JComboBox cbLifestyle;
    private JButton btSave;
    private JButton btCancel;
    private JTextField tfHeight;
    private JTextField tfWeight;
    private JTextField tfBodyfat;
    private JComboBox cbWeightGoal;
    private JTextField tfProteins;
    private JTextField tfCarbs;
    private JTextField tfFats;
    private JLabel txtCalories;
    private JPanel birthdayPanel;
    private JDateChooser jBirthdayChooser;
    private JComboBox cbSex;
    private JPanel panelOne;
    private JButton btUpdateNutrients;

    public UserEditModePage(JFrame parent) {
        super(parent);
        UserEditModeExecutor userEditModeExecutor = ExecutorProvider.getUserEditModeExecutor();
        User user = userEditModeExecutor.getUser();
        UserBuilder newUserBuilder = new PickyUserBuilder();
        btUpdateNutrients.setToolTipText("It'll automatically calculate the nutrients using your stats");
        try {
            BufferedImage binImage = ImageIO.read(new File("res/images/updateIcon.png"));
            btUpdateNutrients.setIcon(new ImageIcon(binImage.getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
        } catch (IOException | NullPointerException ignored) {
        }
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        // FROM LOCALDATE TO DATE
        ZoneId defaultZoneId = ZoneId.systemDefault();
        DecimalFormat df = new DecimalFormat("0.000");
        // User:
        tfName.setText(user.getName());
        jBirthdayChooser.setDate(Date.from(user.getUserStatus().getDateOfBirth().atStartOfDay(defaultZoneId).toInstant()));
        tfHeight.setText(Integer.toString(user.getUserStatus().getHeight()));
        tfWeight.setText(df.format(user.getUserStatus().getWeight()));
        tfBodyfat.setText(df.format(user.getUserStatus().getBodyFat()));

        if(user.getMealPlan().isPresent()){
            newUserBuilder.setMealPlan(user.getMealPlan().get());
        }

        DailyProgresses dailyProgresses = user.getDailyProgresses();
        Collection<Meal> eatenMeals = dailyProgresses.getEatenMeals();
        int burntCalories = dailyProgresses.getBurnedCalories();
        newUserBuilder.setDailyProgresses(eatenMeals,burntCalories);

        if (user.getUserStatus().getSex() == Sex.MALE) {
            cbSex.setSelectedIndex(0);
            newUserBuilder.setSex(Sex.MALE);
        } else if (user.getUserStatus().getSex() == Sex.FEMALE) {
            cbSex.setSelectedIndex(1);
            newUserBuilder.setSex(Sex.FEMALE);
        }
        if (user.getUserGoal().getLifeStyle() == LifeStyle.SEDENTARY) {
            cbLifestyle.setSelectedIndex(0);
            newUserBuilder.setLifeStyle(LifeStyle.SEDENTARY);
        } else if (user.getUserGoal().getLifeStyle() == LifeStyle.LIGHTLY_ACTIVE) {
            cbLifestyle.setSelectedIndex(1);
            newUserBuilder.setLifeStyle(LifeStyle.LIGHTLY_ACTIVE);
        } else if (user.getUserGoal().getLifeStyle() == LifeStyle.ACTIVE) {
            cbLifestyle.setSelectedIndex(2);
            newUserBuilder.setLifeStyle(LifeStyle.ACTIVE);
        } else if (user.getUserGoal().getLifeStyle() == LifeStyle.VERY_ACTIVE) {
            cbLifestyle.setSelectedIndex(3);
            newUserBuilder.setLifeStyle(LifeStyle.VERY_ACTIVE);
        }
        if (user.getUserGoal().getWeightVariationGoal() == WeightGoal.LOSE_WEIGHT) {
            cbWeightGoal.setSelectedIndex(0);
            newUserBuilder.setWeightVariationGoal(WeightGoal.LOSE_WEIGHT);
        } else if (user.getUserGoal().getWeightVariationGoal() == WeightGoal.MAINTAIN_WEIGHT) {
            cbWeightGoal.setSelectedIndex(1);
            newUserBuilder.setWeightVariationGoal(WeightGoal.MAINTAIN_WEIGHT);
        } else if (user.getUserGoal().getWeightVariationGoal() == WeightGoal.INCREASE_WEIGHT) {
            cbWeightGoal.setSelectedIndex(2);
            newUserBuilder.setWeightVariationGoal(WeightGoal.INCREASE_WEIGHT);
        }
        // Nutrients:
        tfProteins.setText(df.format(user.getUserGoal().getRequiredNutrients().getProteins()));
        tfCarbs.setText(df.format(user.getUserGoal().getRequiredNutrients().getCarbs()));
        tfFats.setText(df.format(user.getUserGoal().getRequiredNutrients().getFats()));
        txtCalories.setText(df.format(user.getUserGoal().getRequiredNutrients().getCalories()));
        ColorButtons.ColorLeftButtons(btUser, btDailyProgress, btSettings, btGroceries, btDiet);
        newUserBuilder.setDateOfBirth(JCalUtils.jCalToLocDate(jBirthdayChooser.getDate()));
        ActionListener listener = e -> {
            String cmd = e.getActionCommand();
            setVisible(false);
            MainFrame.changePage(new PanelButtonsConverter(cmd).Convert());
        };
        btSettings.addActionListener(listener);
        btDailyProgress.addActionListener(listener);
        btUser.addActionListener(listener);
        btGroceries.addActionListener(listener);
        btDiet.addActionListener(listener);
        // Birthday
        jBirthdayChooser.addPropertyChangeListener(propertyChangeEvent -> {
            newUserBuilder.setDateOfBirth(JCalUtils.jCalToLocDate(jBirthdayChooser.getDate()));
            if (LocalDate.now().compareTo(newUserBuilder.getDateOfBirth()) <= 0) {   //TODO: If a person is older than 150 years old -> null
                newUserBuilder.setDateOfBirth(null);
            }
        });
        btCancel.addActionListener(e -> MainFrame.changePage(PanelButtons.USER));
        btSave.addActionListener(e -> {
            if (update(newUserBuilder)) {
                next(newUserBuilder);
            }
        });
        cbLifestyle.addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            String item = cb.getSelectedItem().toString();
            switch (item) {
                case "Sedentary" -> newUserBuilder.setLifeStyle(LifeStyle.SEDENTARY);
                case "Slightly Active" -> newUserBuilder.setLifeStyle(LifeStyle.LIGHTLY_ACTIVE);
                case "Active" -> newUserBuilder.setLifeStyle(LifeStyle.ACTIVE);
                case "Very Active" -> newUserBuilder.setLifeStyle(LifeStyle.VERY_ACTIVE);
                default -> System.out.println("Error in UserEditModePage - 1");
            }
        });
        cbWeightGoal.addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            String item = cb.getSelectedItem().toString();
            switch (item) {
                case "Decrease Weight" -> newUserBuilder.setWeightVariationGoal(WeightGoal.LOSE_WEIGHT);
                case "Maintain Weight" -> newUserBuilder.setWeightVariationGoal(WeightGoal.MAINTAIN_WEIGHT);
                case "Increase Weight" -> newUserBuilder.setWeightVariationGoal(WeightGoal.INCREASE_WEIGHT);
                default -> System.out.println("Error in UserEditModePage - 2");
            }
        });
        cbSex.addActionListener(e -> {
            switch (cbSex.getSelectedIndex()) {
                case 0 -> newUserBuilder.setSex(Sex.MALE);
                case 1 -> newUserBuilder.setSex(Sex.FEMALE);
            }
        });
        btUpdateNutrients.addActionListener(e -> {
            if (update(newUserBuilder)) {
                resetNutrients(newUserBuilder);
            }
        });
        setNavigationMenuListeners();
    }

    private boolean update(UserBuilder userBuilder) {
        // TODO -> Put update in the executor (it will return 1, 2, 3, 4 ... if it's an error, 0 otherwise
        // Name
        if (!tfName.getText().isEmpty()) {
            userBuilder.setName(tfName.getText());
            if (userBuilder.getName().length() > 20 || !StringsUtils.isAlpha(userBuilder.getName())) {
                JOptionPane.showMessageDialog(panelOne, "Insert valid name", "Error", JOptionPane.ERROR_MESSAGE);
                userBuilder.setName(null);
            }
        } else {
            JOptionPane.showMessageDialog(panelOne, "Missing name", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        // Weight
        if (!tfWeight.getText().isEmpty()) {
            userBuilder.setWeight(StringToNumber.convertPositiveFloat(tfWeight.getText()));
            if (userBuilder.getWeight() > 800 || userBuilder.getWeight() < 10) {
                JOptionPane.showMessageDialog(panelOne, "Insert valid weight", "Error", JOptionPane.ERROR_MESSAGE);
                userBuilder.setWeight(0);
            }
        } else {
            JOptionPane.showMessageDialog(panelOne, "Missing weight", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        // Height
        if (!tfHeight.getText().isEmpty()) {
            userBuilder.setHeight(StringToNumber.convertPositiveInteger(tfHeight.getText()));
            if (userBuilder.getHeight() > 300 || userBuilder.getHeight() < 10) {
                JOptionPane.showMessageDialog(panelOne, "Insert valid height", "Error", JOptionPane.ERROR_MESSAGE);
                userBuilder.setHeight(0);
            }
        } else {
            JOptionPane.showMessageDialog(panelOne, "Missing height", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        // Birthday
        if (userBuilder.getDateOfBirth() == null) {
            JOptionPane.showMessageDialog(panelOne, "Insert valid birthday", "Error", JOptionPane.ERROR_MESSAGE);
        }
        // Sex
        if (userBuilder.getSex() == null) {
            JOptionPane.showMessageDialog(panelOne, "Missing sex", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        if (userBuilder.getName() == null | userBuilder.getSex() == null | userBuilder.getWeight() == 0 | userBuilder.getHeight() == 0 | userBuilder.getDateOfBirth() == null) {
            return false;
        }
        // BodyFat
        if (!tfBodyfat.getText().isEmpty()) {
            userBuilder.setBodyFat(StringToNumber.convertPositiveFloat(tfBodyfat.getText()));
            if (userBuilder.getBodyFat() <= 0 | userBuilder.getBodyFat() > 100) {
                JOptionPane.showMessageDialog(panelOne, "Insert valid body-fat percentage", "Error", JOptionPane.ERROR_MESSAGE);
                userBuilder.setBodyFat(0);
            } else {
                return true;
            }
        } else {
            BodyFatCalculator bodyFatCalculator = new DeurenbergCalculator();
            userBuilder.setBodyFat(bodyFatCalculator.calculate(userBuilder.getHeight(), userBuilder.getWeight(), new AgeCalculator().age(userBuilder.getDateOfBirth()), userBuilder.getSex()));
            return true;
        }
        return false;
    }

    private void next(UserBuilder userBuilder) {
        //System.out.println(userBuilder.getBodyFat());
        NutrientsBuilder newNutrientsBuilder = new PickyNutrientsBuilder();
        newNutrientsBuilder.setComplexCarbs(StringToNumber.convertPositiveFloat(tfCarbs.getText()));
        newNutrientsBuilder.setUnSaturatedFats(StringToNumber.convertPositiveFloat(tfFats.getText()));
        newNutrientsBuilder.setProteins(StringToNumber.convertPositiveFloat(tfProteins.getText()));
        userBuilder.setRequiredNutrients(newNutrientsBuilder.build());
        /*
        JOptionPane.showMessageDialog(panelOne, "Selected:" + "\n" + "Name: " + userBuilder.getName() + "\n" + "Sex: " + userBuilder.getSex() + "\n" + "Height: " + userBuilder.getHeight() + "cm\n" + "Weight: " + userBuilder.getWeight() + "Kg\n" + "Birthday: " + userBuilder.getDateOfBirth() + "\n" + "Body fat: " + userBuilder.getBodyFat() + "%");
        JOptionPane.showMessageDialog(panelOne, "Nutrients:\n" + "Proteins: " + userBuilder.getRequiredNutrients().getProteins() + "\nCarbs: " + userBuilder.getRequiredNutrients().getCarbs() + "\nFats: " + userBuilder.getRequiredNutrients().getFats() + "\nCalories: " + userBuilder.getRequiredNutrients().getCalories());
         */
        // Save user
        ExecutorProvider.getUserEditModeExecutor().saveUser(userBuilder.build());
        new MainFrame();
        MainFrame.changePage(PanelButtons.USER);
        parent.dispose();
    }

    private void resetNutrients(UserBuilder userBuilder) {
        NutrientsRequirementCalculator nutrientsCalculated = new HarrisBenedictCalculator();
        Nutrients nutrients = nutrientsCalculated.calculate(userBuilder.getHeight(), userBuilder.getWeight(), new AgeCalculator().age(userBuilder.getDateOfBirth()), userBuilder.getSex(), userBuilder.getLifeStyle(), userBuilder.getWeightVariationGoal());
        DecimalFormat df = new DecimalFormat("0.000");
        txtCalories.setText(df.format(nutrients.getCalories()));
        tfProteins.setText(df.format(nutrients.getProteins()));
        tfCarbs.setText(df.format(nutrients.getCarbs()));
        tfFats.setText(df.format(nutrients.getFats()));
    }

    public void setNavigationMenuListeners() {
        ActionListener listener = e -> {
            String cmd = e.getActionCommand();
            setVisible(false);
            MainFrame.changePage(new PanelButtonsConverter(cmd).Convert());
        };
        btSettings.addActionListener(listener);
        btDailyProgress.addActionListener(listener);
        btUser.addActionListener(listener);
        btGroceries.addActionListener(listener);
        btDiet.addActionListener(listener);
    }

    private void createUIComponents() {
        jBirthdayChooser = new JDateChooser();
    }
}
