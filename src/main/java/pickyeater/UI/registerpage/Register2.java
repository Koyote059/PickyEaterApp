package pickyeater.UI.registerpage;

/**
 * @author Claudio Di Maio
 */
import pickyeater.basics.user.LifeStyle;
import pickyeater.executors.user.RegisterExecutor;
import pickyeater.themes.filehandler.ThemeHandler;
import pickyeater.themes.filehandler.ThemesEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Register2 extends JFrame {
    private JButton btSedentary;
    private JButton btVeryActive;
    private JButton btSlightlyActive;
    private JButton btActive;
    private JPanel mainPanel;
    private JButton btBack;

    public Register2(RegisterExecutor registerExecutor) {
        setContentPane(mainPanel);
        setSize(677, 507);    //pack();
        //setLocation(350,150);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        if (new ThemeHandler().ReadTheme() == ThemesEnum.LIGHT_THEME) {
            btSedentary.setBackground(Color.decode("#FFFFFF"));
            btSlightlyActive.setBackground(Color.decode("#FFFFFF"));
            btActive.setBackground(Color.decode("#FFFFFF"));
            btVeryActive.setBackground(Color.decode("#FFFFFF"));
        } else if (new ThemeHandler().ReadTheme() == ThemesEnum.DARK_THEME) {
            btSedentary.setBackground(Color.decode("#000000"));
            btSlightlyActive.setBackground(Color.decode("#000000"));
            btActive.setBackground(Color.decode("#000000"));
            btVeryActive.setBackground(Color.decode("#000000"));
        }

        btSedentary.addActionListener(actionEvent -> {
            registerExecutor.getUserBuilder().setLifeStyle(LifeStyle.SEDENTARY);
            next(registerExecutor);
        });
        btSlightlyActive.addActionListener(actionEvent -> {
            registerExecutor.getUserBuilder().setLifeStyle(LifeStyle.LIGHTLY_ACTIVE);

            next(registerExecutor);
        });
        btActive.addActionListener(actionEvent -> {
            registerExecutor.getUserBuilder().setLifeStyle(LifeStyle.ACTIVE);

            next(registerExecutor);
        });
        btVeryActive.addActionListener(actionEvent -> {
            registerExecutor.getUserBuilder().setLifeStyle(LifeStyle.VERY_ACTIVE);

            next(registerExecutor);
        });
    btBack.addComponentListener(new ComponentAdapter() { } );
        btBack.addActionListener(actionEvent -> {
            setVisible(false);
            new Register1();
        });
        btSedentary.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (new ThemeHandler().ReadTheme() == ThemesEnum.LIGHT_THEME) {
                    btSedentary.setBackground(Color.decode("#B1EA9D"));
                    btSlightlyActive.setBackground(Color.decode("#FFFFFF"));
                    btActive.setBackground(Color.decode("#FFFFFF"));
                    btVeryActive.setBackground(Color.decode("#FFFFFF"));
                } else if (new ThemeHandler().ReadTheme() == ThemesEnum.DARK_THEME) {
                    btSedentary.setBackground(Color.decode("#32AB5E"));
                    btSlightlyActive.setBackground(Color.decode("#000000"));
                    btActive.setBackground(Color.decode("#000000"));
                    btVeryActive.setBackground(Color.decode("#000000"));
                }
            }
        });
        btSlightlyActive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (new ThemeHandler().ReadTheme() == ThemesEnum.LIGHT_THEME) {
                    btSedentary.setBackground(Color.decode("#FFFFFF"));
                    btSlightlyActive.setBackground(Color.decode("#B1EA9D"));
                    btActive.setBackground(Color.decode("#FFFFFF"));
                    btVeryActive.setBackground(Color.decode("#FFFFFF"));
                } else if (new ThemeHandler().ReadTheme() == ThemesEnum.DARK_THEME) {
                    btSedentary.setBackground(Color.decode("#000000"));
                    btSlightlyActive.setBackground(Color.decode("#32AB5E"));
                    btActive.setBackground(Color.decode("#000000"));
                    btVeryActive.setBackground(Color.decode("#000000"));
                }
            }
        });
        btActive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (new ThemeHandler().ReadTheme() == ThemesEnum.LIGHT_THEME) {
                    btSedentary.setBackground(Color.decode("#FFFFFF"));
                    btSlightlyActive.setBackground(Color.decode("#FFFFFF"));
                    btActive.setBackground(Color.decode("#B1EA9D"));
                    btVeryActive.setBackground(Color.decode("#FFFFFF"));
                } else if (new ThemeHandler().ReadTheme() == ThemesEnum.DARK_THEME) {
                    btSedentary.setBackground(Color.decode("#000000"));
                    btSlightlyActive.setBackground(Color.decode("#000000"));
                    btActive.setBackground(Color.decode("#32AB5E"));
                    btVeryActive.setBackground(Color.decode("#000000"));
                }
            }
        });
        btVeryActive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (new ThemeHandler().ReadTheme() == ThemesEnum.LIGHT_THEME) {
                    btSedentary.setBackground(Color.decode("#FFFFFF"));
                    btSlightlyActive.setBackground(Color.decode("#FFFFFF"));
                    btActive.setBackground(Color.decode("#FFFFFF"));
                    btVeryActive.setBackground(Color.decode("#B1EA9D"));
                } else if (new ThemeHandler().ReadTheme() == ThemesEnum.DARK_THEME) {
                    btSedentary.setBackground(Color.decode("#000000"));
                    btSlightlyActive.setBackground(Color.decode("#000000"));
                    btActive.setBackground(Color.decode("#000000"));
                    btVeryActive.setBackground(Color.decode("#32AB5E"));
                }
            }
        });
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (new ThemeHandler().ReadTheme() == ThemesEnum.LIGHT_THEME) {
                    btSedentary.setBackground(Color.decode("#FFFFFF"));
                    btSlightlyActive.setBackground(Color.decode("#FFFFFF"));
                    btActive.setBackground(Color.decode("#FFFFFF"));
                    btVeryActive.setBackground(Color.decode("#FFFFFF"));
                } else if (new ThemeHandler().ReadTheme() == ThemesEnum.DARK_THEME) {
                    btSedentary.setBackground(Color.decode("#000000"));
                    btSlightlyActive.setBackground(Color.decode("#000000"));
                    btActive.setBackground(Color.decode("#000000"));
                    btVeryActive.setBackground(Color.decode("#000000"));
                }
            }
        };
        btActive.addMouseListener(listener);
        btSlightlyActive.addMouseListener(listener);
        btSedentary.addMouseListener(listener);
        btVeryActive.addMouseListener(listener);
    }
    private void next(RegisterExecutor registerExecutor){
        if (registerExecutor.getUserBuilder().getLifeStyle() != null){
            //JOptionPane.showMessageDialog(buttonPanel, "Lifestyle: " + lifeStyle);
            setVisible(false);
            new Register3(registerExecutor);
        }
    }
}
