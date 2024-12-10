import java.util.ArrayList;
import java.util.Scanner;

public class StudentDegreeSystem {

    // Admin credentials
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    // Student credentials
    private static final String STUDENT_USERNAME = "student";
    private static final String STUDENT_PASSWORD = "1234";

    // List of students
    private ArrayList<Student> students;

    public static void main(String[] args) {
        StudentDegreeSystem system = new StudentDegreeSystem();
        system.run();
    }

    public StudentDegreeSystem() {
        students = new ArrayList<>();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        // Login functionality (Admin and Student)
        System.out.println("Welcome to the Student Degree Management System!");
        System.out.println("Please choose the login option:");

        System.out.println("1. Login as Admin");
        System.out.println("2. Login as Student");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            // Admin Login
            if (adminLogin(scanner)) {
                showAdminPanel(scanner);
            } else {
                System.out.println("Invalid admin credentials.");
            }
        } else if (choice == 2) {
            // Student Login
            if (studentLogin(scanner)) {
                showStudentPanel();
            } else {
                System.out.println("Invalid student credentials.");
            }
        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }

    private boolean adminLogin(Scanner scanner) {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        return username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD);
    }

    private boolean studentLogin(Scanner scanner) {
        System.out.print("Enter Student Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Student Password: ");
        String password = scanner.nextLine();

        return username.equals(STUDENT_USERNAME) && password.equals(STUDENT_PASSWORD);
    }

    private void showAdminPanel(Scanner scanner) {
        System.out.println("\nWelcome to the Admin Panel!");
        boolean running = true;

        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student Grade");
            System.out.println("4. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    updateStudentGrade(scanner);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void showStudentPanel() {
        System.out.println("\nWelcome, Student!");
        // Add functionality for students if needed (e.g., view grades)
    }

    private void addStudent(Scanner scanner) {
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Roll Number: ");
        String rollNo = scanner.nextLine();
        System.out.print("Enter Grade: ");
        String grade = scanner.nextLine();

        students.add(new Student(name, rollNo, grade));
        System.out.println("Student added successfully!");
    }

    private void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("\nList of Students:");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    private void updateStudentGrade(Scanner scanner) {
        System.out.print("Enter Roll Number of the student to update: ");
        String rollNo = scanner.nextLine();
        System.out.print("Enter new Grade: ");
        String newGrade = scanner.nextLine();

        boolean found = false;
        for (Student student : students) {
            if (student.getRollNo().equals(rollNo)) {
                student.setGrade(newGrade);
                System.out.println("Grade updated successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student with roll number " + rollNo + " not found.");
        }
    }

    // Student class to represent each student
    static class Student {
        private String name;
        private String rollNo;
        private String grade;

        public Student(String name, String rollNo, String grade) {
            this.name = name;
            this.rollNo = rollNo;
            this.grade = grade;
        }

        public String getRollNo() {
            return rollNo;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        @Override
        public String toString() {
            return "Name: " + name + ", Roll No: " + rollNo + ", Grade: " + grade;
        }
    }
}
