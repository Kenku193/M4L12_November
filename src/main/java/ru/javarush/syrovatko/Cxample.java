package ru.javarush.syrovatko;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.sql.rowset.spi.SyncProviderException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class Cxample {

    static BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
    static String answer;
    static boolean quit = false;

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/M4L12";
        String username = "root";
        String password = "qwerty";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            conn.setAutoCommit(false);

            String sql = "SELECT * FROM users";

            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery(sql);

            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet rowset = factory.createCachedRowSet();

            rowset.setTableName("users");

            rowset.populate(result);

            while (!quit) {
                if (!readUser(rowset)) continue;

                updateUser(rowset);

                deleteUser(rowset);

                insertUser(rowset);

                saveChanges(rowset, conn);

                askToQuit();

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    static void readUserInfo(String position, ResultSet result)
            throws SQLException {
        int id = result.getInt("id");
        int age = result.getInt("age");
        String name = result.getString("name");

        String userInfo = "%s, %s, %s\n";
        System.out.format(userInfo, id, age, name);
    }

    static boolean readUser(ResultSet result) throws SQLException, IOException {
        System.out.println("Enter user id: ");
        int row = Integer.parseInt(console.readLine());

        if (result.absolute(row)) {
            readUserInfo("user at row " + row + ": ", result);
            return true;
        } else {
            System.out.println("There's no user at row " + row);
            return false;
        }
    }

    static void updateUser(ResultSet result) throws SQLException, IOException {
        System.out.println("Do you want to update this user (Y/N)?: ");
        answer = console.readLine();

        if (answer.equalsIgnoreCase("Y")) {
            System.out.println("\tUpdate id: ");
            String id = console.readLine();
            System.out.println("\tUpdate age: ");
            String age = console.readLine();
            System.out.println("\tUpdate name: ");
            String name = console.readLine();

            if (!id.equals("")) result.updateString("id", id);
            if (!age.equals("")) result.updateString("age", age);
            if (!name.equals("")) result.updateString("name", name);

            result.updateRow();

            System.out.println("The name has been updated.");
        }

    }

    static void deleteUser(ResultSet result) throws SQLException, IOException {
        System.out.println("Do you want to delete this user (Y/N)?: ");
        answer = console.readLine();

        if (answer.equalsIgnoreCase("Y")) {
            result.deleteRow();

            System.out.println("The user has been removed.");
        }

    }

    static void insertUser(ResultSet result) throws SQLException, IOException {
        System.out.println("Do you want to insert a new user (Y/N)?: ");
        answer = console.readLine();

        if (answer.equalsIgnoreCase("Y")) {
            System.out.println("\tEnter id: ");
            String id = console.readLine();
            System.out.println("\tEnter age: ");
            String age = console.readLine();
            System.out.println("\tEnter name: ");
            String name = console.readLine();

            result.moveToInsertRow();

            result.updateNull("id");
            result.updateString("id", id);
            result.updateString("email", age);
            result.updateString("name", name);

            result.insertRow();
            result.moveToCurrentRow();

            System.out.println("The user has been added.");
        }

    }

    static void saveChanges(CachedRowSet rowset, Connection conn) throws IOException {
        System.out.println("Do you want to save changes (Y/N)?: ");
        answer = console.readLine();

        if (answer.equalsIgnoreCase("Y")) {
            try {
                rowset.acceptChanges(conn);
            } catch (SyncProviderException ex) {
                System.out.println("Error commiting changes to the database: " + ex);
            }
        }
    }

    static void askToQuit() throws IOException {
        System.out.println("Do you want to quit (Y/N)?: ");
        answer = console.readLine();
        quit = answer.equalsIgnoreCase("Y");
    }
}
