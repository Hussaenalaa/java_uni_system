import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//
public class LoginPage {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Admin admin;3

    public LoginPage(Admin admin) {
        this.admin = admin;
        frame = new JFrame("Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username: ");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (username.equals("admin") && password.equals("admin")) {
                    openAdminPanel();
                } else if (admin.isStudentExist(username)) {
                    Student student = admin.getStudent(username);
                    if (student.getPassword().equals(password)) {
                        openStudentPanel(student);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid password!");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Student not found!");
                }
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void openAdminPanel() {
        // Admin Panel for creating students, changing passwords, and setting degrees
        JFrame adminFrame = new JFrame("Admin Panel");
        adminFrame.setSize(400, 300);
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayout(5, 2));

        JLabel studentUsernameLabel = new JLabel("Student Username: ");
        JTextField studentUsernameField = new JTextField();
        JLabel studentPasswordLabel = new JLabel("Password: ");
        JPasswordField studentPasswordField = new JPasswordField();
        JButton createButton = new JButton("Create Student");
        
        JLabel changePasswordLabel = new JLabel("Change Password: ");
        JTextField changePasswordUsernameField = new JTextField();
        JPasswordField changePasswordField = new JPasswordField();
        JButton changePasswordButton = new JButton("Change Password");
        
        JLabel setDegreesLabel = new JLabel("Set Degrees for Student: ");
        JTextField setDegreesUsernameField = new JTextField();
        JTextField degreeField1 = new JTextField("Degree 1");
        JTextField degreeField2 = new JTextField("Degree 2");
        JTextField degreeField3 = new JTextField("Degree 3");
        JTextField degreeField4 = new JTextField("Degree 4");
        JTextField degreeField5 = new JTextField("Degree 5");
        JButton setDegreesButton = new JButton("Set Degrees");

        // Create Student Action
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = studentUsernameField.getText();
                String password = new String(studentPasswordField.getPassword());
                admin.createStudent(username, password);
                JOptionPane.showMessageDialog(adminFrame, "Student created: " + username);
            }
        });

        // Change Password Action
        changePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = changePasswordUsernameField.getText();
                String newPassword = new String(changePasswordField.getPassword());
                admin.changePassword(username, newPassword);
                JOptionPane.showMessageDialog(adminFrame, "Password changed for: " + username);
            }
        });

        // Set Degrees Action
        setDegreesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = setDegreesUsernameField.getText();
                try {
                    int[] degrees = new int[5];
                    degrees[0] = Integer.parseInt(degreeField1.getText());
                    degrees[1] = Integer.parseInt(degreeField2.getText());
                    degrees[2] = Integer.parseInt(degreeField3.getText());
                    degrees[3] = Integer.parseInt(degreeField4.getText());
                    degrees[4] = Integer.parseInt(degreeField5.getText());
                    admin.setDegrees(username, degrees);
                    JOptionPane.showMessageDialog(adminFrame, "Degrees set for: " + username);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(adminFrame, "Invalid degree input.");
                }
            }
        });

        adminPanel.add(studentUsernameLabel);
        adminPanel.add(studentUsernameField);
        adminPanel.add(studentPasswordLabel);
        adminPanel.add(studentPasswordField);
        adminPanel.add(createButton);
        adminPanel.add(new JLabel()); // Empty cell
        adminPanel.add(changePasswordLabel);
        adminPanel.add(changePasswordUsernameField);
        adminPanel.add(changePasswordField);
        adminPanel.add(changePasswordButton);
        adminPanel.add(setDegreesLabel);
        adminPanel.add(setDegreesUsernameField);
        adminPanel.add(degreeField1);
        adminPanel.add(degreeField2);
        adminPanel.add(degreeField3);
        adminPanel.add(degreeField4);
        adminPanel.add(degreeField5);
        adminPanel.add(setDegreesButton);

        adminFrame.add(adminPanel);
        adminFrame.setVisible(true);
    }

    private void openStudentPanel(Student student) {
        // Open Student Panel
        JOptionPane.showMessageDialog(frame, "Welcome " + student.getUsername() + "!");
        StringBuilder degrees = new StringBuilder();
        int[] studentDegrees = student.getDegrees();
        for (int i = 0; i < studentDegrees.length; i++) {
            degrees.append("Subject ").append(i + 1).append(": ").append(studentDegrees[i]).append("\n");
        }
        JOptionPane.showMessageDialog(frame, "Your degrees:\n" + degrees.toString());
    }

    public static void main(String[] args) {
        Admin admin = new Admin();
        admin.createStudent("student1", "password1");
        admin.getStudent("student1").setDegrees(new int[]{90, 85, 78, 92, 88});
        new LoginPage(admin);
    }
}
