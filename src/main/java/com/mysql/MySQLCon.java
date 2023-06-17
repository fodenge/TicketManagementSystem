package com.mysql;

import java.sql.*;
import java.util.Scanner;

public class MySQLCon {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "Shruti@2916";

        try {
            Scanner sc = new Scanner(System.in);
            int option;
            String query;
            boolean state = true;
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement ;
            System.out.println("Connection is successful to the database : " + url);

            while (state) {
                System.out.println("------ TRAIN CHART------");
                query = "Select * from train";
                statement = con.createStatement();
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    System.out.print("train_number: " + rs.getInt("train_number"));
                    System.out.print("|train_name: " + rs.getString("train_name"));
                    System.out.print("|from: " + rs.getString("from"));
                    System.out.print("|to: " + rs.getString("to"));
                    System.out.println("|fare: "+rs.getInt("fare"));
                }
                rs.close();
                System.out.println("---OPERATIONS---");
                System.out.println("1.Show ticket\n2.Insert data\n3.Delete data by ticket_id\n4.Delete all records\n5.Update name on ticket\n6.Exit");
                System.out.print("Choose an option from above: ");
                option = sc.nextInt();
                if (option == 1) {
                    query = "Select * from ticket";
                    statement = con.createStatement();
                    ResultSet rs1 = statement.executeQuery(query);
                    System.out.println("***ticket Table***");
                    while (rs1.next()) {
                        System.out.print("ticket_id: " + rs1.getInt("ticket_id"));
                        System.out.print("|name: " + rs1.getString("name"));
                        System.out.print("|train_number: "+rs1.getInt("train_number"));
                        System.out.print("|no_of_person: " + rs1.getInt("no_of_person"));
                        System.out.println("|total_fare: " + rs1.getInt("total_fare"));
                    }
                    rs1.close();
                } else if (option == 2) {
                    System.out.print("Your name: ");
                    String name = sc.next();
                    System.out.print("Enter train number: ");
                    String train_number = sc.next();
                    System.out.print("Enter number of person: ");
                    int person = sc.nextInt();
                    query = "Select fare from train where train_number = "+train_number;
                    ResultSet rs1 = statement.executeQuery(query);
                    rs1.next();
                    int total_fare = rs1.getInt(1)*person;
                    query = "Insert into ticket (name,train_number,no_of_person,total_fare) values('" + name + "'," + train_number + "," + person +","+total_fare+ ")";
                    statement.execute(query);
                    System.out.println("Data inserted!!");
                } else if (option == 3) {
                    System.out.print("Enter ticket id for which you want to delete record: ");
                    int id = sc.nextInt();
                    query = "Delete from ticket where ticket_id = " + id;
                    PreparedStatement p = con.prepareStatement(query);
                    p.execute();
                    System.out.println("Data deleted for ticket_id " + id + "!!");
                } else if (option == 4) {
                    System.out.print("Are you sure? Y/N : ");
                    char bool = sc.next().charAt(0);
                    if (bool == 'Y' || bool == 'y') {
                        query = "TRUNCATE TABLE ticket";
                        statement.execute(query);
                        System.out.println("All records deleted!!");
                    } else if (bool == 'N' || bool == 'n') {
                        System.out.println("No deletion operation occurred!!");
                    } else {
                        System.out.println("Invalid input. Database closed!!");
                        state = false;
                    }
                } else if (option == 5) {
                    System.out.print("Update on ticket_id:");
                    int conditionValue = sc.nextInt();
//                    System.out.print("Update attribute: ");
//                    String col = sc.next();
                    System.out.print("Updated value: ");
                    String value = sc.next();
                    query = "Update ticket set name ='" + value + "' where ticket_id = " + conditionValue;
                    PreparedStatement p = con.prepareStatement(query);
                    p.execute();
                    System.out.println("Record updated for empId " + conditionValue + "!!");
                } else if (option == 6) {
                    System.out.println("Database closed!!");
                    state = false;
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
