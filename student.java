public class Student {
    private String username;
    private String password;
    private int[] degrees;

    public Student(String username, String password) {
        this.username = username;
        this.password = password;
        this.degrees = new int[5]; // 5 subjects
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setDegrees(int[] degrees) {
        this.degrees = degrees;
    }

    public int[] getDegrees() {
        return degrees;
    }
}
