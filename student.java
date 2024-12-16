import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class StudentManager {
    private static final String FILE_PATH = "student_grades.xlsx";  // Path to the Excel file
    private static Workbook workbook;
    private static Sheet sheet;

    // Static block to initialize the workbook and sheet
    static {
        try {
            FileInputStream file = new FileInputStream(new File(FILE_PATH));
            workbook = new XSSFWorkbook(file);  // Create a workbook instance
            sheet = workbook.getSheetAt(0);  // Get the first sheet of the workbook
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get all student records from the Excel file
    private static List<Map<String, String>> getStudents() {
        List<Map<String, String>> students = new ArrayList<>();
        // Loop through rows of the sheet starting from row 1 (to skip headers)
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            Map<String, String> student = new HashMap<>();
            student.put("ID", String.valueOf(row.getCell(0).getNumericCellValue()));  // Student ID
            student.put("Username", row.getCell(1).getStringCellValue());  // Username
            student.put("Password", row.getCell(2).getStringCellValue());  // Password
            student.put("Grade", row.getCell(4).getStringCellValue());  // Grade
            student.put("Ticket", row.getCell(5) != null ? row.getCell(5).getStringCellValue() : "");  // Ticket number
            students.add(student);
        }
        return students;
    }

    // Authenticate student login using username and password
    public static boolean login(String username, String password) {
        List<Map<String, String>> students = getStudents();
        // Loop through the student list and check for valid username and password
        for (Map<String, String> student : students) {
            if (student.get("Username").equals(username) && student.get("Password").equals(password)) {
                return true;  // Successful login
            }
        }
        return false;  // Failed login
    }

    // Get the details of a specific student
    public static Map<String, String> getStudentDetails(String username) {
        List<Map<String, String>> students = getStudents();
        // Search for the student by username and return their details
        for (Map<String, String> student : students) {
            if (student.get("Username").equals(username)) {
                return student;  // Return student details if found
            }
        }
        return null;  // If student not found
    }

    // Request a new ticket (student's request)
    public static void requestNewTicket(String username, String newTicket) throws IOException {
        // Loop through each student to find the username and update the ticket
        boolean found = false;
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (row.getCell(1).getStringCellValue().equals(username)) {
                found = true;
                // Check if a ticket exists and update it
                if (row.getCell(5) != null) {
                    row.getCell(5).setCellValue(newTicket);  // Replace existing ticket
                } else {
                    row.createCell(5).setCellValue(newTicket);  // Create a new ticket if not already present
                }
                System.out.println("Ticket request for " + username + " has been successfully processed.");
                break;
            }
        }
        
        if (!found) {
            System.out.println("Username not found. Ticket request failed.");
        }

        saveChanges();  // Save the changes to the Excel file
    }

    // Save the workbook (after making any changes)
    private static void saveChanges() throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH)) {
            workbook.write(fileOut);  // Write changes to the Excel file
        }
    }

    // Main method for testing purposes
    public static void main(String[] args) {
        // Simulate login with username and password
        String username = "student1";  // Replace with actual username input
        String password = "password123";  // Replace with actual password input

        // Check if login is successful
        if (login(username, password)) {
            System.out.println("Login successful!");

            // Fetch and display student details
            Map<String, String> studentDetails = getStudentDetails(username);
            if (studentDetails != null) {
                System.out.println("Student Details:");
                System.out.println("Username: " + studentDetails.get("Username"));
                System.out.println("Grade: " + studentDetails.get("Grade"));
                System.out.println("Ticket: " + studentDetails.get("Ticket"));
            }

            // Simulate ticket request (for demonstration purposes)
            try {
                String newTicket = "XYZ123";
                requestNewTicket(username, newTicket);  // Request a new ticket
            } catch (IOException e) {
                System.out.println("Error processing ticket request: " + e.getMessage());
            }

        } else {
            System.out.println("Invalid username or password.");
        }
    }
}
