import java.sql.*;
import java.util.Scanner;

public class Main {
    private static Connection connection = null;
    private static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {

        {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/students";
                String username = "root";
                String password = "password";
                connection = DriverManager.getConnection(url, username, password);
                //if(connection != null) System.out.println("Connection successful");

                boolean isrunning = true;
                while (isrunning) {
                    System.out.println("********************************************");
                    System.out.println("WELCOME TO STUDENT MANAGEMENT SYSTEM.");
                    System.out.println("Enter a Choice");
                    System.out.println(" Press '1' to Insert Record");
                    System.out.println(" Press '2' to Display a Record");
                    System.out.println(" Press '3' to Update Record ");
                    System.out.println(" Press '4' to Delete the record.");
                    System.out.println(" Press '5' to Exit.");

                    int choice = Integer.parseInt(sc.nextLine());
                    switch (choice) {

                        case 1:
                            System.out.println();
                            //resetRollNo();
                            insertRecord();
                            break;

                        case 2:
                            System.out.println();
                            selectRecords();
                            break;

                        case 3:
                            System.out.println();
                            updateRecord();
                            break;

                        case 4:
                            System.out.println();
                            deleteRecord();
                            break;

                        case 5:
                            System.out.println("Thank You, Visit Again!!");
                            isrunning = false;
                            break;
                    }
                }

            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException("Something went wrong");
            }
        }
    }


    private static void insertRecord() throws SQLException {
        //System.out.println("Insert method called");
        String sql = "INSERT INTO student_info(name,percentage,address) VALUES(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println("Enter Name: ");
        preparedStatement.setString(1, sc.nextLine());

        System.out.println("Enter Percentage: ");
        preparedStatement.setDouble(2, Double.parseDouble(sc.nextLine()));

        System.out.println("Enter the Address: ");
        preparedStatement.setString(3, sc.nextLine());
        int rows = preparedStatement.executeUpdate();

        if (rows > 0) {
            System.out.println("Inserted Successfully");
        }
    }

    private static void selectRecords() throws SQLException {
        //System.out.println("Select records called");
        System.out.println("Enter the Roll Number to find information.");
        int number = Integer.parseInt(sc.nextLine());
        String sql = "SELECT * FROM student_info WHERE roll_no = " + number;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            // System.out.println("Record not found");
            int roll_no = resultSet.getInt("roll_no");
            String name = resultSet.getString("name");
            double percentage = resultSet.getDouble("percentage");
            String address = resultSet.getString("address");

            System.out.println("Roll no is: " + roll_no);
            System.out.println("name  is: " + name);
            System.out.println("percentage  is: " + percentage);
            System.out.println("address  is: " + address);
        } else {
            System.out.println("No record Found...");
        }
    }

    private static void updateRecord() throws SQLException {
        System.out.println("Enter the roll no to update the information of the student: ");
        int input_rollNo = Integer.parseInt(sc.nextLine());
        String sql = "SELECT * FROM student_info where roll_no =" + input_rollNo;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            int roll_no = resultSet.getInt("roll_no");
            String name = resultSet.getString("name");
            double percentage = resultSet.getDouble("percentage");
            String address = resultSet.getString("address");

            System.out.println("CURRENT INFORMATION OF THE STUDENT: ");
            System.out.println("Roll no is: " + roll_no);
            System.out.println("name  is: " + name);
            System.out.println("percentage  is: " + percentage);
            System.out.println("address  is: " + address);
            System.out.println("WHAT DO YOU WANT TO UPDATE");
            System.out.println("1. Name");
            System.out.println("2. Percentage");
            System.out.println("3. Address");

            int choice = Integer.parseInt(sc.nextLine());

            String sqlQuery = "update student_info set ";
            switch (choice) {
                case 1:
                    System.out.println("Enter New Name");
                    String newName = sc.nextLine();
                    System.out.println("Updating the Name: ");
                    sqlQuery = sqlQuery + "name = ? where roll_no = " + input_rollNo;
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                    preparedStatement.setString(1, newName);
                    int rows = preparedStatement.executeUpdate();
                    if (rows > 0) System.out.println("Record Updated!!");
                    break;

                case 2:
                    System.out.println("Enter New Percentage: ");
                    double newPercentage = Double.parseDouble(sc.nextLine());
                    System.out.println("Updating the percentage: ");
                    sqlQuery = sqlQuery + "percentage = ? where roll_no = " + input_rollNo;
                    PreparedStatement preparedStatement1 = connection.prepareStatement(sqlQuery);
                    preparedStatement1.setDouble(1, newPercentage);
                    int rows1 = preparedStatement1.executeUpdate();
                    if (rows1 > 0) System.out.println("Record Updated!!");
                    break;
                case 3:
                    System.out.println("Enter New Address");
                    String newAddress = sc.nextLine();
                    System.out.println("Updating the Address: ");
                    sqlQuery = sqlQuery + "address = ? where roll_no = " + input_rollNo;
                    PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery);
                    preparedStatement2.setString(1, newAddress);
                    int rows2 = preparedStatement2.executeUpdate();
                    if (rows2 > 0) System.out.println("Record Updated!!");
                    break;
            }
        } else {
            System.out.println("Records not found");
        }
    }

    private static void deleteRecord() throws SQLException {
        System.out.println("Enter the roll No to be Deleted: ");
        int rollDelete = Integer.parseInt(sc.nextLine());
        String sql = "delete from student_info where roll_no = " + rollDelete;

        Statement statement = connection.createStatement();
        int rows = statement.executeUpdate(sql);
        if (rows > 0) {
            System.out.println("Deleted successfully.");
        }
    }
}