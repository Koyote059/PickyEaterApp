package pickyeater.UI.app.userpage;

/**
 * @author Claudio Di Maio
 */

import com.toedter.calendar.JDateChooser;
import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtons;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.algorithms.BodyFatCalculator;
import pickyeater.algorithms.DeurenbergCalculator;
import pickyeater.algorithms.HarrisBenedictCalculator;
import pickyeater.algorithms.NutrientsRequirementCalculator;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.user.*;
import pickyeater.builders.NutrientsBuilder;
import pickyeater.builders.PickyNutrientsBuilder;
import pickyeater.builders.PickyUserBuilder;
import pickyeater.builders.UserBuilder;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.UserEditModeExecutor;
import pickyeater.utils.AgeCalculator;
import pickyeater.utils.JCalendarToLocalDate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.Date;

public class UserEditModePage extends JFrame {
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JTextField tfName;
    private JComboBox cbLifestyle;
    private JButton btSave;
    private JButton btUndo;
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

    public UserEditModePage() {
        UserEditModeExecutor userEditModeExecutor = ExecutorProvider.getUserEditModeExecutor();
        User user = userEditModeExecutor.getUser();
        UserBuilder newUserBuilder = new PickyUserBuilder();
        NutrientsBuilder newNutrientsBuilder = new PickyNutrientsBuilder();
        // User:
        tfName.setText(user.getName());
        jBirthdayChooser.setDate(new Date(user.getUserStatus().getDateOfBirth().getYear(), user.getUserStatus().getDateOfBirth().getMonthValue(), user.getUserStatus().getDateOfBirth().getDayOfMonth()));
        tfHeight.setText(Integer.toString(user.getUserStatus().getHeight()));
        tfWeight.setText(Double.toString(user.getUserStatus().getWeight()));
        tfBodyfat.setText(Double.toString(user.getUserStatus().getBodyFat()));
        // tfSex.setText(user.getUserStatus().getSex().toString());
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
        } else if (user.getUserGoal().getWeightVariationGoal() == WeightGoal.MANTAIN_WEIGHT) {
            cbWeightGoal.setSelectedIndex(1);
            newUserBuilder.setWeightVariationGoal(WeightGoal.MANTAIN_WEIGHT);
        } else if (user.getUserGoal().getWeightVariationGoal() == WeightGoal.INCREASE_WEIGHT) {
            cbWeightGoal.setSelectedIndex(2);
            newUserBuilder.setWeightVariationGoal(WeightGoal.INCREASE_WEIGHT);
        }
        // Nutrients:
        tfProteins.setText(Double.toString(user.getUserGoal().getRequiredNutrients().getProteins()));
        tfCarbs.setText(Double.toString(user.getUserGoal().getRequiredNutrients().getCarbs()));
        tfFats.setText(Double.toString(user.getUserGoal().getRequiredNutrients().getFats()));
        txtCalories.setText(Double.toString(user.getUserGoal().getRequiredNutrients().getCalories()));
        btDailyProgress.setBackground(Color.white);
        btDiet.setBackground(Color.white);
        btFood.setBackground(Color.white);
        btGroceries.setBackground(Color.white);
        btUser.setBackground(Color.green);
        btSettings.setBackground(Color.white);
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                setVisible(false);
                new MainButton(new PanelButtonsConverter(cmd).Convert());
            }
        };
        btSettings.addActionListener(listener);
        btDailyProgress.addActionListener(listener);
        btUser.addActionListener(listener);
        btGroceries.addActionListener(listener);
        btFood.addActionListener(listener);
        btDiet.addActionListener(listener);
        // Birthday
        jBirthdayChooser.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                newUserBuilder.setDateOfBirth(new JCalendarToLocalDate().jCalendarToLocalDate(propertyChangeEvent.getNewValue()));
                if (LocalDate.now().compareTo(newUserBuilder.getDateOfBirth()) <= 0) {   //TODO: If a person is older than 150 years old -> null
                    newUserBuilder.setDateOfBirth(null);
                }
            }
        });
        btUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new MainButton(PanelButtons.USER);
            }
        });
        btSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (update(newUserBuilder)){
                    next(newUserBuilder);
                }
            }
        });
        cbLifestyle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String item = cb.getSelectedItem().toString();
                if (item.equals("Sedentary")){
                    newUserBuilder.setLifeStyle(LifeStyle.SEDENTARY);
                } else if (item.equals("Slightly Active")){
                    newUserBuilder.setLifeStyle(LifeStyle.LIGHTLY_ACTIVE);
                } else if (item.equals("Active")){
                    newUserBuilder.setLifeStyle(LifeStyle.ACTIVE);
                } else if (item.equals("Very Active")){
                    newUserBuilder.setLifeStyle(LifeStyle.VERY_ACTIVE);
                } else {
                    System.out.println("Error in UserEditModePage - 1");
                }
            }
        });
        cbWeightGoal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String item = cb.getSelectedItem().toString();
                if (item.equals("Decrease Weight")){
                    newUserBuilder.setWeightVariationGoal(WeightGoal.LOSE_WEIGHT);
                } else if (item.equals("Maintain Weight")){
                    newUserBuilder.setWeightVariationGoal(WeightGoal.MANTAIN_WEIGHT);
                } else if (item.equals("Increase Weight")) {
                    newUserBuilder.setWeightVariationGoal(WeightGoal.INCREASE_WEIGHT);
                } else {
                    System.out.println("Error in UserEditModePage - 2");
                }
            }
        });
        cbSex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbSex.getSelectedIndex() == 0){
                    newUserBuilder.setSex(Sex.MALE);
                } else if (cbSex.getSelectedIndex() == 1) {
                    newUserBuilder.setSex(Sex.FEMALE);
                }
            }
        });
        btUpdateNutrients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (update(newUserBuilder)) {
                    resetNutrients(newUserBuilder);
                }
            }
        });
    }

    private boolean update(UserBuilder userBuilder) {
        // Name
        if (!tfName.getText().isEmpty()) {
            userBuilder.setName(tfName.getText());
            if (userBuilder.getName().length() > 20) {
                JOptionPane.showMessageDialog(panelOne, "Insert valid name", "Error", JOptionPane.ERROR_MESSAGE);
                userBuilder.setName(null);
            }
        } else {
            JOptionPane.showMessageDialog(panelOne, "Missing name", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        // Weight
        if (!tfWeight.getText().isEmpty()) {
            userBuilder.setWeight(Float.parseFloat(tfWeight.getText()));
            if (userBuilder.getWeight() > 800 || userBuilder.getWeight() < 10) {
                JOptionPane.showMessageDialog(panelOne, "Insert valid weight", "Error", JOptionPane.ERROR_MESSAGE);
                userBuilder.setWeight(0);
            }
        } else {
            JOptionPane.showMessageDialog(panelOne, "Missing weight", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        // Height
        if (!tfHeight.getText().isEmpty()) {
            userBuilder.setHeight(Integer.parseInt(tfHeight.getText()));
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
        // BodyFat
        if (!tfBodyfat.getText().isEmpty()) {
            userBuilder.setBodyFat(Float.parseFloat(tfBodyfat.getText()));
            if (userBuilder.getBodyFat() < 0 | userBuilder.getBodyFat() > 100) {
                JOptionPane.showMessageDialog(panelOne, "Insert valid body-fat percentage", "Error", JOptionPane.ERROR_MESSAGE);
                userBuilder.setBodyFat(0);
            } else {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    private void next(UserBuilder userBuilder){
        if (userBuilder.getName() != null && userBuilder.getSex() != null && userBuilder.getWeight() != 0 && userBuilder.getHeight() != 0 && userBuilder.getDateOfBirth() != null) {
            if (userBuilder.getBodyFat() == 0) {
                BodyFatCalculator bodyFatCalculator = new DeurenbergCalculator();
                userBuilder.setBodyFat(bodyFatCalculator.calculate(userBuilder.getHeight(), userBuilder.getWeight(),
                        new AgeCalculator().age(userBuilder.getDateOfBirth()), userBuilder.getSex()));
            }
            JOptionPane.showMessageDialog(panelOne, "Selected:" + "\n" + "Name: " + userBuilder.getName() + "\n" + "Sex: " + userBuilder.getSex() + "\n" +
                    "Height: " + userBuilder.getHeight() + "cm\n" + "Weight: " + userBuilder.getWeight() + "Kg\n" + "Birthday: " + userBuilder.getDateOfBirth() + "\n" + "Body fat: " + userBuilder.getBodyFat() + "%");

            // TODO: take stuff from Float.parseFloat(tfCarbs.getText()) etc

            // TODO: SAVE USER

            setVisible(false);
            new MainButton(PanelButtons.USER);
        }
    }
    private void resetNutrients(UserBuilder userBuilder){
        NutrientsRequirementCalculator nutrientsCalculated = new HarrisBenedictCalculator();
        Nutrients nutrients = nutrientsCalculated.calculate(userBuilder.getHeight(),
                userBuilder.getWeight(), new AgeCalculator().age(userBuilder.getDateOfBirth()),
                userBuilder.getSex(), userBuilder.getLifeStyle());

        txtCalories.setText(Double.toString(nutrients.getCalories()));
        tfProteins.setText(Double.toString(nutrients.getProteins()));
        tfCarbs.setText(Double.toString(nutrients.getCarbs()));
        tfFats.setText(Double.toString(nutrients.getFats()));
    }

    private void createUIComponents() {
        jBirthdayChooser = new JDateChooser();
    }
}
