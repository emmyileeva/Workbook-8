# Workbook 8

This repository contains two Java projects focused on logging and JDBC + SQL practice.

---

## 🪵 LoggerExercise

A simple Java application that demonstrates how to implement logging using **Log4j2**. It logs messages at various severity levels (debug, info, warn, error, fatal) and outputs them to both the console and a log file.

---

## 🚓 SQLDatabaseExampleGTA (Bay City SQL: Mini-Challenges)

A set of 6 interactive console challenges using **JDBC** and **SQL Server** to query and manipulate data from a fictional Grand Theft Auto-style database. All challenges are accessible through a simple menu.

### 🧩 Challenges Implemented

1. **The Informant**
   - Look up a citizen by their alias.
   - Query: `SELECT Name, Alias, WantedLevel FROM GTA.Citizens WHERE Alias = ?`

2. **Vehicle Registry**
   - View all vehicles by a specific brand.
   - JOIN between Citizens and Vehicles.

3. **Citizen's Vehicle Fleet**
   - List all vehicles owned by a given citizen.
   - Shows type, brand, and whether each vehicle is stolen.

4. **Average Mission Payout**
   - Displays the average payout for all missions.
   - Uses SQL aggregate function `AVG()`.

5. **Agents Without Missions**
   - Lists citizens who have not completed any missions.
   - Uses `LEFT JOIN` with a `NULL` check.

6. **Top 3 Criminals (Stolen Vehicle Missions)**
   - Shows the top 3 citizens who earned the most from missions involving stolen vehicles.
   - Uses multiple JOINs, `SUM()`, and `ORDER BY`.

---



