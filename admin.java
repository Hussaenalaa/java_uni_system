import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelUtils {
    private static final String FILE_PATH = "student_grades.xlsx";
    private static Workbook workbook;
    private static Sheet sheet;

    static {
        try {
            FileInputStream file = new FileInputStream(new File(FILE_PATH));
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheetAt(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get all users from the Excel file
    public static List<Map<String, String>> getUsers() {
        List<Map<String, String>> users = new ArrayList<>();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {  // Skip the header row
            Row row = sheet.getRow(i);
            Map<String, String> user = new HashMap<>();
            user.put("ID", row.getCell(0).getStringCellValue());
            user.put("Username", row.getCell(1).getStringCellValue());
            user.put("Password", row.getCell(2).getStringCellValue());
            user.put("Role", row.getCell(3).getStringCellValue());
            user.put("Subject1", row.getCell(4).getStringCellValue());
            user.put("Subject2", row.getCell(5).getStringCellValue());
            user.put("Subject3", row.getCell(6).getStringCellValue());
            user.put("Subject4", row.getCell(7).getStringCellValue());
            user.put("Subject5", row.getCell(8).getStringCellValue());
            user.put("GPA", row.getCell(9) != null ? row.getCell(9).getStringCellValue() : "");
            user.put("Ticket", row.getCell(10) != null ? row.getCell(10).getStringCellValue() : "");
            users.add(user);
        }
        return users;
    }

    // Update the grades for a student and calculate GPA
    public static void updateGradesAndGPA(String username, String[] grades) throws IOException {
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {  // Skip the header row
            Row row = sheet.getRow(i);
            if (row.getCell(1).getStringCellValue().equals(username)) {
                // Update grades for each subject
                for (int j = 0; j < grades.length; j++) {
                    row.getCell(4 + j).setCellValue(grades[j]);
                }

                // Calculate GPA based on grades
                double gpa = calculateGPA(grades);
                row.createCell(9).setCellValue(gpa); // Set the GPA

                break;
            }
        }
        saveWorkbook();
    }

    // Calculate GPA based on subject grades
    private static double calculateGPA(String[] grades) {
        double totalPoints = 0;
        int subjectCount = grades.length;
        
        // Define grade to point mapping
        Map<String, Double> gradePoints = new HashMap<>();
        gradePoints.put("A", 4.0);
        gradePoints.put("B", 3.0);
        gradePoints.put("C", 2.0);
        gradePoints.put("D", 1.0);
        gradePoints.put("F", 0.0);
        
        for (String grade : grades) {
            totalPoints += gradePoints.getOrDefault(grade, 0.0);
        }
        
        // Calculate and return GPA
        return totalPoints / subjectCount;
    }

    // Add a new user (Admin functionality)
    public static void addUser(String username, String password, String role) throws IOException {
        int lastRow = sheet.getPhysicalNumberOfRows();
        Row row = sheet.createRow(lastRow);
        row.createCell(0).setCellValue(lastRow); // ID
        row.createCell(1).setCellValue(username);
        row.createCell(2).setCellValue(password);
        row.createCell(3).setCellValue(role);
        row.createCell(4).setCellValue(""); // Subject 1 grade initially empty
        row.createCell(5).setCellValue(""); // Subject 2 grade initially empty
        row.createCell(6).setCellValue(""); // Subject 3 grade initially empty
        row.createCell(7).setCellValue(""); // Subject 4 grade initially empty
        row.createCell(8).setCellValue(""); // Subject 5 grade initially empty
        row.createCell(9).setCellValue(""); // GPA initially empty
        row.createCell(10).setCellValue(""); // Ticket initially empty
        saveWorkbook();
    }

    private static void saveWorkbook() throws IOException {
        FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
        workbook.write(fileOut);
        fileOut.close();
    }
}
