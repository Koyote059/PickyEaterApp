package pickyeater.UI.app.dailyprogresspage;

import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.PickyMeal;
import pickyeater.executors.AddEatenMealExecutor;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.searcher.MealSearcherExecutor;
import pickyeater.managers.EaterManager;
import pickyeater.utils.MealQuantityConverter;
import pickyeater.utils.StringToNumber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;

public class AddEatenMealPage extends JFrame {
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JButton btCancel;
    private JPanel pieChartPanel;
    private JList listMeals;
    private JPanel mainPanel;
    private JButton btSave;
    private JTextField tfQuantity;

    public AddEatenMealPage() {
        btDailyProgress.setBackground(Color.decode("#B1EA9D"));
        btDiet.setBackground(Color.decode("#FFFFFF"));
        btFood.setBackground(Color.decode("#FFFFFF"));
        btGroceries.setBackground(Color.decode("#FFFFFF"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#FFFFFF"));

        MealSearcherExecutor mealSearcherExecutor = new MealSearcherExecutor(ExecutorProvider.getEaterManager());

        listMeals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listMeals.setListData(mealSearcherExecutor.getAllMealsObj());
        listMeals.setToolTipText("Click twice to check ingredients");

        setContentPane(mainPanel);
        setSize(677, 507);    //pack();
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        ActionListener listener = e -> {

            String cmd = e.getActionCommand();
            setVisible(false);
            new MainButton(new PanelButtonsConverter(cmd).Convert());
        };
        btSettings.addActionListener(listener);
        btDailyProgress.addActionListener(listener);
        btUser.addActionListener(listener);
        btGroceries.addActionListener(listener);
        btFood.addActionListener(listener);
        btDiet.addActionListener(listener);
        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                //new DailyProgressPage();
            }
        });
        btSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMeal = listMeals.getSelectedValue().toString();
                Set<Meal> mealSet = mealSearcherExecutor.getAllMeals();

                System.out.println(selectedMeal);
                Meal currentMeal = mealSet.iterator().next();
                Iterator<Meal> mealIterator = mealSet.iterator();

                while (mealIterator.hasNext()){
                    currentMeal = mealIterator.next();
                    if (currentMeal.getName().equals(selectedMeal)){
                        break;
                    }
                    mealIterator.remove();
                }

                if (new StringToNumber().convertInteger(tfQuantity.getText()) == 0){
                    JOptionPane.showMessageDialog(mainPanel, "Error, insert valid number", "",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    currentMeal = new MealQuantityConverter().convert(currentMeal, new StringToNumber().convertInteger(tfQuantity.getText()));
                    AddEatenMealExecutor addEatenMealExecutor = new AddEatenMealExecutor(ExecutorProvider.getEaterManager());
                    addEatenMealExecutor.addEatenMeal(currentMeal);
                    System.out.println(currentMeal);    // TODO -> ??
                    JOptionPane.showMessageDialog(mainPanel, "Eaten meal:\n" + currentMeal);
                }
            }
        });
    }
}