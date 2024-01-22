package cz.itnetwork.projekt_itnetwork_v3;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void spustitProgram(DatabaseMySQL insureeDatabase) throws SQLException {

        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        UserInterface userInterface = new UserInterface(scanner, insureeDatabase);

        /**
         * proměnná pro uložení volby vybrané uživatelem
         */
        int action = 0;

        /**
         * kontrola, zda uživatel zadal číslo, a ne například písmeno
         */
        do {
            userInterface.displayStartScreen();
            try {
                action = Integer.parseInt(scanner.nextLine());
            } catch (Exception exception) {
                System.out.println("Nezadali jste číslo.");
            }

            System.out.println();

            switch (action) {
                case 1 -> userInterface.addInsuree();


                case 2 -> userInterface.listInsurees();


                case 3 -> userInterface.findInsuree();


                case 4 -> System.out.println("Děkuji za použití aplikace.");


                default -> System.out.println("Nesprávná volba. Proveďte nový výběr.\n");

            }
        } while ((action != 4));
    }

    public static void main(String[] args) {
        try {
            DatabaseMySQL insureeDatabase = new DatabaseMySQL("pojistenci", "pojistenci", "root", "");
            spustitProgram(insureeDatabase);
        } catch (SQLException exception) {
            System.out.println("Chyba: " + exception.getMessage());
        }
    }
}
