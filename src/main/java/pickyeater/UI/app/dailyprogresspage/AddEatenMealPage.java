package pickyeater.UI.app.dailyprogresspage;

import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.PickyMeal;
import pickyeater.executors.AddEatenMealExecutor;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.searcher.MealSearcherExecutor;
import pickyeater.managers.EaterManager;

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

        btDailyProgress.setBackground(Color.green);
        btDiet.setBackground(Color.white);
        btFood.setBackground(Color.white);
        btGroceries.setBackground(Color.white);
        btUser.setBackground(Color.white);
        btSettings.setBackground(Color.white);

        MealSearcherExecutor mealSearcherExecutor = new MealSearcherExecutor(ExecutorProvider.getEaterManager());

        listMeals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listMeals.setListData(mealSearcherExecutor.getAllMealsObj());

        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                setVisible(false);
                new MainButton(new PanelButtonsConverter(cmd).Convert());
            }
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
                new DailyProgressPage();
            }
        });
        btSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // TODO: Do stuff but stay in the same page
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
                AddEatenMealExecutor addEatenMealExecutor =
                        new AddEatenMealExecutor(ExecutorProvider.getEaterManager());

                addEatenMealExecutor.addEatenMeal(currentMeal);

                System.out.println(currentMeal);

                JOptionPane.showMessageDialog(mainPanel, "Eaten meal:\n" + currentMeal);
                // TODO: FIX MASSIVE ERROR

            }
        });
    }
}