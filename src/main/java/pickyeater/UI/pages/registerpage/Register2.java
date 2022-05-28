package pickyeater.UI.pages.registerpage;

/**
 * @author Claudio Di Maio
 */
import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.themes.filehandler.ThemeHandler;
import pickyeater.UI.themes.filehandler.ThemesEnum;
import pickyeater.basics.mealplan.PickyMealPlan;
import pickyeater.basics.user.LifeStyle;
import pickyeater.executors.user.RegisterExecutor;
import pickyeater.UI.themes.ColorButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Register2 extends PickyPage {
    private JButton btSedentary;
    private JButton btVeryActive;
    private JButton btSlightlyActive;
    private JButton btActive;
    private JPanel mainPanel;
    private JButton btBack;
    private JLabel txtChangeTheme;

    public Register2(RegisterExecutor registerExecutor,JFrame parent) {
        super(parent);
        //setSize(677, 507);    //pack();
        //setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2 - 677/2,Toolkit.getDefaultToolkit().getScreenSize().height/2 - 507/2);
        ColorButtons cB = new ColorButtons();
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

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
        btBack.addActionListener(actionEvent -> RegisterMainFrame.changePage(1));
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

                for (int i = 0; getComponentCount() > i; i++) {
                    SwingUtilities.updateComponentTreeUI(getComponent(i));
                }
            }
        });
    }
    private void next(RegisterExecutor registerExecutor){
        if (registerExecutor.getUserBuilder().getLifeStyle() != null){
            RegisterMainFrame.changePage(3);
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

    @Override
    public void showPage() {
        draw();
        super.showPage();
    }
}
