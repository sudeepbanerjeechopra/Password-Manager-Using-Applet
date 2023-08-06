import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class PasswordManagerApplet extends Applet implements ActionListener {
    private Map<String, String> passwordDatabase;
    private JTextField websiteField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public PasswordManagerApplet() {
        passwordDatabase = new HashMap<>();
    }

    @Override
    public void init() {
        setLayout(new GridLayout(7, 2));
        JLabel websiteLabel = new JLabel("Website/Service:");
        JLabel usernameLabel = new JLabel("Username:");
        websiteField = new JTextField(20);
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordField.setEchoChar('*');
        JButton generateButton = new JButton("Generate Password");
        generateButton.addActionListener(this);
        JButton addButton = new JButton("Add Password");
        addButton.addActionListener(this);
        JButton retrieveButton = new JButton("Retrieve Password");
        retrieveButton.addActionListener(this);

        add(websiteLabel);
        add(websiteField);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(generateButton);
        add(addButton);
        add(retrieveButton);

        // Add the "CSE(AIML) Team - 13" label with bold and bigger font
        JLabel teamLabel = new JLabel("CSE(AIML) Team - 13");
        teamLabel.setFont(new Font("Arial", Font.BOLD, 16));
        teamLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(teamLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("Generate Password".equals(command)) {
            generateRandomPassword();
        } else if ("Add Password".equals(command)) {
            addPassword();
        } else if ("Retrieve Password".equals(command)) {
            retrievePassword();
        }
    }

    private void generateRandomPassword() {
        String generatedPassword = generateRandomString(12); // Change the length as needed
        passwordField.setText(generatedPassword);
    }

    private String generateRandomString(int length) {
        String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * charset.length());
            randomString.append(charset.charAt(index));
        }

        return randomString.toString();
    }

    private void addPassword() {
        String website = websiteField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Check for duplicate passwords
        for (String storedWebsite : passwordDatabase.keySet()) {
            String storedPassword = passwordDatabase.get(storedWebsite);
            if (password.equals(storedPassword)) {
                showMessageBox("Password already exists for another website!", "Warning", JOptionPane.WARNING_MESSAGE);
                return; // Stop further processing
            }
        }

        passwordDatabase.put(website, password);
        showMessageBox("Password added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }

    private void retrievePassword() {
        String website = websiteField.getText();
        String password = passwordDatabase.get(website);

        if (password != null) {
            showMessageBox("Username: " + usernameField.getText() + "\nPassword: " + password, "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            showMessageBox("Password not found for " + website, "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearFields() {
        websiteField.setText("");
        usernameField.setText("");
        passwordField.setText("");
    }

    private void showMessageBox(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Password Manager Applet");
            PasswordManagerApplet applet = new PasswordManagerApplet();
            applet.init();
            frame.setContentPane(applet);
            frame.setSize(500, 400); // Adjust the size as needed
            frame.setVisible(true);
        });
    }
}
