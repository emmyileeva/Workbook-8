package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main {

    // 1. Define the JDBC connection string (URL) with necessary configuration options.
    // This includes:
    // - The database server address and port
    // - The database name
    // - Login credentials (username and password)
    // - Security settings (encryption, certificate validation)
    // - Connection timeout settings
    private static final String DB_URL = "jdbc:sqlserver://skills4it.database.windows.net:1433;" +
            "database=Courses;" +
            "user=gtareader@skills4it;" +
            "password=StrongPass!2025;" +
            "encrypt=true;" +
            "trustServerCertificate=false;" +
            "loginTimeout=30;";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Bay City SQL Operations");
            System.out.println("1) Challenge 1: The Informant (Lookup by Alias)");
            System.out.println("0) Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1 -> runInformantCheck();
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void runInformantCheck() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the alias of the citizen to look up: ");
        String alias = scanner.nextLine();

        String query = "SELECT Name, Alias, WantedLevel FROM GTA.Citizens WHERE Alias = ? ";

        try (
                Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setString(1, alias);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n--- Citizen Profile ---");
                System.out.printf("Name: %s\nAlias: %s\nWanted Level: %d\n",
                        rs.getString("Name"),
                        rs.getString("Alias"),
                        rs.getInt("WantedLevel"));
                System.out.println("------------------------");
            } else {
                System.out.println("\nNo citizen found with that alias.");
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}