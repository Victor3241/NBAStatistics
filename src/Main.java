import Viewers.AdminLogIn;
import Viewers.MainView;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        /*
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "victor";

        Connection connection = DriverManager.getConnection(url,user,password);
        Statement statement = connection.createStatement();

        String tableName1 = "\"Players\"";
        String tableName2 = "\"People\"";

        String insertName = "Grey";

        String Coloumn = "\"ppG\"";

        String insertQuery = "insert into "+tableName1+" values(" + "DEFAULT" + ", '"+insertName+"'" + ")";

        String selectQuery = "SELECT people.\"name\", players.\"ppG\" FROM " + tableName1 + " players JOIN " + tableName2 + " people ON players.\"personId\" = people.\"personId\" ORDER BY players." + Coloumn + " DESC";

        //statement.execute(insertQuery);
        statement.execute(selectQuery);
        statement.executeQuery(selectQuery);
        ResultSet query = statement.getResultSet();
        query.next();
        do {
            int i = 1;
            while(i <= 2){
                System.out.print(query.getArray(i) + " ");
                i++;
            }
            System.out.println();
        }
        while(query.next());
        System.out.println(selectQuery);

         */

        MainView view = new MainView();
    }
}