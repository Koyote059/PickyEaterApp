package pickyeater.database;

import pickyeater.database.jsonutils.FoodCreator;
import pickyeater.database.jsonutils.JSONCreator;
import org.json.JSONException;
import org.json.JSONObject;
import pickyeater.basics.user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class JSONUserDatabase implements UserDatabase {

    private final String dbPath;

    public JSONUserDatabase(String dbPath) {
        this.dbPath = dbPath;
    }

    @Override
    public Optional<User> loadUser() {
        try {
            if(!Files.exists(Path.of(dbPath))) return Optional.empty();
            JSONObject jsonObject = JSONCreator.getJsonObjectFromFile(dbPath);
            User user = FoodCreator.getUserFromJSON(jsonObject);
            return Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void saveUser(User user) {
        try{
            File file = new File(dbPath);
            if(!file.exists()) file.createNewFile();
            JSONObject userJSON = JSONCreator.getUserJsonObject(user);
            Files.writeString(Path.of(dbPath),userJSON.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
