package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class TaskManager {

    static final String[] options = {"add", "remove", "list", "exit"};
    static final String file_name = "tasks_test.csv";
    static String[][] tasks;

    // ======================= MAIN ============================

    public static void main(String[] args) {
        tasks = fileToTab(file_name);
        printFile(file_name);
        printHead(options);
        //printTaskTab(tasks);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String dataInput = scanner.nextLine();
            switch (dataInput) {
                case "add":
                    System.out.println("METODA ADD W BUDOWIE");
                    break;
                case "remove":
                    System.out.println("METODA REMOVE W BUDOWIE");
                    break;
                case "list":
                    printTaskTab(tasks);
                    break;
                case "exit":
                    System.out.println(ConsoleColors.RED + "Bye, bye.");
                    System.exit(0);
                    break;
                default:
                    System.out.println(ConsoleColors.RED + "Wrong option - try again!" + ConsoleColors.RESET);
                    //printHead(options);
            }
            printHead(options);
        }


    }

    // ===================== METODY ============================

    // DRUKOWANIE OPCJI PROGRAMU
    public static void printHead(String[] tab) {
        System.out.println(ConsoleColors.BLUE + "Please select an option" + ConsoleColors.RESET);
        for (String s : tab) {
            System.out.println(s);
        }
    }

    // DRUKOWANIE PLIKU Z ZADANIAMI
    public static void printFile(String fileName) {
        Path dir = Paths.get(fileName);
        if (Files.exists(dir)) {
            System.out.println(ConsoleColors.BLUE + "Actual tasks saved in file: " + ConsoleColors.YELLOW + file_name + ConsoleColors.RESET);
        }
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
        int counter = 0;
        String[] singleTask;
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
        //System.out.println(Arrays.deepToString(tasksTab));
        return tasksTab;
    }

    public static void printTaskTab(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i + " : ");
            for (int k = 0; k < tab[i].length; k++) {
                System.out.print(tab[i][k] + " ");
            }
            System.out.println();
        }
    }


}
