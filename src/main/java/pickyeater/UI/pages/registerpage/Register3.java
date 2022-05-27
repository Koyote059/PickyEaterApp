package pickyeater.UI.pages.registerpage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.themes.filehandler.ThemeHandler;
import pickyeater.UI.themes.filehandler.ThemesEnum;
import pickyeater.basics.user.WeightGoal;
import pickyeater.executors.user.RegisterExecutor;
import pickyeater.UI.themes.ColorButtons;

import javax.swing.*;
import java.awt.event.*;

public class Register3 extends JFrame {
    private JButton btLoseWeight;
    private JButton btGainWeight;
    private JButton btMaintainWeight;
    private JPanel mainPanel;
    private JButton btBack;
    private JLabel txtChangeTheme;

    public Register3(RegisterExecutor registerExecutor) {
        setContentPane(mainPanel);
        setSize(677, 507);    //pack();
        //setLocation(350,150);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ColorButtons cB = new ColorButtons();
        draw();

        btLoseWeight.addActionListener(actionEvent -> {
            registerExecutor.getUserBuilder().setWeightVariationGoal(WeightGoal.LOSE_WEIGHT);

            next(registerExecutor);
        });
        btGainWeight.addActionListener(actionEvent -> {
            registerExecutor.getUserBuilder().setWeightVariationGoal(WeightGoal.INCREASE_WEIGHT);

            next(registerExecutor);
        });
        btMaintainWeight.addActionListener(actionEvent -> {
            registerExecutor.getUserBuilder().setWeightVariationGoal(WeightGoal.MAINTAIN_WEIGHT);

            next(registerExecutor);
        });

    btBack.addComponentListener(new ComponentAdapter() { } );
        btBack.addActionListener(actionEvent -> {
            new Register2(registerExecutor);
            setVisible(false);
        });
        btLoseWeight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                cB.ColorButtonWhite(btGainWeight);
                cB.ColorButtonWhite(btMaintainWeight);
                cB.ColorButtonGreen(btLoseWeight);
            }
        });
        btGainWeight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                cB.ColorButtonGreen(btGainWeight);
                cB.ColorButtonWhite(btMaintainWeight);
                cB.ColorButtonWhite(btLoseWeight);
            }
        });
        btMaintainWeight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                cB.ColorButtonWhite(btGainWeight);
                cB.ColorButtonGreen(btMaintainWeight);
                cB.ColorButtonWhite(btLoseWeight);
            }
        });
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                cB.ColorButtonWhite(btGainWeight);
                cB.ColorButtonWhite(btMaintainWeight);
                cB.ColorButtonWhite(btLoseWeight);
            }
        };
        btGainWeight.addMouseListener(listener);
        btMaintainWeight.addMouseListener(listener);
        btLoseWeight.addMouseListener(listener);
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
    private void next(RegisterExecutor registerExecutor){
        if (registerExecutor.getUserBuilder().getWeightVariationGoal() != null){
            new Register4(registerExecutor);
            setVisible(false);
        }
    }

    private void draw(){
        ColorButtons cB = new ColorButtons();
        cB.ColorButtonWhite(btGainWeight);
        cB.ColorButtonWhite(btMaintainWeight);
        cB.ColorButtonWhite(btLoseWeight);

        new RegisterChangeTheme(txtChangeTheme);
    }
}
