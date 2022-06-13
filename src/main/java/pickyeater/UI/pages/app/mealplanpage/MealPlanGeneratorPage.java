package pickyeater.UI.pages.app.mealplanpage;

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.pages.leftbuttons.PanelButtons;
import pickyeater.basics.food.Meal;
import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.builders.MealPlanBuilder;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.MealPlanCreatorExecutor;
import pickyeater.utils.Resources;
import pickyeater.utils.pagesutils.DailyMealPlanColumn;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MealPlanGeneratorPage extends PickyPage {
    private final JPanel topPanel;
    private final MealPlanBuilder mealPlanBuilder;
    List<DailyMealPlanColumn> columns = new ArrayList<>();
    private boolean isEditing = false;
    private JPanel mainPanel;
    private JButton doneButton;
    private JButton cancelButton;

    public MealPlanGeneratorPage(MealPlan mealPlan, JFrame parent) {
        this(ExecutorProvider.getMealPlanExecutor(), mealPlan, parent);
        isEditing = true;
    }

    public MealPlanGeneratorPage(MealPlanCreatorExecutor mealPlanCreator, MealPlan mealPlan, JFrame parent) {
        this(mealPlanCreator, parent);
        columns.remove(0);
        for (DailyMealPlan dailyMealPlan : mealPlan.getDailyMealPlans()) {
            DailyMealPlanColumn dailyMealPlanColumn = new DailyMealPlanColumn(parent);
            List<Meal> meals = dailyMealPlan.getMeals();
            dailyMealPlanColumn.addMeals(meals);
            columns.add(dailyMealPlanColumn);
        }
    }

    public MealPlanGeneratorPage(MealPlanCreatorExecutor mealPlanCreator, JFrame parent) {
        super(parent);
        mainPanel.setLayout(new BorderLayout());
        topPanel = new JPanel(new ScrollPaneLayout());
        JScrollPane scrollPane = new JScrollPane(topPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.removeAll();
        mainPanel.add(BorderLayout.CENTER, scrollPane);
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        this.mealPlanBuilder = mealPlanCreator.getMealPlanBuilder();
        doneButton.addActionListener(e -> {
            for (DailyMealPlanColumn column : columns) {
                DailyMealPlan dailyMealPlan = column.getDailyMealPlan();
                if (dailyMealPlan.getMeals().size() == 0) {
                    JOptionPane.showMessageDialog(parent, "You can't leave empty days!");
                    return;
                }
                mealPlanBuilder.addDailyMealPlan(dailyMealPlan);
            }
            parent.setSize(new Dimension(677, 507));
            mealPlanCreator.saveMealPlan(mealPlanBuilder.build());
            MainFrame.changePage(PanelButtons.DIET);
        });
        cancelButton.addActionListener(e -> {
            parent.setSize(new Dimension(677, 507));
            MainFrame.changePage(PanelButtons.DIET);
        });
        GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(1);
        columns.add(new DailyMealPlanColumn(parent));
        setPreferredSize(new Dimension(677, 507));
        setSize(new Dimension(677, 507));
    }

    public void draw() {
        topPanel.removeAll();
        BoxLayout boxLayout = new BoxLayout(topPanel, BoxLayout.Y_AXIS);
        topPanel.setLayout(boxLayout);
        //topPanel.setLayout(new GridLayout(1, columns.size() + 1));
        for (int i = 0; i < columns.size(); i++) {
            DailyMealPlanColumn column = columns.get(i);
            Component component = column.getComponent();
            JPanel dailyMealPlanColumnPanel = new JPanel(new BorderLayout());
            JLabel dayLabel = new JLabel("Day " + (i + 1));
            dayLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
            int finalI = i;
            dayLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (SwingUtilities.isRightMouseButton(e) || e.isPopupTrigger()) {
                        JPopupMenu popupMenu = new JPopupMenu();
                        JMenuItem removeItem = new JMenuItem("Remove");
                        removeItem.addActionListener(e1 -> {
                            if (columns.size() == 0)
                                return;
                            columns.remove(finalI);
                            draw();
                        });
                        popupMenu.add(removeItem);
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            });
            JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
            buttonsPanel.add(cancelButton);
            buttonsPanel.add(doneButton);
            mainPanel.add(BorderLayout.PAGE_END, buttonsPanel);
            JComponent upArrowComponent;
            try {
                BufferedImage binImage = ImageIO.read(new File(Resources.getUpArrowPic()));
                JLabel upArrow = new JLabel();
                upArrow.setIcon(new ImageIcon(binImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
                upArrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                upArrowComponent = upArrow;
            } catch (IOException | NullPointerException ignored) {
                upArrowComponent = new JButton("^");
            }
            upArrowComponent.setToolTipText("Move up");
            upArrowComponent.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (e.getClickCount() != 1 || finalI == 0)
                        return;
                    Collections.swap(columns, finalI, finalI - 1);
                    draw();
                }
            });
            JComponent downArrowComponent;
            try {
                BufferedImage binImage = ImageIO.read(new File(Resources.getDownArrowPic()));
                JLabel downArrow = new JLabel();
                downArrow.setIcon(new ImageIcon(binImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
                downArrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                downArrowComponent = downArrow;
            } catch (IOException | NullPointerException ignored) {
                downArrowComponent = new JButton("v");
            }
            downArrowComponent.setToolTipText("Move down");
            downArrowComponent.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (e.getClickCount() != 1 || finalI == (columns.size() - 1))
                        return;
                    Collections.swap(columns, finalI, finalI + 1);
                    draw();
                }
            });
            JPanel dayNamePanel = new JPanel(new BorderLayout());
            if (i != 0)
                dayNamePanel.add(BorderLayout.LINE_START, upArrowComponent);
            if (i != (columns.size() - 1))
                dayNamePanel.add(BorderLayout.LINE_END, downArrowComponent);
            dayNamePanel.add(BorderLayout.CENTER, dayLabel);
            dayLabel.setToolTipText("Right click to remove");
            dailyMealPlanColumnPanel.add(BorderLayout.PAGE_START, dayNamePanel);
            dailyMealPlanColumnPanel.add(BorderLayout.CENTER, component);
            dailyMealPlanColumnPanel.setMinimumSize(new Dimension(400, 400));
            topPanel.add(dailyMealPlanColumnPanel);
        }
        JButton addTableButton = new JButton();
        try {
            BufferedImage plusImage = ImageIO.read(new File(Resources.getPlusPic()));
            addTableButton.setIcon(new ImageIcon(plusImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        } catch (IOException | NullPointerException ignored) {
            addTableButton.setText("+");
        }
        addTableButton.setToolTipText("Click to add day");
        addTableButton.addActionListener(e -> {
            columns.add(new DailyMealPlanColumn(parent));
            draw();
            revalidate();
        });
        topPanel.add(Box.createRigidArea(new Dimension(5, 5)));
        topPanel.add(addTableButton);
        topPanel.add(Box.createRigidArea(new Dimension(5, 5)));
        addTableButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        revalidate();
    }

    @Override
    public void showPage() {
        draw();
        super.showPage();
    }
}
