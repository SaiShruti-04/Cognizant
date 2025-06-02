import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionHandlingExample {

    static final String JDBC_URL = "jdbc:mysql://localhost:3306/bankingdb";
    static final String USER = "your_username"; // Replace with your MySQL username
    static final String PASSWORD = "your_password"; // Replace with your MySQL password

    public static void main(String[] args) {
        // Initial balances
        System.out.println("Initial Balances:");
        displayBalances();

        System.out.println("\n--- Attempting successful transfer (101 -> 102, $200) ---");
        transferMoney(101, 102, 200.00);
        System.out.println("\nBalances after successful transfer:");
        displayBalances();

        System.out.println("\n--- Attempting failed transfer (101 -> 102, $2000 - insufficient funds) ---");
        transferMoney(101, 102, 2000.00); // This should fail and rollback
        System.out.println("\nBalances after failed transfer attempt (should be rolled back):");
        displayBalances();

        System.out.println("\n--- Attempting failed transfer (101 -> 999 - invalid account) ---");
        transferMoney(101, 999, 50.00); // This should fail and rollback
        System.out.println("\nBalances after failed transfer attempt (should be rolled back):");
        displayBalances();
    }

    public static void transferMoney(int fromAccountId, int toAccountId, double amount) {
        Connection conn = null;
        PreparedStatement debitStmt = null;
        PreparedStatement creditStmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            conn.setAutoCommit(false); // Start transaction

            // 1. Check sender's balance
            String checkBalanceSql = "SELECT balance FROM accounts WHERE account_id = ?";
            PreparedStatement checkBalanceStmt = conn.prepareStatement(checkBalanceSql);
            checkBalanceStmt.setInt(1, fromAccountId);
            rs = checkBalanceStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Error: Sender account " + fromAccountId + " not found.");
                conn.rollback();
                return;
            }
            double senderBalance = rs.getDouble("balance");
            if (senderBalance < amount) {
                System.out.println("Error: Insufficient funds in account " + fromAccountId + " to transfer $" + amount);
                conn.rollback(); // Rollback due to insufficient funds
                return;
            }

            // 2. Debit from sender
            String debitSql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
            debitStmt = conn.prepareStatement(debitSql);
            debitStmt.setDouble(1, amount);
            debitStmt.setInt(2, fromAccountId);
            int debitRows = debitStmt.executeUpdate();

            if (debitRows == 0) {
                System.out.println("Error: Failed to debit from account " + fromAccountId);
                conn.rollback(); // Rollback if debit failed
                return;
            }

            // Simulate a potential error after debit but before credit
            // if (amount > 1000) throw new SQLException("Simulating error after debit");

            // 3. Credit to receiver
            String creditSql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
            creditStmt = conn.prepareStatement(creditSql);
            creditStmt.setDouble(1, amount);
            creditStmt.setInt(2, toAccountId);
            int creditRows = creditStmt.executeUpdate();

            if (creditRows == 0) {
                System.out.println("Error: Receiver account " + toAccountId + " not found. Rolling back.");
                conn.rollback(); // Rollback if credit failed (e.g., target account doesn't exist)
                return;
            }

            conn.commit(); // Commit the transaction if both operations succeed
            System.out.printf("Successfully transferred $%.2f from account %d to account %d.%n", amount, fromAccountId, toAccountId);

        } catch (SQLException e) {
            System.err.println("SQL Error during transaction: " + e.getMessage());
            try {
                if (conn != null) {
                    System.out.println("Transaction rolled back.");
                    conn.rollback(); // Rollback on any SQL error
                }
            } catch (SQLException rbse) {
                System.err.println("Error during rollback: " + rbse.getMessage());
            }
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (debitStmt != null) debitStmt.close();
                if (creditStmt != null) creditStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public static void displayBalances() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT account_id, balance FROM accounts ORDER BY account_id")) {

            while (rs.next()) {
                System.out.printf("Account ID: %d, Balance: $%.2f%n", rs.getInt("account_id"), rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            System.err.println("Error displaying balances: " + e.getMessage());
        }
    }
}