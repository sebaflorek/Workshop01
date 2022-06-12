package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    static final String[] options = {"add", "remove", "list", "exit"};
    static final String file_name = "tasks_test.csv";
    static String[][] tasks;

    // ======================= MAIN ============================

    public static void main(String[] args) {

        System.out.println(ConsoleColors.BLUE + "Actual tasks:" + ConsoleColors.RESET);
        printFile(file_name);
        printHead(options);
        fileToTab(file_name);


    }

    // ===================== METODY ============================

    // DRUKOWANIE OPCJI PROGRAMU
    public static void printHead(String[] tab) {
        System.out.println(ConsoleColors.BLUE + "Please select an option" + ConsoleColors.RESET);
        for (int i = 0; i < options.length; i++) {
            System.out.println(options[i]);
        }
    }

    // DRUKOWANIE PLIKU Z ZADANIAMI
    public static void printFile(String fileName) {
        File file = new File(fileName);
        StringBuilder reader = new StringBuilder();
        try {
            Scanner scan = new Scanner(file);
            int numbering = 0;
            while (scan.hasNextLine()) {
                reader.append(numbering).append(" : ").append(scan.nextLine()).append("\n");
                numbering += 1;
            }
        } catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "File not found" + ConsoleColors.RESET);
        }
        System.out.println(reader);
    }

    // KONWERSJA PLIKU DO TABLICY
    public static String[][] fileToTab(String fileName) {
        //Path path = Paths.get(file_name);
        //if (!Files.exists(path)) {
        //    System.out.println(ConsoleColors.RED + "File not found" + ConsoleColors.RESET);
        //}
        File file = new File(fileName);
        StringBuilder reader = new StringBuilder();
        int counter = 0;
        String[] singleTask = new String[3];
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                scan.nextLine();
                counter += 1;
            }
            //System.out.println(counter);
            //System.out.println(Arrays.toString(singleTask));
        } catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "File not found" + ConsoleColors.RESET);
        }
        String[][] tasksTab = new String[counter][3];
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                for (int i = 0; i < counter; i++) {
                    String line = scan.nextLine();
                    singleTask = line.split(", ");
                    tasksTab[i] = singleTask;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "File not found" + ConsoleColors.RESET);
        }
        // System.out.println(Arrays.deepToString(tasksTab));
        return tasksTab;
    }


}
