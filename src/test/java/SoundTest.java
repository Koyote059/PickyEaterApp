import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SoundTest {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException{

        Scanner scanner = new Scanner(System.in);

        File file = new File("rock.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        String response = "";


        while(!response.equals("Q")) {
            System.out.println("P = play, S = Stop, R = Reset, Q = Quit");
            System.out.print("Enter your choice: ");

            response = scanner.next();
            response = response.toUpperCase();
            switch (response) {
                case ("P") -> clip.start();
                case ("S") -> clip.stop();
                case ("R") -> clip.setMicrosecondPosition(0);
                case ("Q") -> clip.close();
                default -> System.out.println("Not a valid response");
            }

        }
        System.out.println("Byeeee!");
    }
}
