package pickyeater.database;

import pickyeater.basics.groceries.Groceries;
import pickyeater.basics.groceries.GroceriesCheckList;
import pickyeater.database.SQLutils.SQLExecutorManager;
import pickyeater.database.SQLutils.SQLCreator;
import pickyeater.database.SQLutils.SQLSafeQueryExecutor;
import pickyeater.database.SQLutils.SQLUnSafeQueryExecutor;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SQLGroceriesDatabase implements GroceriesDatabase {
    private  final SQLExecutorManager queryExecutor;

    public SQLGroceriesDatabase(SQLExecutorManager queryExecutor) {
        this.queryExecutor = queryExecutor;
    }

    @Override
    public Optional<Groceries> getGroceries()  {
        try {
            SQLSafeQueryExecutor executor = queryExecutor.getSafeQueryExecutor();
            ResultSet resultSet = executor.selectFromGroceriesItemTable();
            if(!resultSet.isBeforeFirst()) return Optional.empty();
            SQLCreator sqlCreator = new SQLCreator();
            Groceries groceries = sqlCreator.getGroceries(resultSet);
            return Optional.of(groceries);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveGroceries(GroceriesCheckList groceriesCheckList) {
        try {
            SQLUnSafeQueryExecutor executor = queryExecutor.getUnSafeQueryExecutor();
            executor.insertIntoGroceriesTable(groceriesCheckList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteGroceries(Groceries groceries) {
        try {
            SQLUnSafeQueryExecutor executor = queryExecutor.getUnSafeQueryExecutor();
            executor.deleteFromGroceriesTable();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
