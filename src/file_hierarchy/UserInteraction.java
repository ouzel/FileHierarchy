package file_hierarchy;

import file_hierarchy.file.FileManager;

import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class UserInteraction {
    // Reader for the interaction with the user.
    private static final Scanner reader = new Scanner(new InputStreamReader(System.in));


    /*
    Starting the interaction.
    Getting the correct folder path and then analysing the files and trying to
    concatenate them if possible.
     */
    public static void start(){
        System.out.println("This app can help you concatenate files with the specific structure.");
        String folder = getFolderPath();
        concatenationProcess(folder);
    }


    /*
    Recursive method for getting the correct folder path.
    Correct folder path means that it leads to an existing folder to analyse files.
    The user is asked to input until they enter the correct path.
     */
    private static String getFolderPath() {
        System.out.println("Input the full path to the folder:");
        String input = reader.nextLine().trim();
        if (!Files.exists(Path.of(input))){
            System.out.println("The path to the folder is not valid. Try again.");
            return getFolderPath();
        } else {
            return input;
        }
    }

    /*
    Method for executing the process of concatenation.
     */
    private static void concatenationProcess(String folder){
        FileManager manager = new FileManager(folder);
        manager.concatenateAll();
    }
}
