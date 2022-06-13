package pickyeater.UI.pages.registerpage;

import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.themes.ColorButtons;
import pickyeater.UI.themes.filehandler.ThemeHandler;
import pickyeater.UI.themes.filehandler.ThemesEnum;
import pickyeater.basics.user.LifeStyle;
import pickyeater.executors.user.RegisterExecutor;
import pickyeater.utils.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Register2 extends PickyPage {
    private JButton btSedentary;
    private JButton btVeryActive;
    private JButton btSlightlyActive;
    private JButton btActive;
    private JPanel mainPanel;
    private JButton btBack;
    private JLabel txtChangeTheme;
    private JLabel txtS;
    private JLabel txtSA;
    private JLabel txtA;
    private JLabel txtVA;

    public Register2(RegisterExecutor registerExecutor, JFrame parent) {
        super(parent);
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
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
        btBack.addActionListener(e -> {
            registerExecutor.getUserBuilder().setBodyFat(0);
            RegisterMainFrame.changePage(1);
        });
        btSedentary.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ColorButtons.ColorButtonGreen(btSedentary);
                ColorButtons.ColorButtonWhite(btSlightlyActive);
                ColorButtons.ColorButtonWhite(btActive);
                ColorButtons.ColorButtonWhite(btVeryActive);
            }
        });
        btSlightlyActive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ColorButtons.ColorButtonWhite(btSedentary);
                ColorButtons.ColorButtonGreen(btSlightlyActive);
                ColorButtons.ColorButtonWhite(btActive);
                ColorButtons.ColorButtonWhite(btVeryActive);
            }
        });
        btActive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ColorButtons.ColorButtonWhite(btSedentary);
                ColorButtons.ColorButtonWhite(btSlightlyActive);
                ColorButtons.ColorButtonGreen(btActive);
                ColorButtons.ColorButtonWhite(btVeryActive);
            }
        });
        btVeryActive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ColorButtons.ColorButtonWhite(btSedentary);
                ColorButtons.ColorButtonWhite(btSlightlyActive);
                ColorButtons.ColorButtonWhite(btActive);
                ColorButtons.ColorButtonGreen(btVeryActive);
            }
        });
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                ColorButtons.ColorButtonWhite(btSedentary);
                ColorButtons.ColorButtonWhite(btSlightlyActive);
                ColorButtons.ColorButtonWhite(btActive);
                ColorButtons.ColorButtonWhite(btVeryActive);
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
        if (registerExecutor.getUserBuilder().getLifeStyle() != null) {
            RegisterMainFrame.changePage(3);
        }
    }

    private void draw() {
        ColorButtons.ColorButtonWhite(btSedentary);
        ColorButtons.ColorButtonWhite(btSlightlyActive);
        ColorButtons.ColorButtonWhite(btActive);
        ColorButtons.ColorButtonWhite(btVeryActive);
        new RegisterChangeTheme(txtChangeTheme);
    }

    @Override
    public void showPage() {
        txtS.setText("");
        txtSA.setText("");
        txtA.setText("");
        txtVA.setText("");
        try {
            BufferedImage imgS = ImageIO.read(new File(Resources.getSedentaryLSPic()));
            txtS.setIcon(new ImageIcon(imgS.getScaledInstance(-1, 95, Image.SCALE_SMOOTH)));
            BufferedImage imgSA = ImageIO.read(new File(Resources.getSlightlyActiveLSPic()));
            txtSA.setIcon(new ImageIcon(imgSA.getScaledInstance(-1, 95, Image.SCALE_SMOOTH)));
            BufferedImage imgA = ImageIO.read(new File(Resources.getActiveLSPic()));
            txtA.setIcon(new ImageIcon(imgA.getScaledInstance(-1, 95, Image.SCALE_SMOOTH)));
            BufferedImage imgVA = ImageIO.read(new File(Resources.getVeryActiveLSPic()));
            txtVA.setIcon(new ImageIcon(imgVA.getScaledInstance(-1, 95, Image.SCALE_SMOOTH)));
        } catch (IOException | NullPointerException ignored) {
            System.out.println("Couldn't process image");
        }
        draw();
        super.showPage();
    }
}
