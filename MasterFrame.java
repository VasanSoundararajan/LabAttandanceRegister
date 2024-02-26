import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class MasterFrame extends JFrame {
    private JTable sessionTable;
    private JLabel statusLabel;
    private String[] columnNames = { "Reg No", "Name", "Date", "System No", "Session" };

    public MasterFrame() {
        statusLabel = new JLabel("Status: Ready");

        // Retrieve data from the database and store it in a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        conectTable(model);

        // Set the model to the JTable
        sessionTable = new JTable(model);
        sessionTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
        sessionTable.setFillsViewportHeight(true);

        add(statusLabel);
        JScrollPane scrollPane = new JScrollPane(sessionTable);
        add(scrollPane);

        setTitle("Master Login");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    static void conectTable(DefaultTableModel model) {
        Connection connection = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection (replace 'your_database_name', 'your_username', and
            // 'your_password' accordingly)
            final String user = "root";
            final String pass = "0221";
            final String url = "jdbc:mysql://localhost:3306/lab_db";
            connection = DriverManager.getConnection(url, user, pass);

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query
            ResultSet resultSet = statement.executeQuery("SELECT * FROM register");

            // Process the result set
            while (resultSet.next()) {
                // Retrieve data by column name
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String date = resultSet.getString("date");
                int systemNo = resultSet.getInt("system_no");
                String session = resultSet.getString("session");

                // Add the data to the table model
                model.addRow(new Object[] { id, name, date, systemNo, session });
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new MasterFrame();
    }
}