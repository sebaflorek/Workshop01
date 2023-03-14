package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskManager {

    static final String[] options = {"add", "remove", "list", "exit"};
    static final String file_name = "tasks.csv";
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
                    addTask();
                    break;
                case "remove":
                    removeTask();
                    break;
                case "list":
                    printTaskTab(tasks);
                    break;
                case "exit":
                    saveTasksToFile(file_name, tasks);
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
        System.out.print(reader);
    }

    // KONWERSJA PLIKU DO TABLICY[][]
    public static String[][] fileToTab(String fileName) {
        File file = new File(fileName);
        int counter = 0;
        String[] singleTask;
        String[][] tasksTab = new String[0][0];
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                scan.nextLine();
                counter += 1;
            }
            tasksTab = new String[counter][3];
            scan = new Scanner(file);
            while (scan.hasNextLine()) {
                for (int i = 0; i < counter; i++) {
                    String line = scan.nextLine();
                    singleTask = line.split(", ");
                    tasksTab[i] = singleTask;
                }
            }
            //System.out.println(counter);
            //System.out.println(Arrays.toString(singleTask));
        } catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "File not found" + ConsoleColors.RESET);
        }
        //System.out.println(Arrays.deepToString(tasksTab));
        return tasksTab;
    }

    // WYDRUKOWANIE TABLICY
    public static void printTaskTab(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i + " : ");
            for (int k = 0; k < tab[i].length; k++) {
                System.out.print(tab[i][k] + " ");
            }
            System.out.println();
        }
    }

    // DODANIE ZADANIA
    public static void addTask() {
        Scanner scanAdd = new Scanner(System.in);
        System.out.println("Please describe the new task:");
        String newDescription = scanAdd.nextLine();
        System.out.println("Please add a due date for new task <year-month-day>");
        String newDate = scanAdd.nextLine();
        System.out.println("Is the task important? Write true or false");
        String isImportant = scanAdd.nextLine();
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = newDescription;
        tasks[tasks.length - 1][1] = newDate;
        tasks[tasks.length - 1][2] = isImportant;
        System.out.println(ConsoleColors.YELLOW + "Added new task. What do you want to do next?" + ConsoleColors.RESET);
    }

    // USUWANIE ZADANIA
    public static void removeTask() {
        Scanner scanRemove = new Scanner(System.in);
        System.out.println("Please select a task number to be removed");
        int taskNumber = -1;
        try {
            taskNumber = scanRemove.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(ConsoleColors.RED + "Not a number. Try again." + ConsoleColors.RESET);
            removeTask();
        }
        if (taskNumber >= 0) {
            try {
                tasks = ArrayUtils.remove(tasks, taskNumber);
                System.out.println(ConsoleColors.YELLOW + "Task " + taskNumber + " removed. What do you want to do next?" + ConsoleColors.RESET);
            } catch (IndexOutOfBoundsException ex) {
                System.out.println(ConsoleColors.RED + "Task number " + taskNumber + " does not exist. Try again." + ConsoleColors.RESET);
                removeTask();
            }
        } else {
            System.out.println(ConsoleColors.RED + "Task number " + taskNumber + " is negative. Try again. " + ConsoleColors.RESET);
        }
    }

    // WYJŚCIE Z PROGRAMU
    public static void saveTasksToFile(String filename, String[][] tab) {
        Path dir = Paths.get(filename);
        Path backupDir = Paths.get("tasks_backup.csv");
        // Tworzymy backup :)
        try {
            Files.copy(dir, backupDir, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        String[] lines = new String[tab.length];
        for (int i = 0; i < tab.length; i++) {
            lines[i] = String.join(", ", tab[i]);
        }
        /* Przed optymalizacją
        try {
            try (FileWriter writer = new FileWriter("tasks.csv")) {
                for (int i = 0; i < tab.length; i++) {
                    writer.write(lines[i] + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
         */
        try {
            Files.write(dir, Arrays.asList(lines));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
