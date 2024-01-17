import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.01:3306/mysql";
    static final String USER = "root";
    static final String PASS = "password";// i have not given the real password  for my server but the code will work
    static final int MAX_RECORDS = 5;

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();


            DatabaseMetaData meta = conn.getMetaData();
            ResultSet tables = meta.getTables(null, null, "employee", null);

            if (tables.next()) {

                String deleteDataSQL = "DELETE FROM employee";
                stmt.executeUpdate(deleteDataSQL);

            }


            String createTableSQL = "CREATE TABLE IF NOT EXISTS employee (" +
                    "empcode INT, " +
                    "empname VARCHAR(255), " +
                    "empage INT, " +
                    "esalary DOUBLE)";
            stmt.executeUpdate(createTableSQL);
            for (int i = 1; i <= MAX_RECORDS; i++) {
                System.out.println("Enter details for Employee " + i + ":");


                System.out.print("Emp Code: ");
                int empCode = scanner.nextInt();

                System.out.print("Emp Name: ");
                String empName = scanner.next();
                System.out.print("Employee Age: ");
                int empAge = scanner.nextInt();
                System.out.print("Employee Salary: ");
                double empSalary = scanner.nextDouble();

                String insertDataSQL = "INSERT INTO employee VALUES (" +
                        empCode + ", '" + empName + "', " + empAge + ", " + empSalary + ")";
                stmt.executeUpdate(insertDataSQL);
            }

            System.out.println("Data inserted successfully.");

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            scanner.close();
        }
    }
}
