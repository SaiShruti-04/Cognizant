import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BasicJdbcConnection {
    // Database credentials
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/jdbcdemo";
    static final String USER = "your_username"; // Replace with your MySQL username
    static final String PASSWORD = "your_password"; // Replace with your MySQL password

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Step 1: Load the JDBC driver (optional for modern JDBC, but good practice)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver loaded successfully.");

            // Step 2: Establish a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Connection established.");

            // Step 3: Create a Statement object
            stmt = conn.createStatement();

            // Step 4: Execute a SELECT query
            String sql = "SELECT id, name, age FROM students";
            System.out.println("Executing query: " + sql);
            rs = stmt.executeQuery(sql);

            // Step 5: Process the ResultSet
            System.out.println("\n--- Student Data ---");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");

                System.out.printf("ID: %d, Name: %s, Age: %d%n", id, name, age);
            }

        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // Step 6: Close resources
            try {
                if (rs != null) rs.close();
            } catch (SQLException se2) {
                // Nothing to do
            }
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
                // Nothing to do
            }
            try {
                if (conn != null) conn.close();
                System.out.println("Connection closed.");
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}