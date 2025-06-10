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
            System.out.println("\n----------Bay City SQL Operations----------");
            System.out.println("1) Challenge 1: The Informant (Lookup by Alias)");
            System.out.println("2) Challenge 2: Vehicle Registry (Lookup by Brand)");
            System.out.println("3) Challenge 3: Citizen's Vehicle Fleet");
            System.out.println("4) Challenge 4: Average Mission Payout");
            System.out.println("5) Challenge 5: Inactive Agents (No Missions)");
            System.out.println("0) Exit");
            System.out.print("\nChoose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1 -> runInformantCheck();
                case 2 -> runVehicleRegistry();
                case 3 -> runCitizenFleetReport();
                case 4 -> runAveragePayout();
                case 5 -> runInactiveAgentReport();
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Challenge 1: The Informant (Lookup by Alias)
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

    // Challenge 2: Vehicle Registry (Lookup by Brand)
    public static void runVehicleRegistry() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter vehicle brand to search for: ");
        String brand = scanner.nextLine();

        String query = "SELECT c.Name, v.Type, v.Brand FROM GTA.Vehicles v " + "JOIN GTA.Citizens c ON v.OwnerID = c.CitizenID WHERE v.Brand = ?";

        try (
                Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setString(1, brand);
            ResultSet rs = stmt.executeQuery();

            System.out.printf("\n--- Vehicles of Brand: %s ---\n", brand);
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("Owner: %-20s Type: %-15s Brand: %-15s\n",
                        rs.getString("Name"),
                        rs.getString("Type"),
                        rs.getString("Brand"));
                System.out.println("-------------------------------");
            }

            if (!found) {
                System.out.println("No vehicles found for that brand.");
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Challenge 3: Citizen's Vehicle Fleet
    public static void runCitizenFleetReport() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter citizen's name to see their vehicle fleet: ");
        String name = scanner.nextLine();

        String query = "SELECT v.Type, v.Brand, v.IsStolen " + "FROM GTA.Vehicles v " + "JOIN GTA.Citizens c ON v.OwnerID = c.CitizenID " + "WHERE c.Name = ?";

        try (
                Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            System.out.printf("\n--- Vehicle Fleet for %s ---\n", name);
            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.printf("Type: %-15s Brand: %-15s Stolen: %b\n",
                        rs.getString("Type"),
                        rs.getString("Brand"),
                        rs.getBoolean("IsStolen"));
                System.out.println("-------------------------------");
            }

            if (!found) {
                System.out.println("No vehicles found for this citizen.");
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Challenge 4: Average Mission Payout
    public static void runAveragePayout() {
        String query = "SELECT AVG(Reward) AS AveragePayout FROM GTA.Missions";

        try (
                Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()
        ) {
            System.out.println("\n--- Mission Payout Analysis ---");

            if (rs.next()) {
                double avg = rs.getDouble("AveragePayout");
                System.out.printf("The average mission payout is: $%.2f\n", avg);
                System.out.println("-------------------------------");
            } else {
                System.out.println("No mission data available.");
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Challenge 5: Inactive Agents (No Missions)
    public static void runInactiveAgentReport() {
        String query =
                "SELECT c.Name FROM GTA.Citizens c " + "LEFT JOIN GTA.Missions m ON c.CitizenID = m.MissionID " + "WHERE m.MissionID IS NULL";

        try (
                Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()
        ) {
            System.out.println("\n--- Agents With No Recorded Missions ---");
            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.printf("Name: %s\n", rs.getString("Name"));
            }

            if (!found) {
                System.out.println("Every citizen has completed at least one mission.");
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
