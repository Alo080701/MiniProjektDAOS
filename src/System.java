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


    public static void insertEksamensForsøg() {
        try {
            // indlæsning
            java.lang.System.out.println("Vi vil nu oprette et nyt eksamensforsøg");
            java.lang.System.out.println("Indtast elev nummer (personen skal være oprettet på forhånd)");
            String elevNrstr=inLine.readLine();
            java.lang.System.out.println("Indtast afviklings ID (afvikling skal være oprettet på forhånd)");
            String afviklingstr=inLine.readLine();
            java.lang.System.out.println("Indtast antal eksamensforsøg");
            String antalForsøgstr=inLine.readLine();
            java.lang.System.out.println("Er bestået? (1 = ja, 0 = nej");
            String beståetstr =inLine.readLine();
            java.lang.System.out.println("Indtast tilstand? (SY (Syg), IM (Ikke Mødt), IA (Ikke Afleveret), VM (Vel Mødt))");
            String tilstandstr=inLine.readLine();
            java.lang.System.out.println("Indtast karakter?");
            String karakterstr = inLine.readLine();
            java.lang.System.out.println("Indtast id for eksamensforsøget");
            String pkIDstr=inLine.readLine();


            String sql = "insert into eksamensForsoeg values (" + pkIDstr + ", " + antalForsøgstr + ", " + beståetstr + ", '" + tilstandstr + "', " + karakterstr + ", " + elevNrstr + ", " + afviklingstr + ")";
            java.lang.System.out.println("SQL-streng er "+ sql);
            stmt.execute(sql);
            java.lang.System.out.println("Eksamensforsøget er nu oprettet");
            if (!minConnection.isClosed()) minConnection.close();
        }
        catch (SQLException e) {
            switch (e.getErrorCode())
            { case 547 : {if (e.getMessage().contains("FK_afviklingforeign"))
                java.lang.System.out.println("Afviklingen er ikke oprettet");
            else
            if (e.getMessage().contains("FK_elevNrforeign"))
                java.lang.System.out.println("Eleven er ikke oprettet");
            else
                java.lang.System.out.println("ukendt fremmednøglefejl");
                break;
            }
                case 2627: {
                    java.lang.System.out.println("Den pågældende eksamensforsøg ID er allerede oprettet");
                    break;
                }
                default: java.lang.System.out.println("fejlSQL:  "+e.getMessage());
            };
        }
        catch (Exception e) {
            java.lang.System.out.println("fejl:  "+e.getMessage());
        }
    };



    public static void InsertEksamensafvikling() {
        try {
            java.lang.System.out.println("Vi vil nu oprette en ny Eksamensafvikling");
            java.lang.System.out.println("Indtast id");
            String iDstr = inLine.readLine();
            java.lang.System.out.println("Indtast er stopprøve (0 = false og 1 = true)");
            String sPstr = inLine.readLine();
            java.lang.System.out.println("Indtast startDato (År-Måned-Dag)");
            String startDstr = inLine.readLine();
            java.lang.System.out.println("Indtast slutDato (År-Måned-Dag)");
            String slutDstr = inLine.readLine();
            java.lang.System.out.println("Indtast Termin (S=Sommer og V=Vinter, EKS. S2024) ");
            String tstr = inLine.readLine();
            java.lang.System.out.println("Indtast eksamens nr");
            String eksNrstr = inLine.readLine();


            String sql = "insert into afvikling values (" + iDstr + ", " + sPstr + ", '" + startDstr + "', '" + slutDstr + "', '" + tstr + "', " + eksNrstr + ")";
            java.lang.System.out.println("SQL-streng er " + sql);
            stmt.execute(sql);

            java.lang.System.out.println("Afviklingen er nu registreret");
            if (!minConnection.isClosed()) minConnection.close();
        } catch (SQLException e) {
            switch (e.getErrorCode())

            {
                case 547: {
                    if (e.getMessage().contains("FK_eksNrforeign"))
                        java.lang.System.out.println("eksamen er ikke oprettet");
                    else
                        java.lang.System.out.println("ukendt fremmednøglefejl");
                    break;
                }
                case 2627: {
                    java.lang.System.out.println("fejl med afviklings id");
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
        try {
            inLine = new BufferedReader(new InputStreamReader(java.lang.System.in));

            String server = "localhost\\SQLEXPRESS";
            String dbnavn = "skolesys";
            String login = "sa";
            String password = "kode";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            minConnection = DriverManager.getConnection("jdbc:sqlserver://" + server + ";databaseName=" + dbnavn +
                    ";user=" + login + ";password=" + password + ";");
            stmt = minConnection.createStatement();

            java.lang.System.out.println("Indtast  ");
            java.lang.System.out.println("A for at oprette eksamens forsøg  ");
            java.lang.System.out.println("B for at oprette en eksamensafvikling");
            java.lang.System.out.println("C for at få liste på studerende ve dgiven eksamen og termin");


            String in = inLine.readLine();
            switch (in) {
                case "A": {
                    insertEksamensForsøg();
                    break;
                }
                case "B": {
                    InsertEksamensafvikling();
                    break;
                }
                case "C": {
                    //insertmedstring();
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
