import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class LaundryApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel cards;
    public DeliveryPanel deliveryPanel;

    public String userName;
    public String userEmail;
    public String userPhone;

    public LaundryApp() {
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        LoginPanel loginPanel = new LoginPanel(this);
        ServicePanel servicePanel = new ServicePanel(this);
        deliveryPanel = new DeliveryPanel(this);

        cards.add(loginPanel, "Login");
        cards.add(servicePanel, "Service");
        cards.add(deliveryPanel, "Delivery");

        add(cards);
        setTitle("Laundry Hub");
        setSize(1200, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        showPanel("Login");
    }

    public void showPanel(String name) {
        cardLayout.show(cards, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LaundryApp());
    }
}

// ----------------------------------------------------------------------
// LOGIN PANEL
// ----------------------------------------------------------------------
class LoginPanel extends JPanel {
    private Image backgroundImage;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@gmail\\.com$";
    private static final String PHONE_REGEX = "^[6-9]\\d{9}$";
    private final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
    private final Pattern phonePattern = Pattern.compile(PHONE_REGEX);

    public LoginPanel(LaundryApp app) {
        backgroundImage = new ImageIcon("Frame2.png").getImage();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        Font labelFont = new Font("Poppins", Font.BOLD, 22);
        Font fieldFont = new Font("Poppins", Font.PLAIN, 20);

        JLabel nameLabel = new JLabel("👤 Name:");
        nameLabel.setFont(labelFont);
        JTextField nameField = new JTextField(18);
        nameField.setFont(fieldFont);

        JLabel mailLabel = new JLabel("📧 Email:");
        mailLabel.setFont(labelFont);
        JTextField mailField = new JTextField(18);
        mailField.setFont(fieldFont);

        JLabel phoneLabel = new JLabel("📱 Phone:");
        phoneLabel.setFont(labelFont);
        JTextField phoneField = new JTextField(18);
        phoneField.setFont(fieldFont);

        JButton register = new JButton("Register / Login");
        register.setFont(new Font("Poppins", Font.BOLD, 18));
        register.setBackground(new Color(60, 120, 216));
        register.setForeground(Color.WHITE);
        register.setFocusPainted(false);
        register.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        gbc.gridx = 0; gbc.gridy = 0; add(nameLabel, gbc);
        gbc.gridx = 1; add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(mailLabel, gbc);
        gbc.gridx = 1; add(mailField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(phoneLabel, gbc);
        gbc.gridx = 1; add(phoneField, gbc);
        gbc.gridx = 1; gbc.gridy = 3; add(register, gbc);

        register.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = mailField.getText().trim();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all details.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!emailPattern.matcher(email).matches()) {
                JOptionPane.showMessageDialog(this, "Enter a valid Gmail address.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!phonePattern.matcher(phone).matches()) {
                JOptionPane.showMessageDialog(this, "Enter a valid 10-digit mobile number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            app.userName = name;
            app.userEmail = email;
            app.userPhone = phone;
            app.showPanel("Service");
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null)
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

// ----------------------------------------------------------------------
// SERVICE PANEL
// ----------------------------------------------------------------------
class ServicePanel extends JPanel {
    private Image backgroundImage;
    private JLabel amountLabel;
    private JCheckBox wash, dryClean, iron;
    private final int WASH_PRICE = 50;
    private final int DRY_CLEAN_PRICE = 120;
    private final int IRON_PRICE = 30;

    public ServicePanel(LaundryApp app) {
        backgroundImage = new ImageIcon("Frame.png").getImage();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("Choose Your Laundry Service");
        title.setFont(new Font("Poppins", Font.BOLD, 30));
        title.setForeground(new Color(40, 40, 90));

        wash = new JCheckBox("Washing (₹" + WASH_PRICE + ")");
        dryClean = new JCheckBox("Dry Cleaning (₹" + DRY_CLEAN_PRICE + ")");
        iron = new JCheckBox("Ironing (₹" + IRON_PRICE + ")");
        Font checkFont = new Font("Poppins", Font.PLAIN, 22);
        wash.setFont(checkFont);
        dryClean.setFont(checkFont);
        iron.setFont(checkFont);

        amountLabel = new JLabel("Amount: ₹0");
        amountLabel.setFont(new Font("Poppins", Font.BOLD, 26));
        amountLabel.setForeground(new Color(0, 102, 0));

        JButton next = new JButton("Next ➜");
        next.setFont(new Font("Poppins", Font.BOLD, 20));
        next.setBackground(new Color(0, 102, 204));
        next.setForeground(Color.WHITE);
        next.setFocusPainted(false);
        next.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        gbc.gridy = 0; add(title, gbc);
        gbc.gridy = 1; add(wash, gbc);
        gbc.gridy = 2; add(dryClean, gbc);
        gbc.gridy = 3; add(iron, gbc);
        gbc.gridy = 4; add(amountLabel, gbc);
        gbc.gridy = 5; add(next, gbc);

        ActionListener updatePrice = e -> {
            int total = 0;
            if (wash.isSelected()) total += WASH_PRICE;
            if (dryClean.isSelected()) total += DRY_CLEAN_PRICE;
            if (iron.isSelected()) total += IRON_PRICE;
            amountLabel.setText("Amount: ₹" + total);
        };

        wash.addActionListener(updatePrice);
        dryClean.addActionListener(updatePrice);
        iron.addActionListener(updatePrice);

        next.addActionListener(e -> {
            int total = 0;
            if (wash.isSelected()) total += WASH_PRICE;
            if (dryClean.isSelected()) total += DRY_CLEAN_PRICE;
            if (iron.isSelected()) total += IRON_PRICE;

            if (total > 0) {
                app.deliveryPanel.setServicePrice(total);
                app.showPanel("Delivery");
            } else {
                JOptionPane.showMessageDialog(this, "Please select at least one service.");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null)
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

// ----------------------------------------------------------------------
// DELIVERY PANEL (with save-to-file feature)
// ----------------------------------------------------------------------
class DeliveryPanel extends JPanel {
    private Image backgroundImage;
    private int servicePrice;
    private JLabel priceLabel;
    private JComboBox<String> pickupBox;
    private JPanel locationPanel;
    private JTextArea addressArea;
    private JComboBox<String> shopLocationBox;
    private JTextField pickupDateField;
    private JTextField deliveryDateField;
    private String selectedLocationType = "Doorstep";

    public DeliveryPanel(LaundryApp app) {
        backgroundImage = new ImageIcon("Frame.png").getImage();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);
        GridBagConstraints inner = new GridBagConstraints();
        inner.insets = new Insets(10, 10, 10, 10);
        inner.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("📦 Delivery & Pickup Details");
        title.setFont(new Font("Poppins", Font.BOLD, 28));
        title.setForeground(new Color(40, 40, 90));

        priceLabel = new JLabel("Amount: ₹0");
        priceLabel.setFont(new Font("Poppins", Font.BOLD, 22));
        priceLabel.setForeground(new Color(0, 100, 0));

        JLabel pickupLabel = new JLabel("Pickup Location:");
        pickupLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        pickupBox = new JComboBox<>(new String[]{"Doorstep", "Shop"});
        pickupBox.setFont(new Font("Poppins", Font.PLAIN, 18));

        JLabel pickupDateLabel = new JLabel("Pickup Date (YYYY-MM-DD):");
        pickupDateLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        pickupDateField = new JTextField(15);
        pickupDateField.setFont(new Font("Poppins", Font.PLAIN, 18));

        JLabel deliveryDateLabel = new JLabel("Delivery Date (YYYY-MM-DD):");
        deliveryDateLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        deliveryDateField = new JTextField(15);
        deliveryDateField.setFont(new Font("Poppins", Font.PLAIN, 18));

        // Location panel
        locationPanel = new JPanel(new CardLayout());
        locationPanel.setOpaque(false);

        addressArea = new JTextArea(4, 25);
        addressArea.setFont(new Font("Poppins", Font.PLAIN, 18));
        addressArea.setBorder(BorderFactory.createTitledBorder("Enter Full Doorstep Address"));
        JScrollPane addressScroll = new JScrollPane(addressArea);
        JPanel addressPanel = new JPanel();
        addressPanel.setOpaque(false);
        addressPanel.add(addressScroll);
        locationPanel.add(addressPanel, "Doorstep");

        shopLocationBox = new JComboBox<>(new String[]{
            "Select Shop...",
            "Shop 1: Near West Hill Govt. Engineering College",
            "Shop 2: City Centre Mall Branch",
            "Shop 3: Industrial Area Service Point"
        });
        shopLocationBox.setFont(new Font("Poppins", Font.PLAIN, 18));
        JPanel shopPanel = new JPanel();
        shopPanel.setOpaque(false);
        shopPanel.add(new JLabel("Select Shop Location:"));
        shopPanel.add(shopLocationBox);
        locationPanel.add(shopPanel, "Shop");

        JButton finish = new JButton("Finish ✅");
        finish.setFont(new Font("Poppins", Font.BOLD, 20));
        finish.setBackground(new Color(0, 153, 76));
        finish.setForeground(Color.WHITE);
        finish.setFocusPainted(false);
        finish.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        int row = 0;
        inner.gridx = 0; inner.gridy = row++; content.add(title, inner);
        inner.gridy = row++; content.add(priceLabel, inner);
        inner.gridy = row++; content.add(pickupLabel, inner);
        inner.gridy = row++; content.add(pickupBox, inner);
        inner.gridy = row++; content.add(pickupDateLabel, inner);
        inner.gridy = row++; content.add(pickupDateField, inner);
        inner.gridy = row++; content.add(deliveryDateLabel, inner);
        inner.gridy = row++; content.add(deliveryDateField, inner);
        inner.gridy = row++; content.add(locationPanel, inner);
        inner.gridy = row++; content.add(finish, inner);

        add(content, gbc);

        pickupBox.addActionListener(e -> {
            selectedLocationType = (String) pickupBox.getSelectedItem();
            ((CardLayout) locationPanel.getLayout()).show(locationPanel, selectedLocationType);
        });

        finish.addActionListener(e -> handleFinish(app));
    }

    private void handleFinish(LaundryApp app) {
        String locationDetails = "";
        boolean valid = true;
        String pickupDateStr = pickupDateField.getText().trim();
        String deliveryDateStr = deliveryDateField.getText().trim();

        if (selectedLocationType.equals("Doorstep")) {
            locationDetails = addressArea.getText().trim();
            if (locationDetails.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter your Doorstep Address.", "Error", JOptionPane.ERROR_MESSAGE);
                valid = false;
            }
        } else {
            locationDetails = (String) shopLocationBox.getSelectedItem();
            if (locationDetails.equals("Select Shop...")) {
                JOptionPane.showMessageDialog(this, "Select a Shop Location.", "Error", JOptionPane.ERROR_MESSAGE);
                valid = false;
            }
        }

        if (valid) {
            try {
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate pickup = LocalDate.parse(pickupDateStr, fmt);
                LocalDate delivery = LocalDate.parse(deliveryDateStr, fmt);
                if (delivery.isBefore(pickup.plusDays(3))) {
                    JOptionPane.showMessageDialog(this, "Delivery must be at least 3 days after pickup.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // --- Order placed message ---
                String message =
                        "✅ Order Placed Successfully!\n\n" +
                        "--- User Details ---\n" +
                        "Name: " + app.userName + "\n" +
                        "Email: " + app.userEmail + "\n" +
                        "Phone: " + app.userPhone + "\n\n" +
                        "--- Order Details ---\n" +
                        "Total Amount: ₹" + servicePrice + "\n" +
                        "Pickup Date: " + pickupDateStr + "\n" +
                        "Delivery Date: " + deliveryDateStr + "\n" +
                        "Location: " + locationDetails;
                JOptionPane.showMessageDialog(this, message, "Order Placed", JOptionPane.INFORMATION_MESSAGE);

                // --- Save details silently to file ---
                saveOrderToFile(app, pickupDateStr, deliveryDateStr, locationDetails);

                app.showPanel("Login");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Use date format YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveOrderToFile(LaundryApp app, String pickup, String delivery, String location) {
        try (FileWriter writer = new FileWriter("receipts.txt", true)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            writer.write("\n-----------------------------\n");
            writer.write("Date: " + LocalDateTime.now().format(dtf) + "\n");
            writer.write("Name: " + app.userName + "\n");
            writer.write("Email: " + app.userEmail + "\n");
            writer.write("Phone: " + app.userPhone + "\n");
            writer.write("Total Amount: ₹" + servicePrice + "\n");
            writer.write("Pickup Date: " + pickup + "\n");
            writer.write("Delivery Date: " + delivery + "\n");
            writer.write("Location: " + location + "\n");
            writer.write("-----------------------------\n");
        } catch (IOException e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }
    }

    public void setServicePrice(int price) {
        servicePrice = price;
        priceLabel.setText("Amount: ₹" + price);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null)
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}