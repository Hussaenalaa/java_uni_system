import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            // Admin login and user management
            System.out.println("Welcome! Please log in as Admin.");
            System.out.print("Enter username: ");
            String adminUsername = scanner.nextLine();
            System.out.print("Enter password: ");
            String adminPassword = scanner.nextLine();

            // Hardcoded admin credentials (for simplicity)
            String correctUsername = "admin1";
            String correctPassword = "adminPassword";

            if (adminUsername.equals(correctUsername) && adminPassword.equals(correctPassword)) {
                System.out.println("Logged in as Admin!");

                // Prompt to add a new user
                System.out.println("Would you like to add a new student? (yes/no)");
                String response = scanner.nextLine();

                if ("yes".equalsIgnoreCase(response)) {
                    System.out.print("Enter new student username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter new student password: ");
                    String password = scanner.nextLine();
                    String role = "student";  // All new users will be students by default

                    // Add the new user
                    ExcelUtils.addUser(username, password, role);
                    System.out.println("New student added successfully!");
                }

                // Prompt to update grades for a student
                System.out.println("Would you like to update grades for a student? (yes/no)");
                response = scanner.nextLine();

                if ("yes".equalsIgnoreCase(response)) {
                    System.out.print("Enter student username to update grades: ");
                    String username = scanner.nextLine();
                    String[] grades = new String[5];

                    // Prompt for grades for 5 subjects
                    for (int i = 0; i < 5; i++) {
                        System.out.print("Enter grade for Subject " + (i + 1) + ": ");
                        grades[i] = scanner.nextLine();
                    }

                    // Update the grades and GPA for the student
                    ExcelUtils.updateGradesAndGPA(username, grades);
                    System.out.println("Grades and GPA updated successfully!");
                }

            } else {
                System.out.println("Invalid Admin credentials.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
