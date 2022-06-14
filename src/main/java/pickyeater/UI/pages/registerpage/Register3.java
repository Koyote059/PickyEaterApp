package pickyeater.UI.pages.registerpage;

import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.themes.ColorButtons;
import pickyeater.UI.themes.filehandler.ThemeHandler;
import pickyeater.UI.themes.filehandler.ThemesEnum;
import pickyeater.basics.user.WeightGoal;
import pickyeater.executors.user.RegisterExecutor;
import pickyeater.utils.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

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

    public Register3(RegisterExecutor registerExecutor, JFrame parent) {
        super(parent);
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
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
        btBack.addComponentListener(new ComponentAdapter() {
        });
        btBack.addActionListener(actionEvent -> RegisterMainFrame.changePage(2));
        btLoseWeight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ColorButtons.ColorButtonWhite(btGainWeight);
                ColorButtons.ColorButtonWhite(btMaintainWeight);
                ColorButtons.ColorButtonGreen(btLoseWeight);
            }
        });
        btGainWeight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ColorButtons.ColorButtonGreen(btGainWeight);
                ColorButtons.ColorButtonWhite(btMaintainWeight);
                ColorButtons.ColorButtonWhite(btLoseWeight);
            }
        });
        btMaintainWeight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ColorButtons.ColorButtonWhite(btGainWeight);
                ColorButtons.ColorButtonGreen(btMaintainWeight);
                ColorButtons.ColorButtonWhite(btLoseWeight);
            }
        });
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                ColorButtons.ColorButtonWhite(btGainWeight);
                ColorButtons.ColorButtonWhite(btMaintainWeight);
                ColorButtons.ColorButtonWhite(btLoseWeight);
            }
        };
        btGainWeight.addMouseListener(listener);
        btMaintainWeight.addMouseListener(listener);
        btLoseWeight.addMouseListener(listener);
        txtChangeTheme.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (Objects.equals(ThemeHandler.ReadTheme(), ThemesEnum.DARK_THEME)) {
                    ThemeHandler.ChangeTheme(ThemesEnum.LIGHT_THEME);
                    draw();
                } else {
                    ThemeHandler.ChangeTheme(ThemesEnum.DARK_THEME);
                    draw();
                }
                SwingUtilities.updateComponentTreeUI(parent);
            }
        });
    }

    private void next(RegisterExecutor registerExecutor) {
        if (registerExecutor.getUserBuilder().getWeightVariationGoal() != null) {
            RegisterMainFrame.changePage(4);
        }
    }

    private void draw() {
        txtLW.setText("");
        txtMW.setText("");
        txtGW.setText("");
        try {
            BufferedImage imgS = ImageIO.read(ClassLoader.getSystemResourceAsStream(Resources.getLoseWeightPic()));
            txtLW.setIcon(new ImageIcon(imgS.getScaledInstance(-1, 195, Image.SCALE_SMOOTH)));
            BufferedImage imgSA = ImageIO.read(ClassLoader.getSystemResourceAsStream(Resources.getMaintainWeightPic()));
            txtMW.setIcon(new ImageIcon(imgSA.getScaledInstance(-1, 195, Image.SCALE_SMOOTH)));
            BufferedImage imgA = ImageIO.read(ClassLoader.getSystemResourceAsStream(Resources.getGainWeightPic()));
            txtGW.setIcon(new ImageIcon(imgA.getScaledInstance(-1, 195, Image.SCALE_SMOOTH)));
        } catch (IOException | NullPointerException ignored) {
            System.out.println("Couldn't process image");
        }
        ColorButtons.ColorButtonWhite(btGainWeight);
        ColorButtons.ColorButtonWhite(btMaintainWeight);
        ColorButtons.ColorButtonWhite(btLoseWeight);
        new RegisterChangeTheme(txtChangeTheme);
    }

    @Override
    public void showPage() {
        draw();
        super.showPage();
    }
}
