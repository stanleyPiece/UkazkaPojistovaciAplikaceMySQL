package cz.itnetwork.projekt_itnetwork_v3;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


/**
 *
 * @author Stanislav Kus
 */
public class UserInterface {

    private DatabaseMySQL insureeDatabase;
    private Scanner scanner;
    private boolean correctEntry;

    /**
     * instance uživatelského rozhraní
     *
     * @param scanner
     */
    UserInterface(Scanner scanner, DatabaseMySQL insureeDatabase) throws SQLException {
        this.insureeDatabase = insureeDatabase;
        this.scanner = scanner;
    }

    /**
     * metoda pro zobrazení úvodní obrazovky
     */
    protected void displayStartScreen() {
        System.out.println("–––––––––––––––––––––––––––––––––");
        System.out.println("Evidence pojištěných");
        System.out.println("–––––––––––––––––––––––––––––––––");
        System.out.println("Vyberte si akci:");
        System.out.println("1 – Přidat nového pojištěného");
        System.out.println("2 – Vypsat všechny pojištěné");
        System.out.println("3 – Vyhledat pojištěného");
        System.out.println("4 – Konec");
    }

    /**
     * metoda sloužící k ověření, zda zadaný text (např. jméno nebo příjmení)
     * obsahuje číslici
     *
     * @param analysedText
     * @return true nebo false
     */
    private boolean containsNumber(String analysedText) {
        char[] numbers = analysedText.toCharArray();
        for (char character : numbers) {
            if (Character.isDigit(character)) {
                return true;
            }
        }
        return false;
    }

    /**
     * metoda sloužící k ověření, zda je zadaný text (např. jméno nebo příjmení)
     * prázdný
     *
     * @param analysedText
     * @return true nebo false
     */
    private boolean isTextEmpty(String analysedText) {
        return analysedText.equals("");
    }

    /**
     * metoda pro získání jména
     *
     * @return insureeFirstName
     */
    private String enterFirstName() {
        /**
         * kontrola spravného zadání jména
         */
        correctEntry = false;
        String insureeFirstName = "";
        do {
            System.out.println("Zadejte jméno pojištěnce:");
            try {
                insureeFirstName = scanner.nextLine().trim();
                // kontrola, zda jméno obsahuje číslice
                if (containsNumber(insureeFirstName)) {
                    System.out.println("Jméno nemůže obsahovat číslice.");
                } // kontrola, zda je jméno prázdné
                else if (isTextEmpty(insureeFirstName)) {
                    System.out.println("Jméno nemůže být prázdné");
                } else {
                    correctEntry = true;
                }
            } catch (Exception exception) {
                System.out.println("Chybně zadané jméno.");
            }
        } while (!correctEntry);
        return insureeFirstName;
    }

    /**
     * metoda pro získání příjmení
     *
     * @return insureeLastName
     */
    private String enterLastName() {
        /**
         * kontrola správného zadání příjmení
         */
        correctEntry = false;
        String insureeLastName = "";
        do {
            System.out.println("Zadejte příjmení pojištěnce:");
            try {
                insureeLastName = scanner.nextLine().trim();
                // kontrola, zda příjmení obsahuje číslice
                if (containsNumber(insureeLastName)) {
                    System.out.println("Příjmení nemůže obsahovat číslice.");
                } // kontrola, zda je příjmení prázdné
                else if (isTextEmpty(insureeLastName)) {
                    System.out.println("Příjmení nemůže být prázdné");
                } else {
                    correctEntry = true;
                }
            } catch (Exception exception) {
                System.out.println("Chybně zadané příjmení.");
            }
        } while (!correctEntry);
        return insureeLastName;
    }

    /**
     * metoda pro získání telefonního čísla
     *
     * @return insureePhone
     */
    private String enterPhone() {
        /**
         * kontrola, zda uživatel správně zadal telefonní číslo
         */
        String insureePhone = "";
        correctEntry = false;
        do {
            System.out.println("Zadejte telefonní číslo pojištěnce:");
            try {
                insureePhone = scanner.nextLine();
                correctEntry = true;
            } catch (Exception exception) {
                System.out.println("Chybně zadané telefonní číslo.");
            }
        } while (!correctEntry);
        return insureePhone;
    }

    /**
     * metoda pro získání věku
     *
     * @return insureeAge
     */
    private int enterAge() {
        /**
         * kontrola, zda uživatel správně zadal věk
         */
        int insureeAge = 0;
        correctEntry = false;
        do {
            System.out.println("Zadejte věk pojištěnce:");
            try {
                insureeAge = Integer.parseInt(scanner.nextLine());
                correctEntry = true;
            } catch (Exception exception) {
                System.out.println("Chybně zadaný věk.");
            }
        } while (!correctEntry);
        return insureeAge;
    }

    /**
     * metoda pro přidání pojištěnce
     */
    protected void addInsuree() throws SQLException {
        insureeDatabase.insertInsureeRecord(enterFirstName(), enterLastName(), enterAge(), enterPhone());
        pressEnter();
    }

    /**
     * String oznamující uživateli, že je databáze prázdná
     *
     * @return Databáze pojištěnců je prázdná.
     */
    private String emptyDatabase() {
        return "Databáze pojištěnců je prázdná.";
    }

    /**
     * String oznamující uživateli, že pojištěnec nebyl nalezen
     *
     * @return Daný pojištěnec nebyl nalezen.
     */
    private String insureeNotFound() {
        return "\n" + "Daný pojištěnec nebyl nalezen.";
    }

    /**
     * String oznamující uživateli, že pojištěnec byl nalezen
      * @return Nalezený pojištěnec:
     */
    private String insureeFound() {
        return "\n" + "Nalezený pojištěnec: ";
    }

    /**
     * metoda pro výpis pojištěnců
     */
    protected void listInsurees() throws SQLException {
        ResultSet insureeRecords = insureeDatabase.listInsureeRecords();
        while (insureeRecords.next()) {
            System.out.println(insureeRecords.getString("jmeno") + " " + insureeRecords.getString("prijmeni")
                                                                            + " " + insureeRecords.getInt("vek")
                                                                            + " " + insureeRecords.getString("telefonni_cislo"));
        }
    }

    /**
     * metoda pro nalezení pojištěnce
     */
    protected void findInsuree() throws SQLException {

        if (!insureeDatabase.listInsureeRecords().next()) {
            System.out.println(emptyDatabase());
        } else {
            ResultSet searchedInsuree = insureeDatabase.findInsureeRecord(enterFirstName(), enterLastName());
            if (!searchedInsuree.next()) {
               System.out.println(insureeNotFound());}
             else {
                 System.out.println(insureeFound());
                 searchedInsuree.beforeFirst();  //vrátíme kurzor zpět před první řádek, aby výpis nevynechal první záznam (kvůli tomu jsme ve třídě DatabaseMySQL změnili typ ResultSet
                 while (searchedInsuree.next())  {
                    System.out.println(searchedInsuree.getString("jmeno") + " " + searchedInsuree.getString("prijmeni")
                            + " " + searchedInsuree.getInt("vek")
                            + " " + searchedInsuree.getString("telefonni_cislo"));
                }
                }
            }

        pressEnter();
    }

    /**
     * metoda vyzývající uživatele k pokračování stisknutím klávesy Enter
     */
    private void pressEnter() {

        while (true) {
            System.out.println("");
            System.out.println("Pokračujte stisknutím klávesy Enter...");
            System.out.println("");
            String pressedEnter = scanner.nextLine();
            if (pressedEnter.isEmpty()) {
                break;
            }
        }
    }
}
