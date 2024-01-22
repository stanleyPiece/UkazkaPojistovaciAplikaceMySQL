package cz.itnetwork.projekt_itnetwork_v3;

public class QueryMySQL { /* třída sloužící k sestavení SQL dotazu */

    private StringBuilder query = new StringBuilder();

    /* výběr hodnot ze všech sloupců */
    protected QueryMySQL selectAll() {
        query.setLength(0);
        query.append("SELECT *");
        return this;
    }
    /* určení tabulky */
    protected QueryMySQL from(String table) {
        query.append(" FROM ")
             .append(table);
        return this;
    }
    /* podmínka WHERE */
    protected QueryMySQL where(String request) {
        query.append(" WHERE ")
             .append(request);
        return this;
    }
    /* vložení do tabulky */
    protected QueryMySQL insert(String table) {
        query.setLength(0);
        query.append("INSERT INTO ")
             .append(table);
        return this;
    }

    /**
     * SQL část pro předání hodnot
     * @param parameters
     * @return values
     */
    protected QueryMySQL values(Object[] parameters) {

        query.append(" VALUES(");

        // kontrola, zda nepředáváme prázdné parametry
        int count = parameters.length;
        if(count == 0)
            throw new IllegalArgumentException("Neplatný počet parametrů");
        query.append(("?,").repeat(count));

        // odstraníme poslední čárku
        query.deleteCharAt(query.lastIndexOf(","))
             .append(");");
        return this;
    }

    /**
     * Metoda, která vrátí sestavený dotaz.
     * @return dotaz
     */
    protected String getQuery() {
        return query.toString();
    }
}
