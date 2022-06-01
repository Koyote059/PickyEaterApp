package pickyeater.UI.pages.registerpage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.themes.filehandler.ThemeHandler;
import pickyeater.UI.themes.filehandler.ThemesEnum;
import pickyeater.basics.user.WeightGoal;
import pickyeater.executors.user.RegisterExecutor;
import pickyeater.UI.themes.ColorButtons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Register3 extends PickyPage {
    private JButton btLoseWeight;
    private JButton btGainWeight;
    private JButton btMaintainWeight;
    private JPanel mainPanel;
    private JButton btBack;
    private JLabel txtChangeTheme;
    private JLabel txtMW;
    private JLabel txtLW;
    private JLabel txtGW;

    public Register3(RegisterExecutor registerExecutor,JFrame parent) {
        super(parent);
        //setSize(677, 507);    //pack();
        //setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2 - 677/2,Toolkit.getDefaultToolkit().getScreenSize().height/2 - 507/2);


        ColorButtons cB = new ColorButtons();
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
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
        btBack.addActionListener(actionEvent -> RegisterMainFrame.changePage(2));
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

                SwingUtilities.updateComponentTreeUI(parent);
            }
        });
    }
    private void next(RegisterExecutor registerExecutor){
        if (registerExecutor.getUserBuilder().getWeightVariationGoal() != null){
            RegisterMainFrame.changePage(4);
        }
    }

    private void draw() {
        txtLW.setText("");
        txtMW.setText("");
        txtGW.setText("");
        try {
            BufferedImage imgS = ImageIO.read(new File("res/images/goal/LW.png"));
            txtLW.setIcon(new ImageIcon(imgS.getScaledInstance(-1, 90, Image.SCALE_SMOOTH)));
            BufferedImage imgSA = ImageIO.read(new File("res/images/goal/MW.png"));
            txtMW.setIcon(new ImageIcon(imgSA.getScaledInstance(-1, 90, Image.SCALE_SMOOTH)));
            BufferedImage imgA = ImageIO.read(new File("res/images/goal/GW.png"));
            txtGW.setIcon(new ImageIcon(imgA.getScaledInstance(-1, 90, Image.SCALE_SMOOTH)));
        } catch (IOException | NullPointerException ignored) {
            System.out.println("Couldn't process iamge");
        }

        ColorButtons cB = new ColorButtons();
        cB.ColorButtonWhite(btGainWeight);
        cB.ColorButtonWhite(btMaintainWeight);
        cB.ColorButtonWhite(btLoseWeight);

        new RegisterChangeTheme(txtChangeTheme);
    }

    @Override
    public void showPage() {
        draw();
        super.showPage();
    }
}
