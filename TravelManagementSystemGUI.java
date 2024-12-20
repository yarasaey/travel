import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import javax.swing.ImageIcon;

public class TravelManagementSystemGUI extends JFrame {
    private JPanel loginPanel, signupPanel;
    private JTextField nameField, phoneField, emailField, usernameField, signupUsernameField, signupEmailField;
    private JPasswordField passwordField, signupPasswordField;
    private JComboBox<String> packageComboBox, accommodationComboBox;
    private JButton loginButton, signupButton, submitLoginButton, submitSignupButton;
    private JTabbedPane mainTabbedPane, loginTabbedPane;
    private HashMap<String, String> userData = new HashMap<>(); // Store user data
    private HashMap<String, String> loginData = new HashMap<>(); // Store login details
    private String selectedPackage = "";
    private String selectedAccommodation = "";
    private String name = "", phone = "", email = "", username = "", password = "";
    private JTextArea summaryTextArea;

    public TravelManagementSystemGUI() {
        setTitle("Travel Management System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Initialize components
        loginTabbedPane = new JTabbedPane();

        // Login Panel
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));
        loginPanel.add(new JLabel("Username: "));
        usernameField = new JTextField();
        loginPanel.add(usernameField);

        loginPanel.add(new JLabel("Password: "));
        passwordField = new JPasswordField();
        loginPanel.add(passwordField);

        submitLoginButton = new JButton("Login");
        submitLoginButton.addActionListener(e -> loginUser());
        loginPanel.add(submitLoginButton);

        signupButton = new JButton("Go to Sign Up");
        signupButton.addActionListener(e -> showSignupForm());
        loginPanel.add(signupButton);

        // Sign Up Panel
        signupPanel = new JPanel();
        signupPanel.setLayout(new GridLayout(4, 2));
        signupPanel.add(new JLabel("Username: "));
        signupUsernameField = new JTextField();
        signupPanel.add(signupUsernameField);

        signupPanel.add(new JLabel("Email: "));
        signupEmailField = new JTextField();
        signupPanel.add(signupEmailField);

        signupPanel.add(new JLabel("Password: "));
        signupPasswordField = new JPasswordField();
        signupPanel.add(signupPasswordField);

        submitSignupButton = new JButton("Submit Sign Up");
        submitSignupButton.addActionListener(e -> signupUser());
        signupPanel.add(submitSignupButton);

        // Add login and signup panels to login tabbedPane
        loginTabbedPane.addTab("Login", loginPanel);
        loginTabbedPane.addTab("SignUp", signupPanel);

        // Dashboard Panel with Tabbed Navigation (after successful login)
        mainTabbedPane = new JTabbedPane();

        // Customer Data Tab
        JPanel customerDataPanel = new JPanel();
        customerDataPanel.setLayout(new GridLayout(5, 2));
        customerDataPanel.add(new JLabel("Name: "));
        nameField = new JTextField();
        customerDataPanel.add(nameField);

        customerDataPanel.add(new JLabel("Phone: "));
        phoneField = new JTextField();
        customerDataPanel.add(phoneField);

        customerDataPanel.add(new JLabel("Email: "));
        emailField = new JTextField();
        customerDataPanel.add(emailField);

        JButton submitCustomerDataButton = new JButton("Submit Customer Data");
        submitCustomerDataButton.addActionListener(e -> {
            name = nameField.getText();
            phone = phoneField.getText();
            email = emailField.getText();
            userData.put("Name", name);
            userData.put("Phone", phone);
            userData.put("Email", email);
            JOptionPane.showMessageDialog(null, "Customer Data Saved", "Info", JOptionPane.INFORMATION_MESSAGE);
            updateSummaryTab();  // Update summary tab
        });
        customerDataPanel.add(submitCustomerDataButton);

        // Accommodation Tab
        JPanel accommodationPanel = new JPanel();
        accommodationComboBox = new JComboBox<>(new String[] {"Hotel", "Hostel"});
        JButton selectAccommodationButton = new JButton("Submit Accommodation");
        selectAccommodationButton.addActionListener(e -> {
            selectedAccommodation = (String) accommodationComboBox.getSelectedItem();
            userData.put("Selected Accommodation", selectedAccommodation);
            JOptionPane.showMessageDialog(null, "You selected: " + selectedAccommodation, "Accommodation Selection", JOptionPane.INFORMATION_MESSAGE);
            updateSummaryTab();  // Update summary tab
        });

        accommodationPanel.add(new JLabel("Select Accommodation Type: "));
        accommodationPanel.add(accommodationComboBox);
        accommodationPanel.add(selectAccommodationButton);

        // Package Tab
        JPanel packagePanel = new JPanel();
        packageComboBox = new JComboBox<>(new String[] {"Luxury", "Adventure", "Cultural"});
        JButton selectPackageButton = new JButton("Submit Package");
        selectPackageButton.addActionListener(e -> {
            selectedPackage = (String) packageComboBox.getSelectedItem();
            userData.put("Selected Package", selectedPackage);
            JOptionPane.showMessageDialog(null, "You selected: " + selectedPackage, "Package Selection", JOptionPane.INFORMATION_MESSAGE);
            updateSummaryTab();  // Update summary tab
        });

        packagePanel.add(new JLabel("Select a Travel Package: "));
        packagePanel.add(packageComboBox);
        packagePanel.add(selectPackageButton);

        // Categories Tab (Displaying Image)
        JPanel categoriesPanel = new JPanel();
        categoriesPanel.setLayout(new BorderLayout());

        // Load image from URL
        try {
            URL imageUrl = new URL("https://www.bing.com/images/search?view=detailV2&ccid=UaX5UZvV&id=A2421E6025CCAAC9356B51138457B2FA900B8226&thid=OIP.UaX5UZvV64wpVfYpvHXGcgHaEX&mediaurl=https%3a%2f%2ftpn-1.s3.eu-west-2.amazonaws.com%2fmedia%2f7386%2fPestana-CR7-Marrakech.jpg&cdnurl=https%3a%2f%2fth.bing.com%2fth%2fid%2fR.51a5f9519bd5eb8c2955f629bc75c672%3frik%3dJoILkPqyV4QTUQ%26pid%3dImgRaw%26r%3d0&exph=992&expw=1680&q=hotel&simid=608004294480306131&FORM=IRPRST&ck=F50AB1C3ACEA458FF56F2D6B2F6B1078&selectedIndex=23&itb=0");
            ImageIcon hotelImage = new ImageIcon(imageUrl);
            JLabel imageLabel = new JLabel(hotelImage);
            categoriesPanel.add(imageLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Summary Tab (Displaying all entered data)
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BorderLayout());
        summaryTextArea = new JTextArea(10, 40);
        summaryTextArea.setEditable(false);
        summaryPanel.add(new JScrollPane(summaryTextArea), BorderLayout.CENTER);

        // Add tabs to the mainTabbedPane
        mainTabbedPane.addTab("Customer Data", customerDataPanel);
        mainTabbedPane.addTab("Accommodation", accommodationPanel);
        mainTabbedPane.addTab("Package", packagePanel);
        mainTabbedPane.addTab("Categories", categoriesPanel);  // Add Categories Tab
        mainTabbedPane.addTab("Summary", summaryPanel);

        // Initially show Login screen
        add(loginTabbedPane, BorderLayout.CENTER);
    }

    // Method to show the signup form
    private void showSignupForm() {
        loginTabbedPane.setSelectedIndex(1); // Switch to the SignUp tab
    }

    // Login user method
    private void loginUser() {
        String inputUsername = usernameField.getText();
        String inputPassword = new String(passwordField.getPassword());

        // Check if loginData contains the username and password matches
        if (loginData.containsKey(inputUsername) && loginData.get(inputUsername).equals(inputPassword)) {
            JOptionPane.showMessageDialog(null, "Login Successful!");
            // After login, show the mainTabbedPane
            getContentPane().removeAll();
            getContentPane().add(mainTabbedPane, BorderLayout.CENTER);
            revalidate();
            repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Username or Password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    /// Sign up user method
    private void signupUser() {
        String inputUsername = signupUsernameField.getText();
        String inputPassword = new String(signupPasswordField.getPassword());
        String inputEmail = signupEmailField.getText();

        // Store user data for login
        loginData.put(inputUsername, inputPassword);
        userData.put("Email", inputEmail);
        JOptionPane.showMessageDialog(null, "Signup Successful! You can now log in.");
        loginTabbedPane.setSelectedIndex(0); // Switch to the Login tab
    }

    // Update the Summary Tab with all the collected user data
    private void updateSummaryTab() {
        StringBuilder summary = new StringBuilder();

        summary.append("User Data Summary:\n");
        summary.append("Name: ").append(userData.getOrDefault("Name", "Not Provided")).append("\n");
        summary.append("Phone: ").append(userData.getOrDefault("Phone", "Not Provided")).append("\n");
        summary.append("Email: ").append(userData.getOrDefault("Email", "Not Provided")).append("\n");
        summary.append("Accommodation: ").append(userData.getOrDefault("Selected Accommodation", "Not Selected")).append("\n");
        summary.append("Package: ").append(userData.getOrDefault("Selected Package", "Not Selected")).append("\n");

        summaryTextArea.setText(summary.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TravelManagementSystemGUI().setVisible(true));
    }
}



                // <editor-fold defaultstate="collapsed" desc="Generated Code">
                ;


       
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
