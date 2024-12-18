import java.util.HashMap;

public class Admin {
    private HashMap<String, Student> students;

    public Admin() {
        students = new HashMap<>();
    }

    public void createStudent(String username, String password) {
        if (!students.containsKey(username)) {
            students.put(username, new Student(username, password));
        }
    }

    public void changePassword(String username, String newPassword) {
        if (students.containsKey(username)) {
            students.get(username).setPassword(newPassword);
        }
    }

    public void setDegrees(String username, int[] degrees) {
        if (students.containsKey(username)) {
            students.get(username).setDegrees(degrees);
        }
    }

    public Student getStudent(String username) {
        return students.get(username);
    }

    public boolean isStudentExist(String username) {
        return students.containsKey(username);
    }
}
