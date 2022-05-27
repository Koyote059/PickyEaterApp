package pickyeater.UI.themes.filehandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;

public class FileHandler {
    public void createFile() {
        try {
            File myObj = new File("res/themes.txt");
            if (!myObj.createNewFile()) {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeFile(ThemesEnum themesEnum) {
        try {
            FileWriter myWriter = new FileWriter("res/themes.txt");
            myWriter.write(themesEnum.toString());
            myWriter.close();
//            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String readFile() {
        String data = "";
        try {
            File myObj = new File("res/themes.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
//                System.out.println(data);
            }
            myReader.close();
            return data;
        } catch (FileNotFoundException e) {
            System.out.println("themes.txt didn't exist, created");
            //e.printStackTrace();
        }
        return data;
    }

    public void deleteFile() {
        File myObj = new File("res/themes.txt");
        if (!myObj.delete()) {
            System.out.println("Failed to delete the file.");
        }
    }
}