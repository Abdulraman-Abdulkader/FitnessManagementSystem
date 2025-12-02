import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:3000/FitnessManagementSystem";
    private static final String USER = "postgres";
    private static final String PASSWORD = "@12S34d56";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//    public static void testConnection() {
//        System.out.println("Testing database connection...\n");
//
//        try (Connection conn = getConnection()) {
//
//            if (conn != null) {
//                System.out.println("SUCCESS: Connected to PostgreSQL!");
//            } else {
//                System.out.println("FAILED: Connection returned null.");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}



