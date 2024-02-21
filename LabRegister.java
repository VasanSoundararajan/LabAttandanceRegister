import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LabRegister extends JFrame {

    // Declare the components
    JLabel nameLabel, regLabel, labLabel, yearLabel, sectionLabel, systemLabel, Slabel;
    JTextField nameField, regField, systemField, Sfeild;
    JComboBox<String> labChoice, yearChoice, sectionChoice, Schoice;
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
        labLabel = new JLabel("Lab: ");
        yearLabel = new JLabel("Year: ");
        sectionLabel = new JLabel("Section: ");
        systemLabel = new JLabel("System Number: ");
        Slabel = new JLabel("Session");

        nameField = new JTextField(20);
        regField = new JTextField(10);
        systemField = new JTextField(10);
        labChoice = new JComboBox<>();
        labChoice.addItem("--");
        labChoice.addItem("Lab 1");
        labChoice.addItem("Lab 2");
        labChoice.addItem("Lab 3");

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

        submit = new JButton("Submit");
        reset = new JButton("Reset");

        submit.addActionListener(e -> {
            if (nameField.getText().equals("") || systemField.getText().equals("") ||
                    regField.getText().equals("") || labChoice.getSelectedItem().equals("--")
                    || yearChoice.getSelectedItem().equals("--") ||
                    sectionChoice.getSelectedItem().equals("--"))
                JOptionPane.showMessageDialog(this, "Enter the Fields", "Fill",
                        JOptionPane.WARNING_MESSAGE);
            else {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    sqlcon = DriverManager.getConnection(dbcon, user, pass);
                    sqlcon.prepareStatement("CREATE DATABASE lab_db");
                    sqlcon.prepareStatement("USE lab_db");
                    sqlcon.prepareStatement("CREATE TABLE register (Reg integer NOT NULL UNIQUE, Syste varchar(20) NOT"
                            + "NULL, yer int not null, sec char(3))");
                    sqlcon.prepareStatement("USE lab_db");
                    st = sqlcon.prepareStatement("insert into register "
                            + "values(?,?,?,?)");

                    st.setString(1, regField.getText());
                    st.setString(2, systemField.getText());
                    st.setString(3, (String) yearChoice.getSelectedItem());
                    st.setString(4, (String) labChoice.getSelectedItem());

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

                        labChoice.setSelectedIndex(0);
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
            labChoice.setSelectedIndex(0);
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
        add(labLabel);
        add(labChoice);
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