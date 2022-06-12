package pickyeater.UI.pages.registerpage;

import pickyeater.utils.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.random.RandomGenerator;

public class WelcomePage extends JFrame implements ActionListener {
    Image logo;
    Timer timer;
    int xVelocity = 1;
    int yVelocity = 2;
    private final RandomGenerator randomGenerator = new Random();
    int x = randomGenerator.nextInt(1, 400);
    int y = randomGenerator.nextInt(1, 400);
    private JPanel mainPanel;
    private JPanel coloredPanel;
    private JLabel txtWelcome;
    private boolean clr = true;

    public WelcomePage() {
        txtWelcome.setFont(new Font("Bauhaus 93", Font.PLAIN, 50));
        txtWelcome.setForeground(Color.decode("#32AB5E"));
        setContentPane(mainPanel);
        setSize(677, 507);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 677 / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 507 / 2);
        setResizable(false);
        coloredPanel.setBackground(Color.decode("#B1EA9D"));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        BufferedImage imgVA;
        try {
            imgVA = ImageIO.read(new File(Resources.getPELPic()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logo = new ImageIcon(imgVA.getScaledInstance(100, -1, Image.SCALE_SMOOTH)).getImage();
        timer = new Timer(60, this);
        timer.start();
        coloredPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                EventQueue.invokeLater(RegisterMainFrame::new);
                setVisible(false);
            }
        });
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(logo, x, y, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (x >= getWidth() - logo.getWidth(null) | x <= 0) {
            xVelocity = xVelocity * -1;
            changeColorOnImpact();
        }
        if (y >= getHeight() - logo.getHeight(null) | y <= 0) {
            yVelocity = yVelocity * -1;
            changeColorOnImpact();
        }
        x = x + xVelocity;
        y = y + yVelocity;
        repaint();
    }

    private void changeColorOnImpact(){
        if (clr){
            txtWelcome.setForeground(Color.decode("#B1EA9D"));
            coloredPanel.setBackground(Color.decode("#32AB5E"));
            clr = false;
        } else {
            txtWelcome.setForeground(Color.decode("#32AB5E"));
            coloredPanel.setBackground(Color.decode("#B1EA9D"));
            clr = true;
        }
    }
}
