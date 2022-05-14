package pickyeater.UI.app.userpage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.basics.user.User;
import pickyeater.database.PickyEatersDatabase;
import pickyeater.executors.UserMealsProgressesExecutor;
import pickyeater.managers.EaterManager;
import pickyeater.managers.PickyEaterManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class UserPage extends JFrame{
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JButton editModeButton;
    private JLabel txtName;
    private JLabel txtHeight;
    private JLabel txtWeight;
    private JLabel txtBodyFat;
    private JLabel txtSex;
    private JLabel txtDateOfBirth;
    private JLabel txtLifestyle;
    private JLabel txtWeightGoal;
    private JLabel txtProteins;
    private JLabel txtCarbs;
    private JLabel txtFats;
    private JLabel txtCalories;
    UserMealsProgressesExecutor userMealsProgressesExecutor;

    public UserPage(PickyEatersDatabase databases) {
        EaterManager eaterManager = new PickyEaterManager(databases.getUserDatabase(),
                databases.getIngredientsDatabase(), databases.getMealsDatabase());


        Optional<User> userOptional = eaterManager.getUserManager().getUser();

        User user = userOptional.get();

        // User:
        txtName.setText(user.getName());
        txtSex.setText(user.getUserStatus().getSex().toString());
        txtDateOfBirth.setText(user.getUserStatus().getDateOfBirth().toString());
        txtHeight.setText(Double.toString(user.getUserStatus().getHeight()));
        txtWeight.setText(Double.toString(user.getUserStatus().getWeight()));
        txtBodyFat.setText(Double.toString(user.getUserStatus().getBodyFat()));
        txtLifestyle.setText(user.getUserGoal().getLifeStyle().toString());
        txtWeightGoal.setText(user.getUserGoal().getWeightVariationGoal().toString());

        // Nutrients:
        txtProteins.setText(Double.toString(user.getUserGoal().getRequiredNutrients().getProteins()));
        txtCarbs.setText(Double.toString(user.getUserGoal().getRequiredNutrients().getCarbs()));
        txtFats.setText(Double.toString(user.getUserGoal().getRequiredNutrients().getFats()));
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
        editModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new UserEditModePage(databases);
            }
        });
    }
}
