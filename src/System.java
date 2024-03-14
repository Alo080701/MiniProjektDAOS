import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class System {

    /**
     * @param args
     */
    static Connection minConnection;
    static Statement stmt;
    static BufferedReader inLine;

    public static void selectudenparm() {
        try {
            // Laver sql-sætning og får den udført
            String sql = "select navn,stilling from person";
            java.lang.System.out.println("SQL-streng er " + sql);
            ResultSet res = stmt.executeQuery(sql);
            // gennemløber svaret
            while (res.next()) {
                String s;
                s = res.getString("navn");
                java.lang.System.out.println(s + "    " + res.getString(2));
            }
            // pæn lukning
            if (!minConnection.isClosed()) minConnection.close();
        } catch (Exception e) {
            java.lang.System.out.println("fejl:  " + e.getMessage());
        }
    }

    public static void selectmedparm() {
        try {
            // Indlæser søgestreng
            java.lang.System.out.println("Indtast søgestreng");
            String inString = inLine.readLine();
            // Laver sql-sætning og får den udført
            String sql = "select navn,stilling from person where navn like '" + inString + "%'";
            java.lang.System.out.println("SQL-streng er " + sql);
            ResultSet res = stmt.executeQuery(sql);
            //gennemløber svaret
            while (res.next()) {
                java.lang.System.out.println(res.getString(1) + "    " + res.getString(2));
            }
            // pæn lukning
            if (!minConnection.isClosed()) minConnection.close();
        } catch (Exception e) {
            java.lang.System.out.println("fejl:  " + e.getMessage());
        }
    }

    public static void insertmedstring() {
        try {
            // indlæsning
            java.lang.System.out.println("Vi vil nu oprette et nyt ansættelsesfforhold");
            java.lang.System.out.println("Indtast cpr (personen skal være oprettet på forhånd");
            String cprstr = inLine.readLine();
            java.lang.System.out.println("Indtast firmanr (firma skal være oprettet på forhånd");
            String firmastr = inLine.readLine();

            // sender insert'en til db-serveren
            String sql = "insert into ansati values ('" + cprstr + "'," + firmastr + ")";
            java.lang.System.out.println("SQL-streng er " + sql);
            stmt.execute(sql);
            // pænt svar til brugeren
            java.lang.System.out.println("Ansættelsen er nu registreret");
            if (!minConnection.isClosed()) minConnection.close();
        } catch (SQLException e) {
            switch (e.getErrorCode())
            // fejl-kode 547 svarer til en foreign key fejl
            {
                case 547: {
                    if (e.getMessage().contains("cprforeign"))
                        java.lang.System.out.println("cpr-nummer er ikke oprettet");
                    else if (e.getMessage().contains("firmaforeign"))
                        java.lang.System.out.println("firmaet er ikke oprettet");
                    else
                        java.lang.System.out.println("ukendt fremmednøglefejl");
                    break;
                }
                // fejl-kode 2627 svarer til primary key fejl
                case 2627: {
                    java.lang.System.out.println("den pågældende ansættelse er allerede oprettet");
                    break;
                }
                default:
                    java.lang.System.out.println("fejlSQL:  " + e.getMessage());
            }
            ;
        } catch (Exception e) {
            java.lang.System.out.println("fejl:  " + e.getMessage());
        }
    }


    public static void insertprepared() {
        try {
            // indl�sning
            java.lang.System.out.println("Vi vil nu oprette et nyt ansættelsesforhold");
            java.lang.System.out.println("Indtast cpr (personen skal være oprettet på forhånd");
            String cprstr = inLine.readLine();
            java.lang.System.out.println("Indtast firmanr (firma skal være oprettet på forhånd");
            String firmastr = inLine.readLine();
            // Anvendelse af prepared statement
            String sql = "insert into ansati values (?,?)";
            PreparedStatement prestmt = minConnection.prepareStatement(sql);
            prestmt.clearParameters();
            prestmt.setString(1, cprstr);
            prestmt.setInt(2, Integer.parseInt(firmastr));
            // Udf�rer s�tningen
            prestmt.execute();
            // p�nt svar til brugeren
            java.lang.System.out.println("Ansættelsen er nu registreret");
            if (!minConnection.isClosed()) minConnection.close();
        } catch (SQLException e) {
            switch (e.getErrorCode())
            // fejl-kode 547 svarer til en foreign key fejl
            {
                case 547: {
                    if (e.getMessage().contains("cprforeign"))
                        java.lang.System.out.println("cpr-nummer er ikke oprettet");
                    else if (e.getMessage().contains("firmaforeign"))
                        java.lang.System.out.println("firmaet er ikke oprettet");
                    else
                        java.lang.System.out.println("ukendt fremmednøglefejl");
                    break;
                }
                // fejl-kode 2627 svarer til primary key fejl
                case 2627: {
                    java.lang.System.out.println("den pågældende ansættelse er allerede oprettet");
                    break;
                }
                default:
                    java.lang.System.out.println("fejlSQL:  " + e.getMessage());
            }
            ;
        } catch (Exception e) {
            java.lang.System.out.println("fejl:  " + e.getMessage());
        }
    }

    public static void selectmedpis() {
        try {
            // Laver sql-sætning og får den udført
            String sql = "select navn, stilling, postdistrikt, firmanavn from opstilling";
            java.lang.System.out.println("SQL-streng er " + sql);
            ResultSet res = stmt.executeQuery(sql);
            // gennemløber svaret
            while (res.next()) {
                String s;
                s = res.getString("navn");
                java.lang.System.out.println(s + "  [" + res.getString(2) + "], Postdistrikt: " + res.getString(3) + ", arbejdsplads: " + res.getString(4));
            }
            // pæn lukning
            if (!minConnection.isClosed()) minConnection.close();
        } catch (Exception e) {
            java.lang.System.out.println("fejl:  " + e.getMessage());
        }
    }

    public static void selectmedost() {
        try {
            // Laver sql-sætning og får den udført
            String sql = "select navn from person where loen > (select AVG(loen) from person)";
            java.lang.System.out.println("SQL-streng er " + sql);
            ResultSet res = stmt.executeQuery(sql);
            // gennemløber svaret
            while (res.next()) {
                String s;
                s = res.getString("navn");
                java.lang.System.out.println(s);
            }
            // pæn lukning
            if (!minConnection.isClosed()) minConnection.close();
        } catch (Exception e) {
            java.lang.System.out.println("fejl:  " + e.getMessage());
        }
    }

    public static void selectmedbjerg() {
        try {
            // Laver sql-sætning og får den udført
            String sql = "select stilling, navn from person where loen in (select MAX(loen) as maxløn from person group by stilling);";
            java.lang.System.out.println("SQL-streng er " + sql);
            ResultSet res = stmt.executeQuery(sql);
            // gennemløber svaret
            while (res.next()) {
                String s;
                s = res.getString("stilling");
                java.lang.System.out.println(s + " " + res.getString(2));
            }
            // pæn lukning
            if (!minConnection.isClosed()) minConnection.close();
        } catch (Exception e) {
            java.lang.System.out.println("fejl:  " + e.getMessage());
        }
    }

    public static void insertPerson() {
        try {
            // indlæsning
            java.lang.System.out.println("Vi vil nu oprette en nyperson");
            java.lang.System.out.println("Indtast cpr");
            String cprstr = inLine.readLine();
            java.lang.System.out.println("Indtast navn");
            String navnstr = inLine.readLine();
            java.lang.System.out.println("Indtast stilling");
            String stillingstr = inLine.readLine();
            java.lang.System.out.println("Indtast løn");
            String loenstr = inLine.readLine();
            java.lang.System.out.println("Indtast postnummer");
            String pnstr = inLine.readLine();

            // sender insert'en til db-serveren
            String sql = "insert into person values ('" + cprstr + "', '" + navnstr + "', '" + stillingstr + "', '" + loenstr + "', '" + pnstr + "')";
            java.lang.System.out.println("SQL-streng er " + sql);
            stmt.execute(sql);
            // pænt svar til brugeren
            java.lang.System.out.println("person er nu registreret");
            if (!minConnection.isClosed()) minConnection.close();
        } catch (SQLException e) {
            switch (e.getErrorCode())
            // fejl-kode 547 svarer til en foreign key fejl
            {
                case 547: {
                    if (e.getMessage().contains("cprforeign"))
                        java.lang.System.out.println("cpr-nummer er ikke oprettet");
                    else if (e.getMessage().contains("firmaforeign"))
                        java.lang.System.out.println("firmaet er ikke oprettet");
                    else
                        java.lang.System.out.println("ukendt fremmednøglefejl");
                    break;
                }
                // fejl-kode 2627 svarer til primary key fejl
                case 2627: {
                    java.lang.System.out.println("fejl med primary key");
                    break;
                }
                default:
                    java.lang.System.out.println("fejlSQL:  " + e.getMessage());
            }
            ;
        } catch (Exception e) {
            java.lang.System.out.println("fejl:  " + e.getMessage());
        }
    }

    public static void insertpostnummer() {
        try {
            // indlæsning
            java.lang.System.out.println("Vi vil nu oprette en nyperson");
            java.lang.System.out.println("Indtast postnummer");
            String postnrstr = inLine.readLine();
            java.lang.System.out.println("Indtast postdistrikt");
            String postdistr = inLine.readLine();

            // sender insert'en til db-serveren
            String sql = "insert into postnummer values ('" + postnrstr + "', '" + postdistr + "')";
            java.lang.System.out.println("SQL-streng er " + sql);
            stmt.execute(sql);
            // pænt svar til brugeren
            java.lang.System.out.println("postnummer er nu registreret");
            if (!minConnection.isClosed()) minConnection.close();
        } catch (SQLException e) {
            switch (e.getErrorCode())
            // fejl-kode 547 svarer til en foreign key fejl
            {
                case 547: {
                    if (e.getMessage().contains("postnrforeign"))
                        java.lang.System.out.println("postnummer er ikke oprettet");
                    else
                        java.lang.System.out.println("ukendt fremmednøglefejl");
                    break;
                }
                // fejl-kode 2627 svarer til primary key fejl
                case 2627: {
                    java.lang.System.out.println("fejl med primary key");
                    break;
                }
                default:
                    java.lang.System.out.println("fejlSQL:  " + e.getMessage());
            }
            ;
        } catch (Exception e) {
            java.lang.System.out.println("fejl:  " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            inLine = new BufferedReader(new InputStreamReader(java.lang.System.in));
            //generel opsætning
            //via native driver
            String server = "localhost\\SQLEXPRESS"; //virker måske hos dig      forsøgt med: LAPTOP-4JPSS6LS\SQLEXPRESS
            //virker det ikke - prøv kun med localhost
            String dbnavn = "skolesys";            //virker måske hos dig
            String login = "sa";                     //skal ikke ændres
            String password = "kode";            //skal ændres
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            minConnection = DriverManager.getConnection("jdbc:sqlserver://" + server + ";databaseName=" + dbnavn +
                    ";user=" + login + ";password=" + password + ";");
            //minConnection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=eksempeldb;user=sa;password=torben07;");
            stmt = minConnection.createStatement();
            //Indlæsning og kald af den rigtige metode
            java.lang.System.out.println("Indtast  ");
            java.lang.System.out.println("A for at oprette eksamens forsøg  ");
            java.lang.System.out.println("B for at oprette en eksamensafvikling");
            java.lang.System.out.println("C for at få liste på studerende ve dgiven eksamen og termin");


            String in = inLine.readLine();
            switch (in) {
                case "A": {
                    selectudenparm();
                    break;
                }
                case "B": {
                    selectmedparm();
                    break;
                }
                case "C": {
                    insertmedstring();
                    break;
                }
                default:
                    java.lang.System.out.println("ukendt indtastning");
            }
        } catch (Exception e) {
            java.lang.System.out.println("fejl:  " + e.getMessage());
        }
    }


}



