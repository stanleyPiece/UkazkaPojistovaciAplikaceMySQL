package cz.itnetwork.projekt_itnetwork_v3;

import java.sql.*;

public class DatabaseMySQL { //třída představující databázi SQL

    //připojení k databázi
    private Connection connection;

    //instance třídy dotazu
    private QueryMySQL queryMySQL;

    //sada výsledků
    private ResultSet insurees;

    //přistupovaná tabulka
    private String table;

    /**
     * Konstruktor třídy databáze
     */
    protected DatabaseMySQL(String database, String table, String userName, String password) throws SQLException {
        queryMySQL = new QueryMySQL();
        this.table = table;
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database, userName, password);

    }
    /**
     * Metoda pro vložení záznamu pojištěnce
     * @param firstName
     * @param lastName
     * @param age
     * @param phoneNumber
     * @throws SQLException
     */
    protected void insertInsureeRecord(String firstName, String lastName, int age, String phoneNumber) throws SQLException {
        Object[] parameters = {null, firstName, lastName, age, phoneNumber};
        queryMySQL.insert(table).values(parameters);
        PreparedStatement queryDatabase = connection.prepareStatement(queryMySQL.getQuery());
        queryDatabase.setString(1, null);
        queryDatabase.setString(2, firstName);
        queryDatabase.setString(3, lastName);
        queryDatabase.setString(4, String.valueOf(age));
        queryDatabase.setString(5, phoneNumber);
        queryDatabase.executeUpdate();
    }

    /**
     * Metoda pro výpis záznamů pojištěnců
     * @return insurees
     * @throws SQLException
     */
    protected ResultSet listInsureeRecords() throws SQLException {
        queryMySQL.selectAll()
                  .from(table);
        PreparedStatement queryDatabase = connection.prepareStatement(queryMySQL.getQuery());
        insurees =  queryDatabase.executeQuery();

        return insurees;
    }

    /**
     * Metoda pro vrácení záznamu hledaného pojištěnce
     * @param firstName
     * @param lastName
     * @return insurees
     * @throws SQLException
     */
    protected ResultSet findInsureeRecord(String firstName, String lastName) throws  SQLException {

        queryMySQL.selectAll()
                  .from(table)
                  .where("jmeno = ? AND prijmeni = ?");
                                                                    // změníme typ ResultSet, abychom mohli procházet vpřed i vzad
        PreparedStatement queryDatabase = connection.prepareStatement(queryMySQL.getQuery(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        queryDatabase.setString(1, firstName);
        queryDatabase.setString(2, lastName);
        insurees =  queryDatabase.executeQuery();

        return insurees;
    }
}
