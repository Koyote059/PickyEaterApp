package pickyeater.UI.registerpage;

/**
 * @author Claudio Di Maio
 */
import pickyeater.basics.user.LifeStyle;
import pickyeater.executors.user.RegisterExecutor;
import pickyeater.themes.ColorButtons;
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
        ColorButtons cB = new ColorButtons();

        cB.ColorButtonWhite(btSedentary);
        cB.ColorButtonWhite(btSlightlyActive);
        cB.ColorButtonWhite(btActive);
        cB.ColorButtonWhite(btVeryActive);

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
    }
    private void next(RegisterExecutor registerExecutor){
        if (registerExecutor.getUserBuilder().getLifeStyle() != null){
            setVisible(false);
            new Register3(registerExecutor);
        }
    }
}
