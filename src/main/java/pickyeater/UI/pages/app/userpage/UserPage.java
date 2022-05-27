package pickyeater.UI.pages.app.userpage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.pages.leftbuttons.PanelButtonsConverter;
import pickyeater.basics.user.User;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.user.UserExecutor;
import pickyeater.UI.themes.ColorButtons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class UserPage extends PickyPage {
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btDiet;
    private JButton btEditMode;
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

    public UserPage(JFrame parent) {
        super(parent);
        UserExecutor userExecutor = ExecutorProvider.getUserExecutor();

        User user = userExecutor.getUser();

        DecimalFormat df = new DecimalFormat("0.00");

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        btEditMode.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        try {
            BufferedImage binImage = ImageIO.read(new File("res/images/accounteditB.png"));
            btEditMode.setIcon(new ImageIcon(binImage.getScaledInstance(40,40,Image.SCALE_SMOOTH)));
            btEditMode.setText("");
        } catch (IOException | NullPointerException ignored) {
        }

        // User:
        txtName.setText(user.getName());
        txtSex.setText(user.getUserStatus().getSex().toString());
        txtDateOfBirth.setText(user.getUserStatus().getDateOfBirth().toString());
        txtHeight.setText(df.format(user.getUserStatus().getHeight()));
        txtWeight.setText(df.format(user.getUserStatus().getWeight()));
        txtBodyFat.setText(df.format(user.getUserStatus().getBodyFat()));
        txtLifestyle.setText(user.getUserGoal().getLifeStyle().toString());
        txtWeightGoal.setText(user.getUserGoal().getWeightVariationGoal().toString());

        // Nutrients:
        txtProteins.setText(df.format(user.getUserGoal().getRequiredNutrients().getProteins()));
        txtCarbs.setText(df.format(user.getUserGoal().getRequiredNutrients().getCarbs()));
        txtFats.setText(df.format(user.getUserGoal().getRequiredNutrients().getFats()));
        txtCalories.setText(df.format(user.getUserGoal().getRequiredNutrients().getCalories()));

        new ColorButtons().ColorLeftButtons(btUser, btDailyProgress, btSettings, btGroceries, btDiet);

        btEditMode.addActionListener(e -> {
            PickyPage userEditModePage = new UserEditModePage(parent);
            userEditModePage.showPage();
        });

        setNavigationMenuListeners();
    }

    public void setNavigationMenuListeners(){
        ActionListener listener = e -> {
            String cmd = e.getActionCommand();
            setVisible(false);
            MainFrame.changePage(new PanelButtonsConverter(cmd).Convert());
        };

        btSettings.addActionListener(listener);
        btDailyProgress.addActionListener(listener);
        btGroceries.addActionListener(listener);
        btDiet.addActionListener(listener);
    }

}
