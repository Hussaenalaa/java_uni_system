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
            user.put("Grade", row.getCell(4).getStringCellValue());
            user.put("Ticket", row.getCell(5) != null ? row.getCell(5).getStringCellValue() : "");
            users.add(user);
        }
        return users;
    }

    // Update the ticket for a student
    public static void updateUserTicket(String username, String ticket) throws IOException {
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {  // Skip the header row
            Row row = sheet.getRow(i);
            if (row.getCell(1).getStringCellValue().equals(username)) {
                row.createCell(5).setCellValue(ticket);
                break;
            }
        }
        saveWorkbook();
    }

    // Update grade for a student (Admin functionality)
    public static void updateGrade(String username, String grade) throws IOException {
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {  // Skip the header row
            Row row = sheet.getRow(i);
            if (row.getCell(1).getStringCellValue().equals(username)) {
                row.createCell(4).setCellValue(grade);
                break;
            }
        }
        saveWorkbook();
    }

    // Add a new student (Admin functionality)
    public static void addUser(String username, String password, String role) throws IOException {
        int lastRow = sheet.getPhysicalNumberOfRows();
        Row row = sheet.createRow(lastRow);
        row.createCell(0).setCellValue(lastRow); // ID
        row.createCell(1).setCellValue(username);
        row.createCell(2).setCellValue(password);
        row.createCell(3).setCellValue(role);
        row.createCell(4).setCellValue(""); // Grade initially empty
        row.createCell(5).setCellValue(""); // Ticket initially empty
        saveWorkbook();
    }

    private static void saveWorkbook() throws IOException {
        FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
        workbook.write(fileOut);
        fileOut.close();
    }
}
