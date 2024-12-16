import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelUtils {
    private static final String FILE_PATH = "student_grades.xlsx";  // Path to the Excel file
    private static Workbook workbook;
    private static Sheet sheet;

    // Static block to load the Excel file
    static {
        try {
            FileInputStream file = new FileInputStream(new File(FILE_PATH));
            workbook = new XSSFWorkbook(file);  // Create a new workbook instance for the Excel file
            sheet = workbook.getSheetAt(0);  // Get the first sheet from the workbook
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get all users from the Excel file
    public static List<Map<String, String>> getUsers() {
        List<Map<String, String>> users = new ArrayList<>();
        // Loop through the rows of the sheet starting from row 1 (skipping the header)
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {  
            Row row = sheet.getRow(i);
            Map<String, String> user = new HashMap<>();
            user.put("ID", String.valueOf(row.getCell(0).getNumericCellValue()));  // ID
            user.put("Username", row.getCell(1).getStringCellValue());  // Username
            user.put("Password", row.getCell(2).getStringCellValue());  // Password
            user.put("Role", row.getCell(3).getStringCellValue());  // Role
            user.put("Grade", row.getCell(4).getStringCellValue());  // Grade
            user.put("Ticket", row.getCell(5) != null ? row.getCell(5).getStringCellValue() : "");  // Ticket (if present)
            users.add(user);  // Add user to the list
        }
        return users;
    }

    // Authenticate user based on username and password
    public static boolean authenticateUser(String username, String password) {
        List<Map<String, String>> users = getUsers();  // Get all users from the Excel sheet
        // Loop through each user and check if their username and password match
        for (Map<String, String> user : users) {
            if (user.get("Username").equals(username) && user.get("Password").equals(password)) {
                return true;  // Authentication successful
            }
        }
        return false;  // Authentication failed
    }

    // Update the ticket for a student (Admin functionality)
    public static void updateUserTicket(String username, String ticket) throws IOException {
        // Loop through each row (starting from row 1)
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            // Check if the username matches
            if (row.getCell(1).getStringCellValue().equals(username)) {
                row.createCell(5).setCellValue(ticket);  // Set ticket in the last column
                break;
            }
        }
        saveWorkbook();  // Save the changes to the Excel file
    }

    // Update grade for a student (Admin functionality)
    public static void updateGrade(String username, String grade) throws IOException {
        // Loop through each row (starting from row 1)
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            // Check if the username matches
            if (row.getCell(1).getStringCellValue().equals(username)) {
                row.createCell(4).setCellValue(grade);  // Set grade in the 5th column
                break;
            }
        }
        saveWorkbook();  // Save the changes to the Excel file
    }

    // Add a new user (Admin functionality)
    public static void addUser(String username, String password, String role) throws IOException {
        int lastRow = sheet.getPhysicalNumberOfRows();  // Get the last row number
        Row row = sheet.createRow(lastRow);  // Create a new row
        row.createCell(0).setCellValue(lastRow);  // ID (set it to the last row number)
        row.createCell(1).setCellValue(username);  // Username
        row.createCell(2).setCellValue(password);  // Password
        row.createCell(3).setCellValue(role);  // Role
        row.createCell(4).setCellValue("");  // Grade (initially empty)
        row.createCell(5).setCellValue("");  // Ticket (initially empty)
        saveWorkbook();  // Save the changes to the Excel file
    }

    // Method to save the Excel file after making changes
    private static void saveWorkbook() throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH)) {
            workbook.write(fileOut);  // Write the changes to the Excel file
        }
    }

    // Example usage
    public static void main(String[] args) {
        // Example of logging in with username and password
        String username = "user1";  // Replace with user input
        String password = "pass1";  // Replace with user input
        
        // Authenticate the user
        if (authenticateUser(username, password)) {
            System.out.println("Login successful!");
            // Proceed to the next action (e.g., user dashboard)
        } else {
            System.out.println("Invalid username or password.");
        }

        // Example of adding a new user (admin functionality)
        try {
            addUser("newUser", "newPass", "student");  // Add a new user
            System.out.println("New user added.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Example of updating the grade of a user (admin functionality)
        try {
            updateGrade("user1", "A+");  // Update grade for user1
            System.out.println("Grade updated for user1.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Example of updating the ticket for a user (admin functionality)
        try {
            updateUserTicket("user1", "456");  // Update ticket for user1
            System.out.println("Ticket updated for user1.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
