import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class LabRegister extends JFrame {

    // Declare the components
    JLabel nameLabel, regLabel, dateLabel, yearLabel, sectionLabel, systemLabel, Slabel;
    JTextField nameField, regField, systemField, Sfeild, dateFeild;
    JComboBox<String> yearChoice, sectionChoice, Schoice;
    JButton submit, reset;
    private static final String user = "root";
    private static final String pass = "0221";
    private static final String dbcon = "jdbc:mysql://localhost:3306/lab_db";
    Connection sqlcon = null;
    PreparedStatement st = null;
    int q;

    public LabRegister() {
        // Initialize the components

        nameLabel = new JLabel("Name: ");
        regLabel = new JLabel("Register Number: ");
        dateLabel = new JLabel("Date ");
        yearLabel = new JLabel("Year: ");
        sectionLabel = new JLabel("Section: ");
        systemLabel = new JLabel("System Number: ");
        Slabel = new JLabel("Session");

        dateFeild = new JTextField(20);
        nameField = new JTextField(20);
        regField = new JTextField(10);
        systemField = new JTextField(10);

        String[] years = new String[21];
        years[0] = "--";
        for (int i = 1; i < 21; i++) {
            years[i] = "" + (1995 + i);
        }
        yearChoice = new JComboBox<>(years);

        Schoice = new JComboBox<>();
        Schoice.addItem("--");
        Schoice.addItem("Forenoon");
        Schoice.addItem("Afternoon");

        String[] sections = { "--", "A", "B", "C" };
        sectionChoice = new JComboBox<>(sections);

        dateFeild.setText(LocalDate.now().toString());
        dateFeild.setEditable(false);
        submit = new JButton("Submit");
        reset = new JButton("Reset");

        submit.addActionListener(e -> {
            if (nameField.getText().equals("") || systemField.getText().equals("") ||
                    regField.getText().equals("") || yearChoice.getSelectedItem().equals("--") ||
                    sectionChoice.getSelectedItem().equals("--"))
                JOptionPane.showMessageDialog(this, "Enter the Fields", "Fill",
                        JOptionPane.WARNING_MESSAGE);
            else {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    sqlcon = DriverManager.getConnection(dbcon, user, pass);
                    sqlcon.prepareStatement("CREATE DATABASE labattandance");
                    sqlcon.prepareStatement("USE labattandance");
                    sqlcon.prepareStatement("CREATE TABLE register (Reg integer NOT NULL UNIQUE, Syste varchar(20) NOT"
                            + "NULL, year int not null, sec char(3), Session  varchar(10))");
                    sqlcon.prepareStatement("USE lab_db");
                    st = sqlcon.prepareStatement("insert into register "
                            + "values(?,?,?,?, ?)");

                    st.setString(1, regField.getText());
                    st.setString(2, systemField.getText());
                    st.setString(3, (String) yearChoice.getSelectedItem());
                    st.setString(4, dateFeild.getText());

                    int result = JOptionPane.showConfirmDialog(this, "CONFRIM YOU WANT TO SAVE",
                            "SAVE",
                            JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        st.executeUpdate();
                        JOptionPane.showMessageDialog(this, "RECORDED SUCESSFULLY", "SAVED",
                                JOptionPane.INFORMATION_MESSAGE);
                        nameField.setText("");
                        systemField.setText("");
                        regField.setText("");

                        // labChoice.setSelectedIndex(0);
                        sectionChoice.setSelectedIndex(0);
                        yearChoice.setSelectedIndex(0);
                    }
                } catch (SQLException ex) {
                    if (ex.getSQLState().equals("HYOOO") && ex.getErrorCode() == 1007)
                        return;
                    JOptionPane.showMessageDialog(this, "ERROR IN SQL" + e, "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, "CLASS NOT FOUND", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            // System.out.println(nameField.getText() + "\n" + regField.getText() + "\n" +
            // systemField.getText() + "\n"
            // + (String) yearChoice.getSelectedItem() + "\n" + (String)
            // Schoice.getSelectedItem() + "\n"
            // + (String) sectionChoice.getSelectedItem() + "\n" +
            // (String) labChoice.getSelectedItem());

        });

        reset.addActionListener(e -> {
            nameField.setText("");
            regField.setText("");
            Schoice.setSelectedIndex(0);
            yearChoice.setSelectedIndex(0);
            sectionChoice.setSelectedIndex(0);
            systemField.setText("");
        });

        // Add the components to the frame
        setLayout(new GridLayout(8, 2));
        add(nameLabel);
        add(nameField);
        add(regLabel);
        add(regField);
        add(dateLabel);
        add(dateFeild);
        add(yearLabel);
        add(yearChoice);
        add(sectionLabel);
        add(sectionChoice);
        add(systemLabel);
        add(systemField);
        add(Slabel);
        add(Schoice);
        add(submit);
        add(reset);

        // Set the frame properties
        setTitle("Lab Register");
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {
        LabRegister frame = new LabRegister();
        frame.setVisible(true);
    }
}