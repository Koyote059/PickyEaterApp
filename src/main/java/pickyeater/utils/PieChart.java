package pickyeater.utils;

import javax.swing.*;
import java.awt.*;

class Slice {
    double value;
    Color color;

    public Slice(double value, Color color) {
        this.value = value;
        this.color = color;
    }
}

class MyComponent extends JComponent {
    Slice[] slices = {new Slice(5, Color.black), new Slice(33, Color.green), new Slice(20, Color.yellow), new Slice(15, Color.red)};

    MyComponent() {
    }

    public void paint(Graphics g) {
        drawPie((Graphics2D) g, getBounds(), slices);
    }

    void drawPie(Graphics2D g, Rectangle area, Slice[] slices) {
        double total = 0.0D;
        for (Slice slice : slices) {
            total += slice.value;
        }
        double curValue = 0.0D;
        int startAngle;
        for (Slice slice : slices) {
            startAngle = (int) (curValue * 360 / total);
            int arcAngle = (int) (slice.value * 360 / total);
            g.setColor(slice.color);
            g.fillArc(area.x, area.y, area.width, area.height, startAngle, arcAngle);
            curValue += slice.value;
        }
    }
}

public class PieChart {
    public static void main(String[] argv) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new MyComponent());
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}