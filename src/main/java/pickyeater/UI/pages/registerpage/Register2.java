package pickyeater.UI.pages.registerpage;

/**
 * @author Claudio Di Maio
 */
import pickyeater.UI.themes.filehandler.ThemeHandler;
import pickyeater.UI.themes.filehandler.ThemesEnum;
import pickyeater.basics.user.LifeStyle;
import pickyeater.executors.user.RegisterExecutor;
import pickyeater.UI.themes.ColorButtons;

import javax.swing.*;
import java.awt.event.*;

public class Register2 extends JFrame {
    private JButton btSedentary;
    private JButton btVeryActive;
    private JButton btSlightlyActive;
    private JButton btActive;
    private JPanel mainPanel;
    private JButton btBack;
    private JLabel txtChangeTheme;

    public Register2(RegisterExecutor registerExecutor) {
        setContentPane(mainPanel);
        setSize(677, 507);    //pack();
        //setLocation(350,150);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        ColorButtons cB = new ColorButtons();

        draw();

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
            new Register1();
            setVisible(false);
        });
        btSedentary.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                cB.ColorButtonGreen(btSedentary);
                cB.ColorButtonWhite(btSlightlyActive);
                cB.ColorButtonWhite(btActive);
                cB.ColorButtonWhite(btVeryActive);
            }
        });
        btSlightlyActive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                cB.ColorButtonWhite(btSedentary);
                cB.ColorButtonGreen(btSlightlyActive);
                cB.ColorButtonWhite(btActive);
                cB.ColorButtonWhite(btVeryActive);
            }
        });
        btActive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                cB.ColorButtonWhite(btSedentary);
                cB.ColorButtonWhite(btSlightlyActive);
                cB.ColorButtonGreen(btActive);
                cB.ColorButtonWhite(btVeryActive);
            }
        });
        btVeryActive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                cB.ColorButtonWhite(btSedentary);
                cB.ColorButtonWhite(btSlightlyActive);
                cB.ColorButtonWhite(btActive);
                cB.ColorButtonGreen(btVeryActive);
            }
        });
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                cB.ColorButtonWhite(btSedentary);
                cB.ColorButtonWhite(btSlightlyActive);
                cB.ColorButtonWhite(btActive);
                cB.ColorButtonWhite(btVeryActive);
            }
        };
        btActive.addMouseListener(listener);
        btSlightlyActive.addMouseListener(listener);
        btSedentary.addMouseListener(listener);
        btVeryActive.addMouseListener(listener);
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
        if (registerExecutor.getUserBuilder().getLifeStyle() != null){
            new Register3(registerExecutor);
            setVisible(false);
        }
    }

    private void draw(){
        ColorButtons cB = new ColorButtons();
        cB.ColorButtonWhite(btSedentary);
        cB.ColorButtonWhite(btSlightlyActive);
        cB.ColorButtonWhite(btActive);
        cB.ColorButtonWhite(btVeryActive);

        new RegisterChangeTheme(txtChangeTheme);
    }
}
